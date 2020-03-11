package com.dsi.controller;

import com.dsi.model.beans.Sport;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.SportManager;


import java.util.List;

public class Sports {

    public static List<Sport> remplirJTableWithSports() throws BLLException {
            SportManager sm = SportManager.getInstance();

        return sm.SelectAll();
    }

}//fin class

