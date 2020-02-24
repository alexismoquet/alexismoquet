package com.dsi.model.beans;

/**
 * Classe Annonce
 *
 * @author Alexis Moquet
 * @since Créé le 24/02/2020
 */
    public class Categorie {
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
     * @param categorie_id
     * @param categorie_libelle
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
     * @param categorie_id
     */
        public void setCategorie_id(int categorie_id) {
            this.categorie_id = categorie_id;
        }

    /**
     * Retourne le libellé de la catégorie
     * @return int: libellé catégorie
     */
        public String getCategorie_libelle() {
            return categorie_libelle;
        }
    /**
     * Défini le libellé de la catégorie
     * @param categorie_libelle
     */
        public void setCategorie_libelle(String categorie_libelle) {
            this.categorie_libelle = categorie_libelle;
        }

    }//fin class
