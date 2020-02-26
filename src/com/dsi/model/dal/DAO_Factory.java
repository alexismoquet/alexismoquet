package com.dsi.model.dal;

/**
 * Classe DAO_Factory
 *
 * @author Christophe Michard
 * @since Créé le 05/02/2020
 */
public class DAO_Factory {

    public static DAO_UtilisateurBo getDAO_UtilisateurBo() {
        DAO_UtilisateurBo daoUtilisateurBo = null;

        try {
            daoUtilisateurBo = (DAO_UtilisateurBo) Class.forName("com.dsi.model.dal.mysql.DAOUtilisateurBo_mysql_impl").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return daoUtilisateurBo;
    }

    public static DAO_Utilisateur getDAO_Utilisateur() {
        DAO_Utilisateur daoUtilisateur = null;

        try {
            daoUtilisateur = (DAO_Utilisateur) Class.forName("com.dsi.model.dal.mysql.DAOUtilisateur_mysql_impl").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return daoUtilisateur;
    }

    public static DAO_Adresse getDAO_Adresse() {
        DAO_Adresse daoAdresse = null;

        try {
            daoAdresse = (DAO_Adresse) Class.forName("com.dsi.model.dal.mysql.DAOAdresse_mysql_impl").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return daoAdresse;
    }

    public static DAO_Annonce getDAO_Annonce() {
        DAO_Annonce daoAnnonce = null;

        try {
            daoAnnonce = (DAO_Annonce) Class.forName("com.dsi.model.dal.mysql.DAOAnnonce_mysql_impl").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return daoAnnonce;
    }

    public static DAO_Categorie getDAO_Categorie() {
        DAO_Categorie daoCategorie = null;

        try {
            daoCategorie = (DAO_Categorie) Class.forName("com.dsi.model.dal.mysql.DAOCategorie_mysql_impl").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return daoCategorie;
    }

    public static DAO_Materiel getDAO_Materiel() {
        DAO_Materiel daoMateriel = null;

        try {
            daoMateriel = (DAO_Materiel) Class.forName("com.dsi.model.dal.mysql.DAOMateriel_mysql_impl").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return daoMateriel;
    }



    public static DAO_Sortie getDAO_Sortie() {
        DAO_Sortie daoSortie = null;

        try {
            daoSortie = (DAO_Sortie) Class.forName("com.dsi.model.dal.mysql.DAOSortie_mysql_impl").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return daoSortie;
    }

    public static DAO_Sport getDAO_Sport() {
        DAO_Sport daoSport = null;

        try {
            daoSport = (DAO_Sport) Class.forName("com.dsi.model.dal.mysql.DAOSport_mysql_impl").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return daoSport;
    }

    public static DAO_Visuel getDAO_Visuel() {
        DAO_Visuel daoVisuel = null;

        try {
            daoVisuel = (DAO_Visuel) Class.forName("com.dsi.model.dal.mysql.DAOVisuel_mysql_impl").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return daoVisuel;
    }

    public static DAO_Commentaire getDAO_Commentaire() {
        DAO_Commentaire daoCommentaire = null;

        try {
            daoCommentaire = (DAO_Commentaire) Class.forName("com.dsi.model.dal.mysql.DAOCommentaire_mysql_impl").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return daoCommentaire;
    }
}//fin class
