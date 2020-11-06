package com.dsi.controller;

import com.dsi.model.beans.Materiel;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.MaterielManager;
import java.util.List;

public class Materiels {
    private Materiels() {
    }

    public static List<Materiel> remplirJTableWithMaterielsIdSport(int idSport) throws BLLException {
        return MaterielManager.getInstance().SelectByIdSport(idSport);
    }
    public static List<Materiel> remplirJTableWithMaterielsIdAdresse(int idAdresse) throws BLLException {
        return MaterielManager.getInstance().SelectByIdAdresse(idAdresse);
    }
    public static List<Materiel> remplirJTableWithMaterielsIdCategorie(int idCategorie) throws BLLException {
        return MaterielManager.getInstance().SelectByIdCategorie(idCategorie);
    }
    public static List<Materiel> remplirJTableWithAllMateriels() throws BLLException {
        return MaterielManager.getInstance().SelectAll();
    }
    public static List<Materiel> remplirJTableWithIdMateriels(int idMateriel) throws BLLException {
        return MaterielManager.getInstance().SelectById(idMateriel);
    }
    public static List<Materiel> remplirJTableWithIdMaterielsAnnonce(int idMateriel) throws BLLException {
        return MaterielManager.getInstance().SelectById(idMateriel);
    }
    
}
