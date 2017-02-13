FindYourWay


Membres :
Pierre Alexandre
Pereira Alexandre
Launay Guillaume
Postel Romain


Tout d'abord, veuillez nous excuser pour le retard, suite un problème de communication au sein du groupe, le projet n'a pas pu être rendu avant le délais.

Concernant le déploiement de l'application, nous n'avons pas réussis à mettre en place les deux docker demandés.
Vous pouvez néanmoins consulter le code et si vous le souhaitez Mr Rouyer, nous pourrions vous faire une démonstration, soit par video soit un midi à l'iut.


Pour déployer le serveur :

_Créer un projet avec notre dossier "API_FindYourWay"
_Ajouter les .jar manquant
_Executer le script "add-user.sh" de wildfly/bin
_Executer le script "jBoss-cli.sh" de wildfly/bin avec les commande suivante :
  1)module add --name=org.postgres --resources=/VotreCheminLocal/postgresql-9.3-1101.jdbc41.jar --dependencies=javax.api,javax.transaction.api
  
  2)/subsystem=datasources/jdbc-driver=postgres:add(driver-name="postgres",driver-module-name="org.postgres",driver-class-name=org.postgresql.Driver)
  
  3)data-source add --jndi-name=java:/PostGreDS --name=PostgrePool --connection-url=jdbc:postgresql://localhost/postgres --driver-name=postgres --user-name=root --password=root
  
 _lancer le serveur 
 
 La doc de l'api est disponnible sous forme d'un document html "Apidoc.html"
