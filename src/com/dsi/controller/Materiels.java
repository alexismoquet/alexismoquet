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
        MaterielManager vm = MaterielManager.getInstance();
        return vm.SelectByIdSport(idSport);
    }

    public static List<Materiel> remplirJTableWithMaterielsIdAdresse(int idAdresse) throws BLLException {
        MaterielManager mm = MaterielManager.getInstance();
        return mm.SelectByIdAdresse(idAdresse);
    }

}
