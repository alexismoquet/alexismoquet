package com.dsi.model.beans;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Classe Annonce
 *
 * @author Alexis Moquet
 * @since Créé le 24/02/2020
 */
public class Materiel implements List<Materiel> {

    //#################
    //### Attributs ###
    //#################

    private int materiel_id;
    private int materiel_categorie_id;
    private int materiel_sport_id;
    private int materiel_adresse_id;
    private String materiel_nom;
    private String materiel_description;
    private double materiel_prix;
    private boolean materiel_caution;
    private double materiel_caution_prix;

    //#################
    //### Constructeurs
    //#################
    /**
     * Constructeur par defaut
     */
    public Materiel() {
    }

    /**
     * Constructeur
     * @param materiel_id - id du materiel
     * @param materiel_categorie_id - id de la categorie du materiel
     * @param materiel_sport_id - id du sport du materiel
     * @param materiel_adresse_id - id de l'adresse du materiel
     * @param materiel_nom - nom du materiel
     * @param materiel_description - description du materiel
     * @param materiel_prix - prix du materiel
     * @param materiel_caution - booleen pour savoir s'il y a une caution pour le materiel
     * @param materiel_caution_prix - montant de la caution du materiel
     */
    public Materiel(int materiel_id, int materiel_categorie_id, int materiel_sport_id, int materiel_adresse_id, String materiel_nom, String materiel_description, double materiel_prix, boolean materiel_caution, double materiel_caution_prix) {
        this.materiel_id = materiel_id;
        this.materiel_categorie_id = materiel_categorie_id;
        this.materiel_sport_id = materiel_sport_id;
        this.materiel_adresse_id = materiel_adresse_id;
        this.materiel_nom = materiel_nom;
        this.materiel_description = materiel_description;
        this.materiel_prix = materiel_prix;
        this.materiel_caution = materiel_caution;
        this.materiel_caution_prix = materiel_caution_prix;
    }

    //#######################
    //### Getters and setters
    //#######################

    /**
     * Retourne l'identifiant du materiel
     * @return int: Identifiant materiel
     */
    public int getMateriel_id() {
        return materiel_id;
    }
    /**
     * Défini l'identifiant du materiel
     * @param materiel_id - id du matriel
     */
    public void setMateriel_id(int materiel_id) {
        this.materiel_id = materiel_id;
    }

    /**
     * Retourne l'identifiant de la catégorie du materiel
     * @return int: materiel_categorie_id materiel
     */
    public int getMateriel_categorie_id() {
        return materiel_categorie_id;
    }
    /**
     * Défini l'identifiant de la catégorie du materiel
     * @param materiel_categorie_id - id de la categorie du materiel
     */
    public void setMateriel_categorie_id(int materiel_categorie_id) {
        this.materiel_categorie_id = materiel_categorie_id;
    }

    /**
     * Retourne l'identifiant du sport pour un materiel
     * @return int: Identifiant sport
     */
    public int getMateriel_sport_id() {
        return materiel_sport_id;
    }
    /**
     * Défini l'identifiant du sport du materiel
     * @param materiel_sport_id - id du sport du materiel
     */
    public void setMateriel_sport_id(int materiel_sport_id) {
        this.materiel_sport_id = materiel_sport_id;
    }

    /**
     * Retourne l'identifiant de l'adresse du materiel
     * @return int: Identifiant adresse
     */
    public int getMateriel_adresse_id() {
        return materiel_adresse_id;
    }
    /**
     * Défini l'identifiant de l'adresse du materiel
     * @param materiel_adresse_id - id de l'adresse du materiel
     */
    public void setMateriel_adresse_id(int materiel_adresse_id) {
        this.materiel_adresse_id = materiel_adresse_id;
    }

    /**
     * Retourne le nom du materiel
     * @return string: nom materiel
     */
    public String getMateriel_nom() {
        return materiel_nom;
    }
    /**
     * Défini le nom du materiel
     * @param materiel_nom - nom du materiel
     */
    public void setMateriel_nom(String materiel_nom) {
        this.materiel_nom = materiel_nom;
    }

    /**
     * Retourne la description du materiel
     * @return String: deszcription materiel
     */
    public String getMateriel_description() {
        return materiel_description;
    }
    /**
     * Défini la description du materiel
     * @param materiel_description - description du materiel
     */
    public void setMateriel_description(String materiel_description) {
        this.materiel_description = materiel_description;
    }

    /**
     * Retourne le prix du materiel
     * @return double : prix materiel
     */
    public double getMateriel_prix() {
        return materiel_prix;
    }
    /**
     * Défini le prix du materiel
     * @param materiel_prix - prix du materiel
     */
    public void setMateriel_prix(double materiel_prix) {
        this.materiel_prix = materiel_prix;
    }

    /**
     * Retourne un boolean pour savoir s'il y a une caution pour le materiel
     * @return boolean: caution materiel
     */
    public boolean isMateriel_caution() {
        return materiel_caution;
    }
    /**
     * Défini s'il y a une caution pour le materiel
     * @param materiel_caution - booleen de caution du materiel
     */
    public void setMateriel_caution(boolean materiel_caution) {
        this.materiel_caution = materiel_caution;
    }

    /**
     * Retourne le montant de la caution du materiel
     * @return double: montant caution materiel
     */
    public double getMateriel_caution_prix() {
        return materiel_caution_prix;
    }
    /**
     * Défini le montant de la caution pour le materiel
     * @param materiel_caution_prix - montant de la caution du materiel
     */
    public void setMateriel_caution_prix(double materiel_caution_prix) {
        this.materiel_caution_prix = materiel_caution_prix;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<Materiel> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(Materiel materiel) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Materiel> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Materiel> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Materiel get(int index) {
        return null;
    }

    @Override
    public Materiel set(int index, Materiel element) {
        return null;
    }

    @Override
    public void add(int index, Materiel element) {

    }

    @Override
    public Materiel remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<Materiel> listIterator() {
        return null;
    }

    @Override
    public ListIterator<Materiel> listIterator(int index) {
        return null;
    }

    @Override
    public List<Materiel> subList(int fromIndex, int toIndex) {
        return null;
    }
}//fin class
