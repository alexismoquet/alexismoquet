package com.dsi.controller;

import com.dsi.model.bll.BLLException;
import com.dsi.view.PageConnexionBO;

public class Main {
    public static void main(String[] args) throws BLLException {

        PageConnexionBO pboc = new PageConnexionBO();
        pboc.initialiserComposants();
        pboc.setVisible(true);

    }
}
