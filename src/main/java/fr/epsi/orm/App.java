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
        //création des entités
        /*
            insererPersonnes();
         insererStyles();
        insererGroupes();
        insererArticles();
        insererCommande();  
        */

        //Récupérer la totalité des oeuvres d'un artiste

        ArticleDao a = new ArticleDao();
        /*
        PersonDao p = new PersonDao();
        Personne eminem = p.findByNomPrenom("Eminem","");
        List<CD> cds = a.findArticlesFromArtiste((Artiste)eminem);
        for(CD cd: cds){
            System.out.println(cd);
        }

        //Récupérer la totalité des oeuvres d'un groupe
        GroupeDao gdao = new GroupeDao();
        Groupe redhot = gdao.findByNom("Red Hot Chilli Peppers");
        List<CD> cds2 = a.findArticlesFromGroupe(redhot);
        for(CD cd: cds2){
            System.out.println(cd);
        }

        //Récupérer les sommes des prix de chaque type d'article
        HashMap<String, Double> list = a.sumAllPrices();
        for(String s: list.keySet()){
            System.out.println(s+": "+list.get(s));
        }
        */

        insererCommande();
        StyleDao sdao = new StyleDao();
        StyleMusical best = sdao.bestStyle();
        System.out.println(best);
        System.out.println(sdao.sellsStyle(best));
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
        /*
        CD slimshady = (CD)adao.findByTitre("The Slim Shady");
        LigneCommande ligne = new LigneCommande(slimshady,10);
        Commande cmd = new Commande();
        cmd.addLigne(ligne);
*/
        CommandeDao cmddao = new CommandeDao();
        //cmddao.insertCommande(cmd);

        CD bssm = (CD)adao.findByTitre("Blood sugar sex  magik");
        LigneCommande ligne2 = new LigneCommande(bssm,11);
        Commande cmd2 = new Commande();
        cmd2.addLigne(ligne2);


        cmddao.insertCommande(cmd2);
    }
}
