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
class UtilisateurTest {

    private Utilisateur utilisateur = new Utilisateur();

    @Test
    void getIdUtilisateur() {
        utilisateur.setIdUtilisateur(1);
        assertEquals(1, utilisateur.getIdUtilisateur());
    }

    @Test
    void getNom() {
        utilisateur.setNom(1);
        assertEquals(1, utilisateur.getNom());
    }

    @Test
    void getPrenom() {
        utilisateur.setPrenom(1);
        assertEquals(1, utilisateur.getPrenom());
    }
    @Test
    void getTelFix() {
        utilisateur.setTelFix(1);
        assertEquals(1, utilisateur.getTelFix());
    }
    @Test
    void getTelMob() {
        utilisateur.setTelMob(1);
        assertEquals(1, utilisateur.getTelMob());
    }
    @Test
    void getMotDePasse() {
        utilisateur.setMotDePasse(1);
        assertEquals(1, utilisateur.getMotDePasse());
    }
    @Test
    void getEmail() {
        utilisateur.setEmail(1);
        assertEquals(1, utilisateur.getEmail());
    } @Test
    void getDateInscription() {
        utilisateur.setDateInscription(1);
        assertEquals(1, utilisateur.getDateInscription());
    } @Test
    void getAdresses() {
        utilisateur.setAdresses(1);
        assertEquals(1, utilisateur.getAdresses());
    }

}//fin class