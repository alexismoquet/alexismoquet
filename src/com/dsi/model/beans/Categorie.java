package com.dsi.model.beans;

import java.io.Serializable;

/**
 * Classe Annonce
 *
 * @author Alexis Moquet
 * @since Créé le 24/02/2020
 */
    public class Categorie implements Serializable {
        //#################
        //### Attributs ###
        //#################

        private int categorie_id;
        private String categorie_libelle;

        //#################
        //### Constructeurs
        //#################
    /**
     * Constructeur par defaut
     */
        public Categorie() {
        }

    /**
     * Constructeur
     * @param categorie_id - id de la categorie
     * @param categorie_libelle - libelle de la categorie
     */
        public Categorie(int categorie_id, String categorie_libelle) {
            this.categorie_id = categorie_id;
            this.categorie_libelle = categorie_libelle;
        }

        //#######################
        //### Getters and setters
        //#######################

    /**
     * Retourne l'identifiant de la catégorie
     * @return int: Identifiant catégorie
     */
        public int getCategorie_id() {
            return categorie_id;
        }
    /**
     * Défini l'identifiant de la catégorie
     * @param categorie_id - id de la categorie
     */
        public void setCategorie_id(int categorie_id) {
            this.categorie_id = categorie_id;
        }

    /**
     * Retourne le libellé de la catégorie
     * @return String: libellé catégorie
     */
        public String getCategorie_libelle() {
            return categorie_libelle;
        }
    /**
     * Défini le libellé de la catégorie
     * @param categorie_libelle - libellé de la catégorie
     */
        public void setCategorie_libelle(String categorie_libelle) {
            this.categorie_libelle = categorie_libelle;
        }

}//fin class
