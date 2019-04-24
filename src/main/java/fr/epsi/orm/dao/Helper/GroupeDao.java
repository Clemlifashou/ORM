package fr.epsi.orm.dao.Helper;

import fr.epsi.orm.exceptions.AlreadyExistsException;
import fr.epsi.orm.model.Groupe;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class GroupeDao extends GenericDao {

    /**
     * Inserts a groupe.
     *
     * @param groupe The group to persist.
     * @return The persisted group.
     * @throws AlreadyExistsException The group already exists.
     */
    public Groupe insertStyle(Groupe groupe) throws AlreadyExistsException {

        // Check if person already exists
        Groupe existing = findByNom(groupe.getNom());
        if (existing != null) {
            throw new AlreadyExistsException("A groupe with the name " + groupe.getNom() + ' ' + " already exists.");
        }

        this.insert(groupe);
        return (groupe);
    }

    public Groupe findByNom(String nom) {
        TypedQuery<Groupe> query = getEntityManager().createQuery("from Groupe g where g.nom = :nom", Groupe.class);
        query.setParameter("nom", nom);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
