package com.dsi.controller;

import com.dsi.model.beans.Materiel;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.MaterielManager;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.util.List;

public class Materiels {
    public static List<Materiel> remplirJTableWithMaterielsIdSport(int idSport) throws BLLException {
        MaterielManager mm = MaterielManager.getInstance();
        return mm.SelectByIdSport(idSport);
    }

    public static List<Materiel> remplirJTableWithMaterielsIdAdresse(int idAdresse) throws BLLException {
        MaterielManager mm = MaterielManager.getInstance();
        return mm.SelectByIdAdresse(idAdresse);
    }

    public static List<Materiel> remplirJTableWithMaterielsIdCategorie(int idCategorie) throws BLLException {
        MaterielManager mm = MaterielManager.getInstance();
        return mm.SelectByIdCategorie(idCategorie);
    }

    public static List<Materiel> remplirJTableWithAllMateriels() throws BLLException {
        MaterielManager mm = MaterielManager.getInstance();
        return mm.SelectAll();
    }
    public static List<Materiel> remplirJTableWithIdMateriels(int idMateriel) throws BLLException {
        MaterielManager mm = MaterielManager.getInstance();
        return mm.SelectById(idMateriel);
    }


}
