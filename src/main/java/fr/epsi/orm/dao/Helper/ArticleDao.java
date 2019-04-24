package fr.epsi.orm.dao.Helper;

import fr.epsi.orm.exceptions.AlreadyExistsException;
import fr.epsi.orm.model.Article;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.sql.SQLException;

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

    private Article findByTitre(String titre) {
        TypedQuery<Article> query = getEntityManager().createQuery("from Article a where a.titre = :titre", Article.class);
        query.setParameter("titre", titre);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
