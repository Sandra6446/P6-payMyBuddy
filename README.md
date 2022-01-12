# P6-payMyBuddy

Ce repo contient :
- Le dossier pmb-backend
- Le dossier pmb-frontend
- Le rapport de couverture de tests Jacoco
- La Javadoc
- Le script de création et de remplissage de la base de donnée Mysql pour les essais
- Le document Etude et Conception : qui contient le MPD et l'étude préparatoire au projet

-----------------------------------------
 Préparation de la base de donnée MySQL
-----------------------------------------

- Installer MySQL Server
- Se connecter sur le compte admin
- Lancer le script ScriptCreationDatabase.sql à l'aide de la commande :
    > source "chemin du script"
    
    Le script déclenche :
    * Suppression de la base pay_my_buddy si elle a déjà été créée
    * Création d'une nouvelle base pay_my_buddy
    * Création d'un USER ALL PRIVILEGES :  identifiant: openclassrooms / mdp:OC
    * Création de 5 utilisateurs dans la base de donnée :
        mail : sarah@email.com / mdp : sarah
        mail : thomas@email.com / mdp : thomas
        mail : louis@email.com / mdp : louis
        mail : myriam@email.com / mdp : myriam
        mail : rachel@email.com / mdp rachel

-----------------------------------------
 Installation et lancement du backend
-----------------------------------------

- Installer Maven et Java SDK 11
- Vérifier le chemin et le port de la base de donnée et rectifier si nécessaire l'url dans le fichier main/resources/application.properties
- Ouvrir un terminal dans pmb-backend :
- > mvn install
- > java -jar "fichier .jar généré dans target"

-----------------------------------------
 Installation et lancement du frontend
-----------------------------------------

- Installer npm
- Ouvrir un terminal dans pmb-frontend :
- > npm install
- > npm run serve

Vous pouvez cliquer sur une des deux URL proposées et commencer les essais...
