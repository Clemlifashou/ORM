package fr.epsi.orm;

import fr.epsi.orm.dao.Helper.*;
import fr.epsi.orm.exceptions.AlreadyExistsException;
import fr.epsi.orm.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws AlreadyExistsException {
        //création des entités
        /*
            insererPersonnes();
         insererStyles();
        insererGroupes();
        */
        insererArticles();

        //Récupérer la totalité des oeuvres d'un artiste



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
}
