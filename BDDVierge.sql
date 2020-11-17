#------------------------------------------------------------
#        Script MariaDb.
#		
#	Autheur :		Christophe Michard & Alexis Moquet
#	Créé le :		29/11/2019
#	Modifié le : 	17/11/2020
#	Par :			Alexis Moquet
#------------------------------------------------------------

#------------------------------------------------------------
# BDD
#------------------------------------------------------------

DROP DATABASE IF EXISTS HandispapBDD;
CREATE DATABASE HandispapBDD CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE HandispapBDD;


#------------------------------------------------------------
# Table: Utilisateurs Sites
#------------------------------------------------------------

CREATE TABLE Utilisateurs(
        utilisateur_id   				Int  Auto_increment  NOT NULL COMMENT "Identifiant de l'utilisateur"  ,
        utilisateur_nom              	Varchar (50) NOT NULL COMMENT "Nom"  ,
        utilisateur_prenom           	Varchar (50) NOT NULL COMMENT "Prénom"  ,
        utilisateur_tel_fix          	Varchar (20) NOT NULL COMMENT "N° de téléphone fixe"  ,
        utilisateur_tel_mob          	Varchar (20) NOT NULL COMMENT "N° de téléphone mobile"  ,
        utilisateur_email            	Varchar (255) NOT NULL COMMENT "Adresse mail"  ,
        utilisateur_mot_de_passe     	Varchar (255) COMMENT "Mot de passe"  ,
        utilisateur_compte_actif     	Tinyint NOT NULL COMMENT "Compte Actif" DEFAULT 1 ,
        utilisateur_cle_mdp		     	Varchar (255) COMMENT "Clé de réinitialisation du mot de passe"  ,
		utilisateur_date_init_mdp		Date COMMENT "Date de la demande de réinitialisation du mot de passe"  ,
        utilisateur_date_inscription 	Date NOT NULL COMMENT "Date d'inscription"
	,CONSTRAINT Utilisateurs_PK PRIMARY KEY (utilisateur_id)
	,CONSTRAINT Utilisateurs_Email_UK UNIQUE (utilisateur_email)
)ENGINE=InnoDB;

#------------------------------------------------------------
# Table: Utilisateurs Back Office
#------------------------------------------------------------

CREATE TABLE Utilisateurs_bo(
        utilisateur_bo_id   			Int  Auto_increment  NOT NULL COMMENT "Identifiant de l'utilisateur",
        utilisateur_bo_login            Varchar (50) NOT NULL COMMENT "Login"  ,
        utilisateur_bo_mot_de_passe     Varchar (255) NOT NULL COMMENT "Mot de passe",
        utilisateur_bo_role     		Varchar (20) NOT NULL COMMENT "Rôle de l'utilisateur"
	,CONSTRAINT Utilisateurs_bo_PK PRIMARY KEY (utilisateur_bo_id)
	
	,CONSTRAINT utilisateur_bo_login_UK UNIQUE (utilisateur_bo_login)
)ENGINE=InnoDB;

#------------------------------------------------------------
# Table: Adresses
#------------------------------------------------------------

