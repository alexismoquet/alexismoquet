package com.dsi.model.beans;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Classe MaterielTest
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
class MaterielTest {

    Materiel materiel = new Materiel();

    @Test
    void getMateriel_id() {
        materiel.setMateriel_id(1);
        assertEquals(1, materiel.getMateriel_id());
    }

    @Test
    void getMateriel_categorie_id() {
        materiel.setMateriel_categorie_id(1);
        assertEquals(1, materiel.getMateriel_categorie_id());
    }

    @Test
    void getMateriel_sport_id() {
        materiel.setMateriel_sport_id(1);
        assertEquals(1, materiel.getMateriel_sport_id());
    }

    @Test
    void getMateriel_adresse_id() {
        materiel.setMateriel_adresse_id(1);
        assertEquals(1, materiel.getMateriel_adresse_id());
    }

    @Test
    void getMateriel_nom() {
        materiel.setMateriel_nom("nom materiel");
        assertEquals("nom materiel", materiel.getMateriel_nom());
    }
}//fin class