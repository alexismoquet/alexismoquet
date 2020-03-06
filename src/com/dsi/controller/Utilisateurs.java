package com.dsi.controller;

import com.dsi.model.beans.Utilisateur;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.UtilisateurManager;
import java.util.List;

public class Utilisateurs {

    public static List <Utilisateur> remplirJTableWithAllUtilisateurs() throws BLLException {
        UtilisateurManager um = UtilisateurManager.getInstance();
        List<Utilisateur> u = um.SelectAll();

        return u;
    }

}//fin class
