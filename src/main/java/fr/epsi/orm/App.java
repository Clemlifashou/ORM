package fr.epsi.orm;

import fr.epsi.orm.dao.Helper.*;
import fr.epsi.orm.exceptions.AlreadyExistsException;
import fr.epsi.orm.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class App {
    public static void main(String[] args) throws AlreadyExistsException {
        //      CRUD       //

        //création des entités
        insererPersonnes();
        insererStyles();
        insererGroupes();
        insererArticles();
        insererCommande();


        //Récupération des entités dans la base :
        readPersonnes();
        readStyles();
        readGroupes();
        readArticles();

        //Update des entités en base :
        updatePersonne();
        updateStyle();
        updateGroupe();
        updateArticle();

        //Delete des entités en base :
        /*deleteArticle();
        deleteGroupe();
        deleteStyle();
        deletePersonne();
        */

        //  REQUETES COMPLEXES  //

        //Récupérer la totalité des oeuvres d'un artiste
        ArticleDao a = new ArticleDao();
        PersonDao p = new PersonDao();
        Personne eminem = p.findByNomPrenom("Eminem","");
        List<CD> cds = a.findArticlesFromArtiste((Artiste)eminem);
        System.out.println("Voici les articles d'eminem: ");
        for(CD cd: cds){
            System.out.println(cd);
        }

        //Récupérer la totalité des oeuvres d'un groupe
        GroupeDao gdao = new GroupeDao();
        Groupe redhot = gdao.findByNom("New Red Hot Chilli Peppers");
        System.out.println(redhot);
        List<CD> cds2 = a.findArticlesFromGroupe(redhot);
        System.out.println("Voici les articles des Red Hot: ");
        for(CD cd: cds2){
            System.out.println(cd);
        }

        //Récupérer les sommes des prix de chaque type d'article
        HashMap<String, Double> list = a.sumAllPrices();
        for(String s: list.keySet()){
            System.out.println("La somme des prix de " + s +": "+list.get(s));
        }

        //Récupérer la catégorie musicale le qui rencontre le meillleur succès
        StyleDao sdao = new StyleDao();
        StyleMusical best = sdao.bestStyle();
        System.out.println("L'article qui a vendu le plus est " + best + "avec " + sdao.sellsStyle(best) + "ventes.");
    }

    public static void insererPersonnes(){
        ArrayList<Personne> listPeople = new ArrayList<Personne>();
        PersonDao pdao = new PersonDao();

        Artiste ray = new Artiste("Ray", "Charles");
        listPeople.add(ray);

        Artiste eminem = new Artiste("Eminem", "");
        listPeople.add(eminem);

        Acteur dicaprio = new Acteur("Leonardo", "DiCaprio");
        listPeople.add(dicaprio);

        Auteur jk = new Auteur("Joanne", "Rowling");
        listPeople.add(jk);

        Realisateur tarantino = new Realisateur("Quantin", "Tarantino");
        listPeople.add(tarantino);

        for(Personne p: listPeople){
            try {
                pdao.insertPersonne(p);
                System.out.println(p.getPrenom() + ' '+ p.getNom() + " inséré");
            } catch (AlreadyExistsException e) {
                e.printStackTrace();
            }
        }
    }

    public static void insererStyles(){
        StyleDao sdao = new StyleDao();
        ArrayList<StyleMusical> listStyles = new ArrayList<StyleMusical>();

        StyleMusical rock = new StyleMusical("Rock");
        listStyles.add(rock);

        StyleMusical jazz = new StyleMusical("Jazz");
        listStyles.add(jazz);

        StyleMusical rap = new StyleMusical("Rap");
        listStyles.add(rap);

        for(StyleMusical s: listStyles){
            try {
                sdao.insertStyle(s);
                System.out.println(s.getNom() + " inséré");
            } catch (AlreadyExistsException e) {
                e.printStackTrace();
            }
        }
    }

    public static void insererGroupes(){
        GroupeDao gdao = new GroupeDao();
        ArrayList<Groupe> groupes = new ArrayList<Groupe>();

        Groupe redHot = new Groupe();
        redHot.setNom("Red Hot Chilli Peppers");
        groupes.add(redHot);

        Groupe queen = new Groupe();
        queen.setNom("Queen");
        groupes.add(queen);

        for(Groupe g: groupes){
            try {
                gdao.insertStyle(g);
                System.out.println(g.getNom() + " inséré");
            } catch (AlreadyExistsException e) {
                e.printStackTrace();
            }
        }
    }

    public static void insererArticles(){
        ArticleDao adao = new ArticleDao();
        GroupeDao gdao = new GroupeDao();
        StyleDao sdao = new StyleDao();
        PersonDao pdao = new PersonDao();
        ArrayList<Article> articles = new ArrayList<Article>();

        Groupe redHot = gdao.findByNom("Red Hot Chilli Peppers");
        Artiste eminem = (Artiste) pdao.findByNomPrenom("Eminem", "");

        StyleMusical rock = sdao.findByNom("Rock");
        StyleMusical rap = sdao.findByNom("Rap");

        CD bssm = new CD("Blood sugar sex  magik",rock,redHot);
        articles.add(bssm);
        CD californication = new CD("Californication",rock,redHot);
        articles.add(californication);
        CD slimShady = new CD("The Slim Shady", rap, eminem);
        articles.add(slimShady);

        for(Article a: articles){
            try {
                adao.insertArticle(a);
                System.out.println(a.getTitre() + " inséré");
            } catch (AlreadyExistsException e) {
                e.printStackTrace();
            }
        }
    }

    public static void insererCommande(){
        ArticleDao adao = new ArticleDao();

        CD slimshady = (CD)adao.findByTitre("The Slim Shady");
        LigneCommande ligne = new LigneCommande(slimshady,10);
        Commande cmd = new Commande();
        cmd.addLigne(ligne);

        CommandeDao cmddao = new CommandeDao();
        cmddao.insertCommande(cmd);

        CD bssm = (CD)adao.findByTitre("Blood sugar sex  magik");
        LigneCommande ligne2 = new LigneCommande(bssm,11);
        Commande cmd2 = new Commande();
        cmd2.addLigne(ligne2);


        cmddao.insertCommande(cmd2);
    }

    private static void deletePersonne() {
        PersonDao personDao = new PersonDao();

        personDao.delete(1);

        readPersonnes();
    }

    private static void deleteStyle() {
        StyleDao styleDao = new StyleDao();

        styleDao.delete(1);

        readStyles();
    }

    private static void deleteGroupe() {
        GroupeDao groupeDao = new GroupeDao();

        groupeDao.delete(1);

        readGroupes();
    }

    private static void deleteArticle() {
        ArticleDao articleDao = new ArticleDao();

        articleDao.delete(1);

        readArticles();
    }

    private static void updateGroupe() {
        GroupeDao groupeDao = new GroupeDao();

        groupeDao.updateNom("Red Hot Chilli Peppers", "New Red Hot Chilli Peppers");

        readGroupes();
    }

    private static void updateStyle() {
        StyleDao styleDao = new StyleDao();

        styleDao.updateNom("Rap", "Rap/RnB");

        readStyles();
    }

    private static void updateArticle() {
        ArticleDao articleDao = new ArticleDao();

        articleDao.update("Californication", 15.0f, "Californication (limited edition)");

        readArticles();
    }

    private static void updatePersonne() {
        PersonDao personDao = new PersonDao();

        personDao.updateNomPrenom("Joanne", "Rowling","Rowling/Galbraith", "Joanne/Robert");

        readPersonnes();

    }

    private static void readArticles() {
        ArticleDao articleDao = new ArticleDao();

        List<Article> articles = articleDao.findAll();

        for (Article article : articles) {
            System.out.println(article);
        }
    }

    private static void readGroupes() {
        GroupeDao groupeDao = new GroupeDao();

        List<Groupe> groupes = groupeDao.findAll();

        for (Groupe groupe : groupes)
        {
            System.out.println(groupe);
        }
    }

    private static void readStyles() {
        StyleDao styleDao = new StyleDao();

        List<StyleMusical> styleMusicaux = styleDao.findAll();

        for (StyleMusical styleMusical : styleMusicaux)
        {
            System.out.println(styleMusical);
        }
    }

    private static void readPersonnes() {
        PersonDao personDao = new PersonDao();

        List<Personne> personnes = personDao.findAll();

        for (Personne personne : personnes)
        {
            System.out.println(personne);
        }
    }
}
