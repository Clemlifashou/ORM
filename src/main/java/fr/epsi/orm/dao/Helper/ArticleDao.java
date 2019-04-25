package fr.epsi.orm.dao.Helper;

import fr.epsi.orm.exceptions.AlreadyExistsException;
import fr.epsi.orm.model.Article;
import fr.epsi.orm.model.Artiste;
import fr.epsi.orm.model.CD;
import fr.epsi.orm.model.Groupe;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class ArticleDao extends GenericDao {

    /**
     * Inserts an article.
     * @param article The article to persist.
     * @return The persisted article.
     * @throws AlreadyExistsException The object already exists.
     */
    public Article insertArticle(Article article)throws AlreadyExistsException{

        // Check if number already exists
        Article existing = findByTitre(article.getTitre());
        if (existing != null) {
            throw new AlreadyExistsException("An article with the title " + article.getTitre() + " already exists.");
        }

        this.insert(article);
        return (article);
    }

    /**
     * Finds a article by its id.
     * @return The matching article, otherwise null.
     * @throws SQLException
     */
    public Article findById(long id){
        return getEntityManager().find(Article.class, id);
    }

    public Article findByTitre(String titre) {
        TypedQuery<Article> query = getEntityManager().createQuery("from Article a where a.titre = :titre", Article.class);
        query.setParameter("titre", titre);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<CD> findArticlesFromGroupe(Groupe groupe){
        TypedQuery<CD> query = getEntityManager().createQuery("select cd from CD cd join cd.groupe a where a = :groupe" , CD.class);
        query.setParameter("groupe", groupe);

        return query.getResultList();
    }

    public List<CD> findArticlesFromArtiste(Artiste artiste){
        TypedQuery<CD> query = getEntityManager().createQuery("select cd from CD cd join cd.artiste a where a = :artiste" , CD.class);
        query.setParameter("artiste", artiste);

        return query.getResultList();
    }

    public Double sumPrices(String type){
        String request = "select sum(t.prix) from "+type+" t";
        TypedQuery<Double> query = getEntityManager().createQuery(request , Double.class);
        return query.getSingleResult();
    }

    public HashMap<String, Double> sumAllPrices(){
        HashMap<String, Double> list = new HashMap<String, Double>();
        list.put("CD", sumPrices("CD"));
        list.put("Livres", sumPrices("Livre"));
        list.put("DVD", sumPrices("DVD"));
        return list;
    }
}
