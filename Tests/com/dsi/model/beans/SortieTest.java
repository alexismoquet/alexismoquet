package com.dsi.model.beans;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Classe SortieTest
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
class SortieTest {

    Sortie sortie = new Sortie();
    @Test
    void getSortie_id() {
        sortie.setSortie_id(1);
        assertEquals(1, sortie.getSortie_id());
    }

    @Test
    void getSortie_materiel_id() {
        sortie.setSortie_materiel_id(1);
        assertEquals(1, sortie.getSortie_materiel_id());
    }

    @Test
    void getSortie_etat() {
        sortie.setSortie_etat("Sortie");
        assertEquals("Sortie", sortie.getSortie_etat());
    }

    @Test
    void getSortie_date_sortie() {
        sortie.setSortie_date_sortie(new Date());
        assertEquals(new Date(), sortie.getSortie_date_sortie());
    }

    @Test
    void getSortie_date_retour() {
        sortie.setSortie_date_retour(new Date());
        assertEquals(new Date(), sortie.getSortie_date_retour());
    }

}//fin class