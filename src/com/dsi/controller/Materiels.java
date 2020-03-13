package com.dsi.controller;

import com.dsi.model.beans.Materiel;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.MaterielManager;

import java.util.List;

public class Materiels {
    public static Materiel remplirJTableWithMateriels(int idMateriel) throws BLLException {
        MaterielManager vm = MaterielManager.getInstance();

        return vm.SelectById(idMateriel);
    }
}
