Projet realis� en groupe de 3 compos� de 
Th�o Georges
Clement Bourdeau
Antoine Deboeuf

_Creation de la base avec la m�thode topDown.
_Nous avons choisi de cr�er une base personne avec des Artistes, r�alisateurs, Acteurs et auteurs qui en h�ritent 
en utilisant la strat�gie single table.
_M�me chose pour CD, DVD et Livre qui h�ritent de Article avec cette fois la strat�gie joined.
_Enfin pour les commandes nous avons choisi de cr�er une entit� ligne commande.


_Premiere requete:
La m�thode findArticlesFromArtiste de l'articleDao prend en parametre une artiste et renvoi toutes ses oeuvres

_Deuxi�me requete
La m�thode sumAllPrices renvoi un tableau associatif de la forme "type => somme des prix"

_Derni�re requete
La m�thode bestStyle du styleDao affiche le style ayant �t� le plus vendu et sellsStyle son nombre de ventes

_Enfin toutes les op�rations CRUD sont faites