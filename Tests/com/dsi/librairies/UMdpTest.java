package com.dsi.librairies;

import com.dsi.model.beans.UtilisateurBo;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.UtilisateurBoManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UMdpTest {

    private String mdpValide = "P@ssw0rd"; //Mot de passe valide pour l'utilisateur admin
    private String mdpNonValide = "Password";

    //Création du manager utilisateur du back office
    private static UtilisateurBoManager ubm = UtilisateurBoManager.getInstance();

    //Récupération du mot de passe de l'utilisateur admin en base
    private static UtilisateurBo utilisateur = null;

    static {
        try {
            utilisateur = ubm.selectByLogin("admin");
        } catch (BLLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void mdpCrypte() {
        //Succes de cryptage. Le test échoue si le cryptage est mauvais
        assertEquals(UMdp.mdpCrypte(mdpValide), utilisateur.getMdp());

        //Echec de cryptage. Le test échoue si le cryptage est bon
        assertNotEquals(UMdp.mdpCrypte(mdpValide), utilisateur.getMdp()+"aze");
    }

    @Test
    void mdpCompare() {
        //Mot de passe valide. Le test échoue si les mot de passe sont différent
        assertTrue(UMdp.mdpCompare(mdpValide, utilisateur.getMdp()));

        //Mot de passe non valide. Le test échoue si les mots de passe sont identiques
        assertFalse(UMdp.mdpCompare(mdpNonValide, utilisateur.getMdp()));
    }
}
