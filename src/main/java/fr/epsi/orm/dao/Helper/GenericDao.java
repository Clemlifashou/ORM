package fr.epsi.orm.dao.Helper;

import javax.persistence.EntityManager;

import fr.epsi.orm.dao.Helper.DatabaseHelper;
import fr.epsi.orm.exceptions.AlreadyExistsException;

public class GenericDao {

    private EntityManager entityManager;

    public GenericDao() {
        this.entityManager = DatabaseHelper.createEntityManager();
    }

    protected EntityManager getEntityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = DatabaseHelper.createEntityManager();
        }
        return entityManager;
    }

    protected void insert(Object object){
        entityManager = getEntityManager();
        DatabaseHelper.beginTransaction(entityManager);
        entityManager.persist(object);
        DatabaseHelper.commitTransactionAndClose(entityManager);
    }
}
