package com.dsi.controller;

import com.dsi.model.beans.Annonce;
import com.dsi.model.bll.AnnonceManager;
import com.dsi.model.bll.BLLException;
import com.dsi.model.dal.DAO_Annonce;

import java.util.Collections;
import java.util.List;

public class Annonces {

    public static List<Annonce> remplirJTableWithAnnoncesIdUser(int idUtilisateur) throws BLLException {
        AnnonceManager am = AnnonceManager.getInstance();
        return am.SelectByIdUtilisateur(idUtilisateur);
    }

    public static List<Annonce> remplirJTableWithAnnoncesIdMateriel(int idMateriel) throws BLLException {
        AnnonceManager am = AnnonceManager.getInstance();
        return am.SelectByidMateriel(idMateriel);
    }

    public static List<Annonce> remplirJTableWithIdAnnonces(int idAnnonce) throws BLLException {
        AnnonceManager am = AnnonceManager.getInstance();
        return Collections.singletonList(am.SelectById(idAnnonce));
    }
    public static List<Annonce> remplirJTableWithAllAnnonces() throws BLLException {
        AnnonceManager am = AnnonceManager.getInstance();
        return am.SelectAll();
    }


}
