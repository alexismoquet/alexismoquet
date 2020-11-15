package com.dsi.controller;

import com.dsi.model.beans.Utilisateur;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.UtilisateurManager;
import java.util.List;

public class Utilisateurs {

    private Utilisateurs() {
    }

    public static List <Utilisateur> remplirJTableWithAllUtilisateurs() throws BLLException {
        return UtilisateurManager.getInstance().SelectAll();
    }

}//fin class
