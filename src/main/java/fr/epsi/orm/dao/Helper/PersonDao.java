package fr.epsi.orm.dao.Helper;

import fr.epsi.orm.exceptions.AlreadyExistsException;
import fr.epsi.orm.model.Article;
import fr.epsi.orm.model.Personne;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class PersonDao extends GenericDao {

    /**
     * Inserts a person.
     * @param personne The person to persist.
     * @return The persisted person.
     * @throws AlreadyExistsException The object already exists.
     */
    public Personne insertPersonne(Personne personne) throws AlreadyExistsException{

        // Check if person already exists
        Personne existing = findByNomPrenom(personne.getNom(), personne.getPrenom());
        if (existing != null) {
            throw new AlreadyExistsException("A person with the name " + personne.getNom() + ' '+ personne.getPrenom() + " already exists.");
        }

        this.insert(personne);
        return (personne);
    }

    public Personne findByNomPrenom(String nom, String prenom){
        TypedQuery<Personne> query = getEntityManager().createQuery("from Personne p where p.nom = :nom and p.prenom = :prenom", Personne.class);
        query.setParameter("nom", nom);
        query.setParameter("prenom", prenom);

        try{
            return query.getSingleResult();
        }
        catch (NoResultException e){
            return null;
        }
    }

    /**
     * Find all personnes.
     * @return list of Personne, otherwise null.
     */
    public List<Personne> findAll() {
        TypedQuery<Personne> query = getEntityManager().createQuery("from Personne", Personne.class);
        try{
            return query.getResultList();
        }
        catch (NoResultException e){
            return null;
        }
    }

    /**
     * Find a personne by its nom and prenom.
     * @param id
     * @return The matching Personne, otherwise null.
     */
    public Personne findById(long id){
        return getEntityManager().find(Personne.class, id);
    }

    /**
     * update a personne by its id.
     * @param nom, prenom, nouveaunom, nouveauprenom
     * @return The updated Personne.
     */
    public Personne updateNomPrenom(String nom, String prenom, String nouveaunom, String nouveauprenom) {
        Personne Personne = findByNomPrenom(nom, prenom);
        if(!nouveaunom.equals("")){
            Personne.setNom(nouveaunom);
        }
        if(!nouveauprenom.equals("")){
            Personne.setPrenom(nouveauprenom);
        }

        getEntityManager().getTransaction().begin();
        getEntityManager().merge(Personne);
        getEntityManager().getTransaction().commit();

        return Personne;
    }

    /**
     * delete a Personne by its id.
     * @param id
     */
    public void delete(long id) {
        Personne bddPersonne = getEntityManager().find(Personne.class, id);

        getEntityManager().getTransaction().begin();
        getEntityManager().remove(bddPersonne);
        getEntityManager().getTransaction().commit();
    }
}
