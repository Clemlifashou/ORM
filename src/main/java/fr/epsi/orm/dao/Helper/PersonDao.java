package fr.epsi.orm.dao.Helper;

import fr.epsi.orm.exceptions.AlreadyExistsException;
import fr.epsi.orm.model.Article;
import fr.epsi.orm.model.Personne;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

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
}
