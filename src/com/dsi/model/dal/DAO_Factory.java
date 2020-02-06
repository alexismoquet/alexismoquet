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
}
