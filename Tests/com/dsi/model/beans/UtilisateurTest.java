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

    private final Utilisateur utilisateur = new Utilisateur();

    @Test
    void getIdUtilisateur() {
        utilisateur.setIdUtilisateur(1);
        assertEquals(1, utilisateur.getIdUtilisateur());
    }
    @Test
    void getNom() {
        utilisateur.setNom("nom");
        assertEquals("nom", utilisateur.getNom());
    }
    @Test
    void getPrenom() {
        utilisateur.setPrenom("prenom");
        assertEquals("prenom", utilisateur.getPrenom());
    }
    @Test
    void getTelFix() {
        utilisateur.setTelFix("telFix");
        assertEquals("telFix", utilisateur.getTelFix());
    }
    @Test
    void getTelMob() {
        utilisateur.setTelMob("telMob");
        assertEquals("telMob", utilisateur.getTelMob());
    }
    @Test
    void getMotDePasse() {
        utilisateur.setMotDePasse("mdp");
        assertEquals("mdp", utilisateur.getMotDePasse());
    }
    @Test
    void getEmail() {
        utilisateur.setEmail("email");
        assertEquals("email", utilisateur.getEmail());
    }
    @Test
    void getDateInscription() {
        utilisateur.setDateInscription(new Date());
        assertEquals(new Date(), utilisateur.getDateInscription());
    }
//    @Test
//    void getAdresses() {
//        utilisateur.setAdresses(utilisateur.setAdresses(Adresse.get(0));
//        assertEquals(adresses, utilisateur.getAdresses());
//    }

}//fin class