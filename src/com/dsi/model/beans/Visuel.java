package com.dsi.model.beans;
/**
 * Classe Annonce
 *
 * @author Alexis Moquet
 * @since Créé le 24/02/2020
 */
public class Visuel {

    //#################
    //### Attributs ###
    //#################
    private int visuel_id;
    private int visuel_materiel_id;
    private String visuel_nom_fichier;


    //#################
    //### Constructeurs
    //#################
    /**
     * Constructeur par defaut
     */
    public Visuel() {
    }
    /**
     * Constructeur
     * @param visuel_id
     * @param visuel_materiel_id
     * @param visuel_nom_fichier
     */
    public Visuel(int visuel_id, int visuel_materiel_id, String visuel_nom_fichier) {
        this.visuel_id = visuel_id;
        this.visuel_materiel_id = visuel_materiel_id;
        this.visuel_nom_fichier = visuel_nom_fichier;
    }


    //#######################
    //### Getters and setters
    //#######################
    /**
     * Retourne l'identifiant du visuel
     * @return int: id visuel
     */
    public int getVisuel_id() {
        return visuel_id;
    }
    /**
     * Défini l'identifiant du visuel
     * @param visuel_id
     */
    public void setVisuel_id(int visuel_id) {
        this.visuel_id = visuel_id;
    }

    /**
     * Retourne l'identifiant du materiel du visuel
     * @return int: id materiel visuel
     */
    public int getVisuel_materiel_id() {
        return visuel_materiel_id;
    }
    /**
     * Défini l'identifiant du materiel du visuel
     * @param visuel_materiel_id
     */
    public void setVisuel_materiel_id(int visuel_materiel_id) {
        this.visuel_materiel_id = visuel_materiel_id;
    }

    /**
     * Retourne le nom de fichier du visuel
     * @return String: nom fichier visuel
     */
    public String getVisuel_nom_fichier() {
        return visuel_nom_fichier;
    }
    /**
     * Défini le nom de fichier du visuel
     * @param visuel_nom_fichier
     */
    public void setVisuel_nom_fichier(String visuel_nom_fichier) {
        this.visuel_nom_fichier = visuel_nom_fichier;
    }

}//fin class
