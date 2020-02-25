package com.dsi.model.beans;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe AnnonceTest
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
class AnnonceTest {

    private Annonce annonce = new Annonce();

    @Test
    void getAnnonce_id() {
        annonce.setAnnonce_id(1);
        assertEquals(1, annonce.getAnnonce_id());
    }

    @Test
    void getAnnonce_utilisateur_id() {
        annonce.setAnnonce_utilisateur_id(1);
        assertEquals(1, annonce.getAnnonce_utilisateur_id());
    }

    @Test
    void getAnnonce_materiel_id() {
        annonce.setAnnonce_materiel_id(1);
        assertEquals(1, annonce.getAnnonce_materiel_id());
    }

    @Test
    void getAnnonce_titre() {
        annonce.setAnnonce_titre("Titre");
        assertEquals("Titre", annonce.getAnnonce_titre());
    }

    @Test
    void getAnnonce_description() {
        annonce.setAnnonce_description("Description");
        assertEquals("Description", annonce.getAnnonce_description());
    }

    @Test
    void getAnnonce_date_parution() {
        annonce.setAnnonce_date_parution(new Date());
        assertEquals(new Date(), annonce.getAnnonce_date_parution());
    }

}//fin class