package com.dsi.controller;

import com.dsi.model.beans.Visuel;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.VisuelManager;

import java.util.List;

public class Visuels {
    public static List<Visuel> remplirJTableWithVisuels() throws BLLException {
        VisuelManager vm = VisuelManager.getInstance();

        return vm.SelectAll();
    }
}//fin class
