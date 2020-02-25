package com.dsi.model.beans;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Classe SportTest
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
class SportTest {

    Sport sport = new Sport();

    @Test
    void getSport_id() {
        sport.setSport_id(1);
        assertEquals(1, sport.getSport_id());
    }

    @Test
    void getSport_libelle() {
        sport.setSport_libelle("sport libelle");
        assertEquals("sport libelle", sport.getSport_libelle());
    }
}//fin class