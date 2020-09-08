package com.dsi.controller;

import com.dsi.model.beans.UtilisateurBo;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.UtilisateurBoManager;

import java.util.List;

public class UtilisateursBO {
    public static List<UtilisateurBo> remplirJTableWithAllUtilisateursBO() throws BLLException {
        return UtilisateurBoManager.getInstance().SelectAll();
    }

}
