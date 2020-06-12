package tests;

import org.junit.Test;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Persistence;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class DatabaseTest {

    /**
     * Dummy entity with an ID for testing database.
     */
    @Entity
    public static class TestEntity {

        @Id
        public UUID id;

        public TestEntity(){}
        public TestEntity(UUID id) { this.id = id; }

    }

    /**
     * Persist a TestEntity to the database.
     * @param e to persist.
     */
    void persistTestEntity(TestEntity e){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("p-unit");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.merge(e);

        em.getTransaction().commit();
        em.close();
        emf.close();

    }

    /**
     * Load a persisted entity from the database.
     * @param id of entity to load.
     * @return instance of entity.
     */
    TestEntity loadTestEntity(UUID id){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("p-unit");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        TestEntity r = em.find(TestEntity.class, id);

        em.close();
        emf.close();

        return r;

    }

    /**
     * Save an entity to database with a given ID, then load it back into memory using the same ID and check both
     * instances have the same ID.
     */
    @Test
    public void databaseTest(){

        UUID id = UUID.randomUUID();

        TestEntity a = new TestEntity(id);

        persistTestEntity(a);

        TestEntity b = loadTestEntity(id);

        assertEquals(a.id, b.id);

    }

}
