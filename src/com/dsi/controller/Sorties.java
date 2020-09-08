package com.dsi.controller;

import com.dsi.model.beans.Sortie;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.SortieManager;

import java.util.List;

public class Sorties {

    public static List<Sortie> remplirJTableWithAllSorties()  throws BLLException {
        return SortieManager.getInstance().SelectAll();
    }

    public static List<Sortie> remplirJTableSortiesWithIdMateriel( int idMateriel)  throws BLLException {
        return SortieManager.getInstance().SelectByIdMateriel(idMateriel);
    }
}
