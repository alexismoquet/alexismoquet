package com.dsi.controller;

import com.dsi.librairies.UMdp;
import com.dsi.model.beans.UtilisateurBo;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.UtilisateurBoManager;


public class BOConnexion {

    public void actionIdentification(String texteLogin, String texteMotDePasse) throws BLLException {
        UtilisateurBoManager ubom = new UtilisateurBoManager();
        UtilisateurBo ubo = ubom.selectByLogin("admin");

        if (UMdp.mdpCompare(texteMotDePasse, ubo.getMdp()))
            System.out.println("Ok");

    }

}//fin class
