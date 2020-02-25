package com.dsi.model.beans;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Classe VisuelTest
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
class VisuelTest {

    Visuel visuel = new Visuel();

    @Test
    void getVisuel_id() {
        visuel.setVisuel_id(1);
        assertEquals(1, visuel.getVisuel_id());
    }

    @Test
    void getVisuel_materiel_id() {
        visuel.setVisuel_materiel_id(1);
        assertEquals(1, visuel.getVisuel_materiel_id());
    }

    @Test
    void getVisuel_nom_fichier() {
        visuel.setVisuel_nom_fichier("nom fichier");
        assertEquals("nom fichier", visuel.getVisuel_nom_fichier());
    }
}//fin class