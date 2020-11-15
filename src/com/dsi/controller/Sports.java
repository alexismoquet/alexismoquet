package com.dsi.controller;

import com.dsi.model.beans.Sport;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.SportManager;


import java.util.List;

public class Sports {

    private Sports() {
    }

    public static List<Sport> remplirJTableWithAllSports() throws BLLException {
        return SportManager.getInstance().SelectAll();
    }
}//fin class

