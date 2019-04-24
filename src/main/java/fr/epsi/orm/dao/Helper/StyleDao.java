package fr.epsi.orm.dao.Helper;

import fr.epsi.orm.exceptions.AlreadyExistsException;
import fr.epsi.orm.model.StyleMusical;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class StyleDao extends GenericDao {

    /**
     * Inserts a style.
     * @param style The person to persist.
     * @return The persisted style.
     * @throws AlreadyExistsException The style already exists.
     */
    public StyleMusical insertStyle(StyleMusical style) throws AlreadyExistsException{

        // Check if person already exists
        StyleMusical existing = findByNom(style.getNom());
        if (existing != null) {
            throw new AlreadyExistsException("A style with the name " + style.getNom() + ' ' + " already exists.");
        }

        this.insert(style);
        return (style);
    }

    public StyleMusical findByNom(String nom){
        TypedQuery<StyleMusical> query = getEntityManager().createQuery("from StyleMusical s where s.nom = :nom", StyleMusical.class);
        query.setParameter("nom", nom);

        try{
            return query.getSingleResult();
        }
        catch (NoResultException e){
            return null;
        }
    }
}
