Projet realisé en groupe de 3 composé de 
Théo Georges
Clement Bourdeau
Antoine Deboeuf

_Creation de la base avec la méthode topDown.
_Nous avons choisi de créer une base personne avec des Artistes, réalisateurs, Acteurs et auteurs qui en héritent 
en utilisant la stratégie single table.
_Même chose pour CD, DVD et Livre qui héritent de Article avec cette fois la stratégie joined.
_Enfin pour les commandes nous avons choisi de créer une entité ligne commande.


_Premiere requete:
La méthode findArticlesFromArtiste de l'articleDao prend en parametre une artiste et renvoi toutes ses oeuvres

_Deuxième requete
La méthode sumAllPrices renvoi un tableau associatif de la forme "type => somme des prix"

_Dernière requete
La méthode bestStyle du styleDao affiche le style ayant été le plus vendu et sellsStyle son nombre de ventes

_Enfin toutes les opérations CRUD sont faites