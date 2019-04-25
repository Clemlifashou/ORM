package fr.epsi.orm.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany
    private List<LigneCommande> lignes;

    public Commande() {
        lignes = new ArrayList<LigneCommande>();
    }

    public Commande(List<LigneCommande> lignes) {
        this.lignes = lignes;
    }

    public long getId() {
        return id;
    }

    public List<LigneCommande> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommande> lignes) {
        this.lignes = lignes;
    }

    public void addLigne(LigneCommande ligne){
        this.lignes.add(ligne);
    }

    @Override
    public String toString() {
        return "Commande{" +
                "lignes=" + lignes +
                '}';
    }
}
