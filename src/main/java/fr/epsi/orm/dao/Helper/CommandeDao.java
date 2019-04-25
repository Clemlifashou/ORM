package fr.epsi.orm.dao.Helper;

import fr.epsi.orm.model.Commande;
import fr.epsi.orm.model.LigneCommande;

public class CommandeDao extends GenericDao{

    public LigneCommande insertLigne(LigneCommande ligne){
        this.insert(ligne);
        return ligne;
    }

    public Commande insertCommande(Commande commande){
        for(LigneCommande ligne: commande.getLignes()){
            insertLigne(ligne);
        }
        this.insert(commande);
        return commande;
    }
}
