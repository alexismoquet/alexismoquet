package com.dsi.model.beans;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Classe CategorieTest
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
class CategorieTest {

    Categorie  categorie = new Categorie();

    @Test
    void getCategorie_id() {
        categorie.setCategorie_id(1);
        assertEquals(1, categorie.getCategorie_id());
    }

    @Test
    void getCategorie_libelle() {
        categorie.setCategorie_libelle("Libelle");
        assertEquals("Libelle", categorie.getCategorie_libelle());
    }

}//fin class