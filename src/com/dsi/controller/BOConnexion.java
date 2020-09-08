package com.dsi.controller;

import com.dsi.librairies.UMdp;
import com.dsi.model.beans.UtilisateurBo;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.UtilisateurBoManager;
import com.dsi.view.PageConnexionBO;
import javax.swing.*;

public class BOConnexion {

    public boolean actionIdentification(String texteLogin, String texteMotDePasse) throws BLLException {
        UtilisateurBo ubo = UtilisateurBoManager.getInstance().selectByLogin("a");
        PageConnexionBO pcbo = new PageConnexionBO();
        boolean verifMdpLogin = false;

        if (ubo.getLogin().equals(texteLogin) && ubo.getMdp().equals(texteMotDePasse)) {
            verifMdpLogin = true;
        } else {
            JOptionPane.showMessageDialog(null, "Le login et le mot de passe ne correspondent pas");
        }
      // return verifMdpLogin;
       return UMdp.mdpCompare(texteMotDePasse, ubo.getMdp());
    }

}//fin class
