package com.dsi.controller;

import com.dsi.model.beans.Annonce;
import com.dsi.model.bll.AnnonceManager;
import com.dsi.model.bll.BLLException;

import java.util.List;

public class Annonces {
    public static List<Annonce> remplirJTableWithAnnonces() throws BLLException {
        AnnonceManager am = AnnonceManager.getInstance();

        return am.SelectAll();
    }
}
