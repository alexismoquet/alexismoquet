package com.dsi.model.beans;

import com.dsi.librairies.Roles;

import java.io.Serializable;

/**
 * Classe UtilisateurBo
 *
 * @author Christophe Michard
 * @since Créé le 05/02/2020
 */
public class UtilisateurBo implements Serializable {

//#################
//### Attributs ###
//#################

    private int    idUtilisateur;
    private String login;
    private String mdp;
    private Roles  role;


//#####################
//### Constructeurs ###
//#####################

    /**
     * Constructeur par defaut
     */
    public UtilisateurBo() {}

    /**
     * Constructeur
     * @param pIdUtilisateur
     * @param pLogin
     * @param pRole
     */
    public UtilisateurBo(int pIdUtilisateur, String pLogin, String pMdp, Roles pRole) {
        this.setIdUtilisateur(pIdUtilisateur);
        this.setLogin(pLogin);
        this.setMdp(pMdp);
        this.setRole(pRole);
    }


//###############
//### Getters ###
//###############

    /**
     * Retourne l'identifiant de l'utilisateur
     * @return
     */
    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    /**
     * Retourne le login
     * @return
     */
    public String getLogin() {
        return login;
    }

    /**
     * Retourne le mot de passe crypté
     * @return
     */
    public String getMdp() {
        return mdp;
    }

    /**
     * Retourne le rôle
     * @return
     */
    public Roles getRole() {
        return role;
    }


//###############
//### Setters ###
//###############

    /**
     * Defini l'identifiant de l'utilisateur
     * @param idUtilisateur
     */
    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    /**
     * Defini le login
     * @param login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Defini le mot de passe crypté
     * @param mdp
     */
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    /**
     * Defini le rôle
     * @param role
     */
    public void setRole(Roles role) {
        this.role = role;
    }


//################
//### Méthodes ###
//################


    @Override
    public String toString() {
        return "UtilisateurBo{" +
                "idUtilisateur =" + getIdUtilisateur() +
                ", nom ='" + getLogin() + '\'' +
                ", mot de passe ='" + getMdp() + '\'' +
                ", role ='" + getRole() + '\'' +
                '}';
    }

}
