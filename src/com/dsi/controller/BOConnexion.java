package com.dsi.controller;

import com.dsi.librairies.UMdp;
import com.dsi.model.beans.UtilisateurBo;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.UtilisateurBoManager;


public class BOConnexion {

    public boolean actionIdentification(String texteLogin, String texteMotDePasse) throws BLLException {

        UtilisateurBoManager ubom =  UtilisateurBoManager.getInstance();
        UtilisateurBo ubo = ubom.selectByLogin("admin");
        System.out.println(texteMotDePasse);
        return UMdp.mdpCompare(texteMotDePasse, ubo.getMdp());
    }

}//fin class
