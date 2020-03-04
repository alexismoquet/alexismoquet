package com.dsi.model.beans;

import java.io.Serializable;

/**
 * Classe Adresse
 *
 * @author Christophe Michard
 * @since Créé le 06/02/2020
 */
public class Adresse implements Serializable {

//#################
//### Attributs ###
//#################

    private int     idAdresse;
    private int     idUtilisateur;
    private String  adresse;
    private String  complement;
    private String  codePostal;
    private String  ville;
    private String  departement;
    private String  pays;
    private float   longitude;
    private float   latitude;


//#####################
//### Constructeurs ###
//#####################

    /**
     * Constructeur par défaut
     */
    public Adresse() {}

    /**
     * Constructeur
     * @param pAdresse
     * @param pCodePostal
     * @param pVille
     * @param pDepartement
     * @param pPays
     */
    public Adresse(
                   String pAdresse,
                   String pCodePostal,
                   String pVille,
                   String pDepartement,
                   String pPays) {

        this.setAdresse(pAdresse);
        this.setCodePostal(pCodePostal);
        this.setVille(pVille);
        this.setDepartement(pDepartement);
        this.setPays(pPays);
    }
    /**
     * Constructeur
     * @param pIdAdresse
     * @param pIdUtilisateur
     * @param pAdresse
     * @param pComplement
     * @param pCodePostal
     * @param pVille
     * @param pDepartement
     * @param pPays
     */
    public Adresse(int pIdAdresse,
                   int pIdUtilisateur,
                   String pAdresse,
                   String pComplement,
                   String pCodePostal,
                   String pVille,
                   String pDepartement,
                   String pPays) {
        this.setIdAdresse(pIdAdresse);
        this.setIdUtilisateur(pIdUtilisateur);
        this.setAdresse(pAdresse);
        this.setComplement(pComplement);
        this.setCodePostal(pCodePostal);
        this.setVille(pVille);
        this.setDepartement(pDepartement);
        this.setPays(pPays);
    }

    /**
     * Constructeur
     * @param pIdAdresse
     * @param pIdUtilisateur
     * @param pAdresse
     * @param pComplement
     * @param pCodePostal
     * @param pVille
     * @param pDepartement
     * @param pPays
     * @param pLongitude
     * @param pLatitude
     */
    public Adresse(int pIdAdresse,
                   int pIdUtilisateur,
                   String pAdresse,
                   String pComplement,
                   String pCodePostal,
                   String pVille,
                   String pDepartement,
                   String pPays,
                   float pLongitude,
                   float pLatitude) {
        this(pIdAdresse, pIdUtilisateur, pAdresse, pComplement, pCodePostal, pVille, pDepartement, pPays);

        this.setLongitude(pLongitude);
        this.setLatitude(pLatitude);
    }


//###############
//### Getters ###
//###############

    /**
     * Retourne l'identifiant de l'adresse
     * @return int: Identifiant de l'adresse
     */
    public int getIdAdresse() {
        return idAdresse;
    }

    /**
     * Retourne l'identifiant de l'utilisateur à qui est liée l'adresse
     * @return int: Identifiant de l'utilisateur
     */
    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    /**
     * Retourne l'adresse
     * @return String: Adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Retourne le complément d'adresse
     * @return String: Complément d'adresse
     */
    public String getComplement() {
        return complement;
    }

    /**
     * Retourne le code postal
     * @return String: Code postal
     */
    public String getCodePostal() {
        return codePostal;
    }

    /**
     * Retourne la ville
     * @return String: Ville
     */
    public String getVille() {
        return ville;
    }

    /**
     * Retourne le département
     * @return String: Département
     */
    public String getDepartement() {
        return departement;
    }

    /**
     * Retourne le pays
     * @return String: Pays
     */
    public String getPays() {
        return pays;
    }

    /**
     * Retourne la longitude
     * @return float: Longitude
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * Retourne le pays
     * @return String: Pays
     */
    public float getLatitude() {
        return latitude;
    }


//###############
//### Setters ###
//###############

    /**
     * Défini l'identifiant de l'adresse
     * @param pIdAdresse
     */
    public void setIdAdresse(int pIdAdresse) {
        this.idAdresse = pIdAdresse;
    }

    /**
     * Défini l'identifiant de l'utilisateur à qui est liée l'adresse
     * @param pIdUtilisateur
     */
    public void setIdUtilisateur(int pIdUtilisateur) {
        this.idUtilisateur = pIdUtilisateur;
    }

    /**
     * Défini l'adresse
     * @param pAdresse
     */
    public void setAdresse(String pAdresse) {
        this.adresse = pAdresse;
    }

    /**
     * Défini le complément d'adresse
     * @param pComplement
     */
    public void setComplement(String pComplement) {
        this.complement = pComplement;
    }

    /**
     * Défini le code postal
     * @param pCodePostal
     */
    public void setCodePostal(String pCodePostal) {
        this.codePostal = pCodePostal;
    }

    /**
     * Défini la ville
     * @param pVille
     */
    public void setVille(String pVille) {
        this.ville = pVille;
    }

    /**
     * Défini le département
     * @param pDepartement
     */
    public void setDepartement(String pDepartement) {
        this.departement = pDepartement;
    }

    /**
     * Défini le pays
     * @param pPays
     */
    public void setPays(String pPays) {
        this.pays = pPays;
    }

    /**
     * Défini la longitude
     * @param pLongitude
     */
    public void setLongitude(float pLongitude) {
        this.longitude = pLongitude;
    }

    /**
     * Défini la latitude
     * @param pLatitude
     */
    public void setLatitude(float pLatitude) {
        this.latitude = pLatitude;
    }


//################
//### Méthodes ###
//################

    @Override
    public String toString() {
        return "Adresse{" +
                "idAdresse=" + getIdAdresse() +
                ", idUtilisateur=" + getIdUtilisateur() +
                ", adresse='" + getAdresse() + '\'' +
                ", complement='" + getComplement() + '\'' +
                ", codePostal='" + getCodePostal() + '\'' +
                ", ville='" + getVille() + '\'' +
                ", departement='" + getDepartement() + '\'' +
                ", pays='" + getPays() + '\'' +
                ", longitude=" + getLongitude() +
                ", latitude=" + getLatitude() +
                '}';
    }
}
