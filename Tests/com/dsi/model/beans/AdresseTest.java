package com.dsi.model.beans;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdresseTest {

    private Adresse adresse = new Adresse();

    @Test
    void getIdAdresse() {
        adresse.setIdAdresse(1);
        assertEquals(1, adresse.getIdAdresse());
    }

    @Test
    void getIdUtilisateur() {
        adresse.setIdUtilisateur(1);
        assertEquals(1, adresse.getIdUtilisateur());
    }

    @Test
    void getAdresse() {
        adresse.setAdresse("Une adresse");
        assertEquals("Une adresse", adresse.getAdresse());
    }

    @Test
    void getComplement() {
        adresse.setComplement("Un complément");
        assertEquals("Un complément", adresse.getComplement());
    }

    @Test
    void getCodePostal() {
        adresse.setCodePostal("44260");
        assertEquals("44260", adresse.getCodePostal());
    }

    @Test
    void getVille() {
        adresse.setVille("Une ville");
        assertEquals("Une ville", adresse.getVille());
    }

    @Test
    void getDepartement() {
        adresse.setDepartement("Un département");
        assertEquals("Un département", adresse.getDepartement());
    }

    @Test
    void getPays() {
        adresse.setPays("Un pays");
        assertEquals("Un pays", adresse.getPays());
    }

    @Test
    void getLongitude() {
        adresse.setLongitude(0.0000047f);
        assertEquals(0.0000047f, adresse.getLongitude());
    }

    @Test
    void getLatitude() {
        adresse.setLatitude(0.0000047f);
        assertEquals(0.0000047f, adresse.getLatitude());
    }
}