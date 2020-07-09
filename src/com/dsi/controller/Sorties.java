package com.dsi.controller;

import com.dsi.model.beans.Sortie;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.SortieManager;

import java.util.List;

public class Sorties {

    public static List<Sortie> remplirJTableWithAllSorties()  throws BLLException {
        SortieManager sm = SortieManager.getInstance();
        return sm.SelectAll();
    }
}
