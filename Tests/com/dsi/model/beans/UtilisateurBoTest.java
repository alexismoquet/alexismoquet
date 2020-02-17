package com.dsi.model.beans;

import com.dsi.librairies.Roles;
import com.dsi.librairies.UMdp;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilisateurBoTest {
    private UtilisateurBo ubo = new UtilisateurBo();

    @Test
    void getIdUtilisateur() {
        ubo.setIdUtilisateur(1);
        assertEquals(1, ubo.getIdUtilisateur());
    }

    @Test
    void getLogin() {
        ubo.setLogin("login");
        assertEquals("login", ubo.getLogin());
    }

    @Test
    void getMdp() {
        String mdp = UMdp.mdpCrypte("P@ssw0rd");

        ubo.setMdp(mdp);
        assertEquals(mdp, ubo.getMdp());
    }

    @Test
    void getRole() {
        ubo.setRole(Roles.ADMIN);
        assertEquals(Roles.ADMIN, ubo.getRole());
    }

}