package com.dsi.controller;

import com.dsi.model.beans.Utilisateur;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.UtilisateurManager;
import java.util.List;

public class Utilisateurs {

    public List <Utilisateur> remplirTable() throws BLLException {
        UtilisateurManager um = UtilisateurManager.getInstance();
        return um.SelectAll();
    }

}//fin class