CREATE TABLE Adresses(
        adresse_id                  		Int  Auto_increment  NOT NULL COMMENT "Identifiant de l'adresse"  ,
        adresse_utilisateur_id              Int NOT NULL COMMENT "Identifiant de l'utilisateur" ,
        adresse_adresse                     Varchar (150) NOT NULL COMMENT "Adresse (N° de voie et nom de la rue)"  ,
        adresse_complement                  Varchar (255) NOT NULL COMMENT "Complément d'adresse (Etage, batiment ...)"  ,
        adresse_code_postal                 Char (5) NOT NULL COMMENT "Code postal"  ,
        adresse_ville                       Varchar (50) NOT NULL COMMENT "Ville"  ,
        adresse_departement                 Varchar (30) NOT NULL COMMENT "Département"  ,
        adresse_pays                        Varchar (50) NOT NULL COMMENT "Pays"  ,
        adresse_longitude                   Decimal (15,10) NOT NULL COMMENT "Longitude"  ,
        adresse_latitude                    Decimal (15,10) NOT NULL COMMENT "Latitude"
	,CONSTRAINT Adresses_PK PRIMARY KEY (adresse_id)

	,CONSTRAINT Adresses_Utilisateurs_FK FOREIGN KEY (adresse_utilisateur_id) REFERENCES Utilisateurs(utilisateur_id) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Categories
#------------------------------------------------------------

CREATE TABLE Categories(
        categorie_id 			Int  Auto_increment  NOT NULL COMMENT "Identifiant de la catégorie"  ,
        categorie_libelle      	Varchar (50) NOT NULL COMMENT "Libellé" 
	,CONSTRAINT Categories_PK PRIMARY KEY (categorie_id)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Sports
#------------------------------------------------------------

CREATE TABLE Sports(
        sport_id 		Int  Auto_increment  NOT NULL COMMENT "Identifiant du sport"  ,
        sport_libelle  	Varchar (50) NOT NULL COMMENT "Libellé" 
	,CONSTRAINT Sports_PK PRIMARY KEY (sport_id)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Materiels
#------------------------------------------------------------

CREATE TABLE Materiels(
        materiel_id          			 Int  Auto_increment  NOT NULL COMMENT "Identifiant du matériel"  ,
        materiel_categorie_id            Int  COMMENT "Identifiant de la catégorie"  ,
        materiel_sport_id                Int  COMMENT "Identifiant du sport"  ,
        materiel_adresse_id              Int  COMMENT "Identifiant de l'adresse"  ,
        materiel_nom                     Varchar (50) NOT NULL COMMENT "Nom"  ,
        materiel_description             Varchar (255) NOT NULL COMMENT "Description"  ,
        materiel_prix                    Decimal (10,2) NOT NULL COMMENT "Prix de la location"  ,
        materiel_caution                 TINYINT NOT NULL COMMENT "Caution (Vrai si une caution est demandée)" DEFAULT 1  ,
        materiel_caution_prix            Decimal (10,2) NOT NULL COMMENT "Prix de la caution"
	,CONSTRAINT Materiels_PK PRIMARY KEY (materiel_id)

	,CONSTRAINT Materiels_Categories_FK FOREIGN KEY (materiel_categorie_id) REFERENCES Categories(categorie_id) ON DELETE CASCADE ON UPDATE CASCADE
	,CONSTRAINT Materiels_Sports_FK FOREIGN KEY (materiel_sport_id) REFERENCES Sports(sport_id) ON DELETE CASCADE ON UPDATE CASCADE
	,CONSTRAINT Materiels_Adresses_FK FOREIGN KEY (materiel_adresse_id) REFERENCES Adresses(adresse_id) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Annonces
#------------------------------------------------------------

CREATE TABLE Annonces(
        annonce_id                  		Int  Auto_increment  NOT NULL COMMENT "Identifiant de l'annonce"  ,
        annonce_utilisateur_id              Int  COMMENT "Identifiant de l'utilisateur"  ,
        annonce_materiel_id                 Int  COMMENT "Identifiant du matériel"  ,
        annonce_titre                       Varchar (50) NOT NULL COMMENT "Titre"  ,
        annonce_description                 Varchar (255) NOT NULL COMMENT "Description"  ,
        annonce_date_parution               Date NOT NULL COMMENT "Date de parution",
        annonce_valider		                TINYINT NOT NULL COMMENT "Validation de l'annonce" DEFAULT 0
	,CONSTRAINT Annonces_PK PRIMARY KEY (annonce_id)

	,CONSTRAINT Annonces_Utilisateurs_FK FOREIGN KEY (annonce_utilisateur_id) REFERENCES Utilisateurs(utilisateur_id) ON DELETE CASCADE ON UPDATE CASCADE
	,CONSTRAINT Annonces_Materiels_FK FOREIGN KEY (annonce_materiel_id) REFERENCES Materiels(materiel_id) ON DELETE CASCADE ON UPDATE CASCADE
	,CONSTRAINT Annonces_Materiels_AK UNIQUE (annonce_materiel_id)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Commentaires
#------------------------------------------------------------

CREATE TABLE Commentaires(
        commentaire_id      		Int  Auto_increment  NOT NULL COMMENT "Identifiant du commentaire"  ,
        commentaire_annonce_id      Int COMMENT "Identifiant de l'annonce"  ,
        commentaire_utilisateur_id  Int COMMENT "Identifiant de l'utilisateur"  ,
        commentaire_note            Int COMMENT "Note donnée à l'annonce"  ,
        commentaire_message         Varchar (255) COMMENT "Message",
		commentaire_date_parution   Date COMMENT "Date de parution du commentaire",
        commentaire_valider		    TINYINT NOT NULL COMMENT "Validation du commentaire" DEFAULT 0
	,CONSTRAINT Commentaires_PK PRIMARY KEY (commentaire_id)

	,CONSTRAINT Commentaires_Annonces_FK FOREIGN KEY (commentaire_annonce_id) REFERENCES Annonces(annonce_id) ON DELETE CASCADE ON UPDATE CASCADE
	,CONSTRAINT Commentaires_Utilisateurs_FK FOREIGN KEY (commentaire_utilisateur_id) REFERENCES Utilisateurs(utilisateur_id) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Visuels
#------------------------------------------------------------

CREATE TABLE Visuels(
        visuel_id             Int  Auto_increment  NOT NULL COMMENT "Identifiant du visuel"  ,
        visuel_materiel_id    Int COMMENT "Identifiant du matériel"  ,
        visuel_nom_fichier    Varchar (400) NOT NULL COMMENT "Nom du fichier image"
	,CONSTRAINT Visuels_PK PRIMARY KEY (visuel_id)

	,CONSTRAINT Visuels_Materiels_FK FOREIGN KEY (visuel_materiel_id) REFERENCES Materiels(materiel_id) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Sorties
#------------------------------------------------------------

CREATE TABLE Sorties(
        sortie_id             		Int  Auto_increment  NOT NULL COMMENT "Identifiant de la sortie"  ,
        sortie_materiel_id    		Int  COMMENT "Identifiant du matériel"  ,
        sortie_client_id  			Int  COMMENT "Identifiant du client"  DEFAULT 0,
        sortie_etat                 Varchar (20)   COMMENT "Etat de la sortie (Réserver, Sortie, ...)"  ,
        sortie_date_sortie          Date   COMMENT "Date de sortie"  ,
        sortie_date_retour          Date  NULL COMMENT "Date de retour"
	,CONSTRAINT Sorties_PK PRIMARY KEY (sortie_id)

	,CONSTRAINT Sorties_Materiels_FK FOREIGN KEY (sortie_materiel_id) REFERENCES Materiels(materiel_id) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB;

#------------------------------------------------------------
# Insertion des valeurs initiales
#------------------------------------------------------------


#------------------------------------------------------------
# Insert: Utilisateur du back office
#------------------------------------------------------------

############ ATTENTION LE MOT DE PASSE "P@ssw0rd" A ETE CRYPTE AVEC LA LIBRAIRIE "jHash.jar" créée par Christophe Michard ##########
############MOT DE PASSE "dsi" POUR LOGIN "dsi"##############
### Les mots de passe des comptes créés sont :"admin", "dsi", "a"
INSERT INTO Utilisateurs_bo (utilisateur_bo_login, utilisateur_bo_mot_de_passe, utilisateur_bo_role) VALUES ('admin', 'b03ddf3ca2e714a6548e7495e2a03f5e824eaac9837cd7f159c67b90fb4b7342', 'administrateur');
INSERT INTO Utilisateurs_bo (utilisateur_bo_login, utilisateur_bo_mot_de_passe, utilisateur_bo_role) VALUES ('dsi', 'c8b8e57e7471cb8dd918801e517391c41011975f4f5d7fbe4bc5f80435fe12b4', 'administrateur');
INSERT INTO Utilisateurs_bo (utilisateur_bo_login, utilisateur_bo_mot_de_passe, utilisateur_bo_role) VALUES ('a', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', 'administrateur');

#------------------------------------------------------------
# Insertion des valeurs de test
#------------------------------------------------------------

#------------------------------------------------------------
# Insert: Utilisateurs
#------------------------------------------------------------
### Le mot de passe du compte créé est : ""

INSERT INTO Utilisateurs (utilisateur_nom, utilisateur_prenom, utilisateur_tel_fix, utilisateur_tel_mob, utilisateur_email, utilisateur_mot_de_passe, utilisateur_compte_actif, utilisateur_cle_mdp, utilisateur_date_inscription) 
	VALUES ('MOQUET', 'Alexis', '06 61 83 92 07', '06 61 83 92 07', 'alexis.moquet@dsi-ap.com', '$2y$10$UyXEYppMuVnYN3Vd8l/enu3UoLr9zPTOXuQGWiZ/h4GQejoCJvlH.', 1, "", '2014-04-04');

#------------------------------------------------------------
# Insert: Adresses
#------------------------------------------------------------

INSERT INTO Adresses (adresse_utilisateur_id, adresse_adresse, adresse_complement, adresse_code_postal, adresse_ville, adresse_departement, adresse_pays, adresse_longitude, adresse_latitude) 
	VALUES (1, '', '','44300', 'NANTES', 'LOIRE ATLANTIQUE', 'FRANCE', 47.336869,  -1.960758);
	
#------------------------------------------------------------
# Insert: Catégories
#------------------------------------------------------------

INSERT INTO Categories (categorie_libelle) VALUES ('Raquette');


#------------------------------------------------------------
# Insert: Sports
#------------------------------------------------------------

INSERT INTO Sports (sport_libelle) VALUES ('Tennis');

	
#------------------------------------------------------------
# Insert: Matériels
#------------------------------------------------------------

INSERT INTO Materiels (materiel_categorie_id, materiel_sport_id, materiel_adresse_id, materiel_nom, materiel_description, materiel_prix, materiel_caution, materiel_caution_prix) 
	VALUES (1, 1, 1, 'Raquette de tennis', 'Raquette Babolar quasi neuve', 150, 1, 0);
	

#------------------------------------------------------------
# Insert: Visuels
#------------------------------------------------------------

# "Nom de l'image obtenu avec la fonction [md5(uniqid('', true))]"

INSERT INTO Visuels (visuel_materiel_id, visuel_nom_fichier) 
	VALUES (1, 'raquetteBabolar.jpg');
	
	

#------------------------------------------------------------
# Procédures Stockées
#------------------------------------------------------------

#------------------------------------------------------------
# Suppresssion des demandes de réinitialisation de mot de passe dépassées
#------------------------------------------------------------

DELIMITER |
CREATE PROCEDURE SupressionDemandeMdp()

BEGIN
	DECLARE nbOccurence int DEFAULT 0;
	DECLARE i int DEFAULT 0;
	DECLARE utilisateurId int DEFAULT 0;
	DECLARE dateInit date DEFAULT '';
	DECLARE dateDuJour date DEFAULT CURRENT_DATE();
	
	#"Nombres d'utilisateurs dans la table"
	SELECT COUNT(*) INTO nbOccurence FROM Utilisateurs;
	
	#"Pour tous utilisateur"
	WHILE i < nbOccurence DO
		#"Sélection de l'utilisateur 'i'"
		SELECT utilisateur_id, utilisateur_date_init_mdp into utilisateurId, dateInit FROM Utilisateurs LIMIT i,1;
		
		#"Contrôle de la différence de date"
		IF (DATEDIFF(dateDuJour, dateInit) > 1) THEN
			#"Si la demande date de plus d'un jour"
			UPDATE Utilisateurs SET utilisateur_cle_mdp = '', utilisateur_date_init_mdp = NULL WHERE utilisateur_id = utilisateurId;
		END IF;
		
		#"Utilisateur suivant"
		SET i = i+1;
	END WHILE;
END |

DELIMITER ;

CREATE DEFINER=`root`@`localhost` EVENT `sdrMdp_Event` ON SCHEDULE EVERY 1 MINUTE ON COMPLETION PRESERVE ENABLE DO call sdrMdp();
SET GLOBAL event_scheduler="ON";
	