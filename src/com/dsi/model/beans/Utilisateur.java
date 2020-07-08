package com.dsi.model.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe Utilisateur
 *
 * @author Christophe Michard
 * @since Créé le 05/02/2020
 */
public class Utilisateur implements Serializable {

//#################
//### Attributs ###
//#################

    private int             idUtilisateur;
    private String          nom;
    private String          prenom;
    private String          telFix;
    private String          telMob;
    private String          email;
    private String          motDePasse;
    private Date            dateInscription;
    private List<Adresse>   adresses;


//#####################
//### Constructeurs ###
//#####################

    /**
     * Constructeur par defaut
     */
    public Utilisateur() {
        this.adresses = new ArrayList<>();
    }

    /**
     * Constructeur
     * @param pIdUtilisateur
     * @param pNom
     * @param pPrenom
     * @param pTelFix
     * @param pTelMob
     * @param pEmail
     * @param pMotDePasse
     * @param pDateInscription
     */
    public Utilisateur(int pIdUtilisateur,
                       String pNom,
                       String pPrenom,
                       String pTelFix,
                       String pTelMob,
                       String pEmail,
                       String pMotDePasse,
                       Date pDateInscription) {
        this();

        this.setIdUtilisateur(pIdUtilisateur);
        this.setNom(pNom);
        this.setPrenom(pPrenom);
        this.setTelFix(pTelFix);
        this.setTelMob(pTelMob);
        this.setEmail(pEmail);
        this.setMotDePasse(pMotDePasse);
        this.setDateInscription(pDateInscription);
    }

    /**
     * Constructeur
     * @param pIdUtilisateur
     * @param pNom
     * @param pPrenom
     * @param pTelFix
     * @param pTelMob
     * @param pEmail
     * @param pMotDePasse
     * @param pDateInscription
     * @param pAdresses
     */
    public Utilisateur(int pIdUtilisateur,
                       String pNom,
                       String pPrenom,
                       String pTelFix,
                       String pTelMob,
                       String pEmail,
                       String pMotDePasse,
                       Date pDateInscription,
                       List<Adresse> pAdresses) {
        this(pIdUtilisateur, pNom, pPrenom, pTelFix, pTelMob, pEmail, pMotDePasse, pDateInscription);

        this.setAdresses(pAdresses);
    }


//###############
//### Getters ###
//###############

    /**
     * Retourne l'identifiant de l'utilisateur
     * @return int: Identifiant utilisateur
     */
    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    /**
     * Retourne le nom
     * @return String: Nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne le prénom
     * @return String: Prénom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Retourne le numéro de téléphone fixe
     * @return String: Numéro de téléphone fixe
     */
    public String getTelFix() {
        return telFix;
    }

    /**
     * Retourne le numéro de téléphone mobile
     * @return String: Numéro de téléphone mobile
     */
    public String getTelMob() {
        return telMob;
    }

    /**
     * Retourne l'email
     * @return String: eMail
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retourne le mot de passe crypté
     * @return
     */
    public String getMotDePasse() {
        return motDePasse;
    }

    /**
     * Retourne la date d'inscription
     * @return Date: date d'inscription
     */
    public Date getDateInscription() {
        return dateInscription;
    }

    /**
     * Retourne la liste des adresses liées à cette utilisateur
     * @return List<Adresse>: Liste des adresses
     */
    public List<Adresse> getAdresses() {
        return adresses;
    }


//###############
//### Setters ###
//###############

    /**
     * Défini l'identifiant de l'utilisateur
     * @param pIdUtilisateur
     */
    public void setIdUtilisateur(int pIdUtilisateur) {
        this.idUtilisateur = pIdUtilisateur;
    }

    /**
     * Défini le nom
     * @param pNom
     */
    public void setNom(String pNom) {
        this.nom = pNom;
    }

    /**
     * Défini le prénom
     * @param pPrenom
     */
    public void setPrenom(String pPrenom) {
        this.prenom = pPrenom;
    }

    /**
     * Défini le numéro de téléphone fixe
     * @param pTelFix
     */
    public void setTelFix(String pTelFix) {
        this.telFix = pTelFix;
    }

    /**
     * Défini le numéro de téléphone mobile
     * @param pTelMob
     */
    public void setTelMob(String pTelMob) {
        this.telMob = pTelMob;
    }

    /**
     * Défini l'adresse mail
     * @param pEmail
     */
    public void setEmail(String pEmail) {
        this.email = pEmail;
    }

    /**
     * Défini le mot de passe crypté
     * @param motDePasse
     */
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    /**
     * Défini la date d'inscription
     * @param pDateInscription
     */
    public void setDateInscription(Date pDateInscription) {
        this.dateInscription = pDateInscription;
    }

    /**
     * Défini la liste des adresses
     * @param pAdresses
     */
    public void setAdresses(List<Adresse> pAdresses) {
        this.adresses = pAdresses;
    }


//################
//### Méthodes ###
//################

    @Override
    public String toString() {
        return "Utilisateur{" +
                "idUtilisateur=" + getIdUtilisateur() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", telFix='" + getTelFix() + '\'' +
                ", telMob='" + getTelMob() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", mot de passe crypté='" + getMotDePasse() + '\'' +
                ", dateInscription='" + getDateInscription() + '\'' +
                ", adresses=" + getAdresses() +
                '}';
    }

}
