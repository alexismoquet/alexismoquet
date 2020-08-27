package com.dsi.controller;

import com.dsi.model.beans.Annonce;
import com.dsi.model.bll.AnnonceManager;
import com.dsi.model.bll.BLLException;
import java.util.List;

public class Annonces {

     public static List<Annonce> remplirJTableWithAnnoncesIdUser(int idUtilisateur) throws BLLException {
        return AnnonceManager.getInstance().SelectByIdUtilisateur(idUtilisateur);
    }

    public static List<Annonce> remplirJTableWithAnnoncesIdMateriel(int idMateriel) throws BLLException {
        return AnnonceManager.getInstance().SelectByIdMateriel(idMateriel);
    }

    public static List<Annonce> remplirJTableWithAllAnnonces() throws BLLException {
        return AnnonceManager.getInstance().SelectAll();
    }
}
