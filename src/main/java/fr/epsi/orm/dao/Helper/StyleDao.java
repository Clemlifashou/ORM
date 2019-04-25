package fr.epsi.orm.dao.Helper;

import fr.epsi.orm.exceptions.AlreadyExistsException;
import fr.epsi.orm.model.StyleMusical;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

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

    public List<StyleMusical> findAll(){
        TypedQuery<StyleMusical> styles = getEntityManager().createQuery("from StyleMusical", StyleMusical.class);
        return styles.getResultList();
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

    public Long sellsStyle(StyleMusical style){
        TypedQuery<Long> query = getEntityManager().createQuery("select sum(l.quantite) from LigneCommande l " +
                "join l.article a where a.id in (" +
                "select cd.id from CD cd where cd.style = :style)", Long.class);
        query.setParameter("style", style);
        if(query.getSingleResult() == null){
            return (long)0;
        }
        return query.getSingleResult();
    }

    public StyleMusical bestStyle(){
        List<StyleMusical> styles = findAll();
        long best = 0;
        StyleMusical beststyle = new StyleMusical();
        for(StyleMusical s: styles){
            if(sellsStyle(s) > best){
                best = sellsStyle(s);
                beststyle = s;
            }
        }
        return beststyle;
    }

    /**
     * update a musical style by its id.
     * @param id
     * @param style
     * @return The updated Musical Style.
     */
    public StyleMusical update(long id, String style) {
        StyleMusical styleMusical = getEntityManager().find(StyleMusical.class, id);

        styleMusical.setNom("Rap/RnB");

        getEntityManager().getTransaction().begin();
        getEntityManager().merge(styleMusical);
        getEntityManager().getTransaction().commit();

        return styleMusical;
    }

    /**
     * delete a musical style by its id.
     * @param id
     */
    public void delete(long id) {
        StyleMusical styleMusical = getEntityManager().find(StyleMusical.class, id);

        getEntityManager().getTransaction().begin();
        getEntityManager().remove(styleMusical);
        getEntityManager().getTransaction().commit();
    }
}
