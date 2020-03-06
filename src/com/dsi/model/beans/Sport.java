package com.dsi.model.beans;
/**
 * Classe Annonce
 *
 * @author Alexis Moquet
 * @since Créé le 24/02/2020
 */
public class Sport extends Utilisateur {

    //#################
    //### Attributs ###
    //#################

    private int sport_id;
    private String sport_libelle;


    //#################
    //### Constructeurs
    //#################
    /**
     * Constructeur par defaut
     */
    public Sport() {
    }
    /**
     * Constructeur
     * @param sport_id
     * @param sport_libelle
     */
    public Sport(int sport_id, String sport_libelle) {
        this.sport_id = sport_id;
        this.sport_libelle = sport_libelle;
    }

    //#######################
    //### Getters and setters
    //#######################

    /**
     * Retourne l'identifiant du sport
     * @return int: Identifiant sport
     */
    public int getSport_id() {
        return sport_id;
    }
    /**
     * Défini l'identifiant du sport
     * @param sport_id
     */
    public void setSport_id(int sport_id) {
        this.sport_id = sport_id;
    }

    /**
     * Retourne le libellé du sport
     * @return String: libellé du sport
     */
    public String getSport_libelle() {
        return sport_libelle;
    }
    /**
     * Défini le libellé du sport
     * @param sport_libelle
     */
    public void setSport_libelle(String sport_libelle) {
        this.sport_libelle = sport_libelle;
    }

}//fin class
