package com.dsi.model.beans;

import java.util.Date;

/**
 * Classe Annonce
 *
 * @author Alexis Moquet
 * @since Créé le 24/02/2020
 */
public class Sortie {


    //#################
    //### Attributs ###
    //#################
    private int sortie_id;
    private int sortie_materiel_id;
    private String sortie_etat;
    private Date sortie_date_sortie;
    private Date sortie_date_retour;
    private Integer sortie_client_id;


    //#################
    //### Constructeurs
    //#################
    /**
     * Constructeur par defaut
     */
    public Sortie() {
    }

    /**
     * Constructeur
     * @param sortie_id
     * @param sortie_materiel_id
     * @param sortie_etat
     * @param sortie_date_sortie
     * @param sortie_date_retour
     * @param sortie_client_id
     */
    public Sortie(int sortie_id, int sortie_materiel_id, String sortie_etat, Date sortie_date_sortie, Date sortie_date_retour, Integer sortie_client_id) {
        this.sortie_id = sortie_id;
        this.sortie_materiel_id = sortie_materiel_id;
        this.sortie_etat = sortie_etat;
        this.sortie_date_sortie = sortie_date_sortie;
        this.sortie_date_retour = sortie_date_retour;
        this.sortie_client_id = sortie_client_id;
    }

    /**
     * Constructeur
     * @param sortie_id
     * @param sortie_materiel_id
     * @param sortie_etat
     * @param sortie_date_sortie
     * @param sortie_date_retour
     */
    public Sortie(int sortie_id, int sortie_materiel_id, String sortie_etat, Date sortie_date_sortie, Date sortie_date_retour) {
        this.sortie_id = sortie_id;
        this.sortie_materiel_id = sortie_materiel_id;
        this.sortie_etat = sortie_etat;
        this.sortie_date_sortie = sortie_date_sortie;
        this.sortie_date_retour = sortie_date_retour;
    }


    //#######################
    //### Getters and setters
    //#######################
    /**
     * Retourne l'identifiant de la sortie
     * @return int: id sortie
     */
    public int getSortie_id() {
        return sortie_id;
    }
    /**
     * Défini l'identifiant du visuel
     * @param sortie_id
     */
    public void setSortie_id(int sortie_id) {
        this.sortie_id = sortie_id;
    }

    /**
     * Retourne l'identifiant du materiel sorti
     * @return int: id materiel sorti
     */
    public int getSortie_materiel_id() {
        return sortie_materiel_id;
    }
    /**
     * Défini l'identifiant du materiel sorti
     * @param sortie_materiel_id
     */
    public void setSortie_materiel_id(int sortie_materiel_id) {
        this.sortie_materiel_id = sortie_materiel_id;
    }

    /**
     * Retourne l'état de sortie du materiel
     * @return String: etat sortie
     */
    public String getSortie_etat() {
        return sortie_etat;
    }
    /**
     * Défini l'état de sortie du materiel
     * @param sortie_etat
     */
    public void setSortie_etat(String sortie_etat) {
        this.sortie_etat = sortie_etat;
    }

    /**
     * Retourne la date de sortie du materiel
     * @return date: date materiel sorti
     */
    public Date getSortie_date_sortie() {
        return sortie_date_sortie;
    }
    /**
     * Défini l'état de sortie du materiel
     * @param sortie_date_sortie
     */
    public void setSortie_date_sortie(Date sortie_date_sortie) {
        this.sortie_date_sortie = sortie_date_sortie;
    }
    /**
     * Retourne la date de retour du materiel sorti
     * @return date: date retour materiel sorti
     */
    public Date getSortie_date_retour() {
        return sortie_date_retour;
    }
    /**
     * Défini la date de retour du materiel sorti
     * @param sortie_date_retour
     */
    public void setSortie_date_retour(Date sortie_date_retour) {
        this.sortie_date_retour = sortie_date_retour;
    }

}//fin class

