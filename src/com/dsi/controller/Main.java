package com.dsi.controller;

import com.dsi.model.bll.BLLException;
import com.dsi.view.PageHubAdmin;
import com.dsi.view.PageConnexionBO;
import com.dsi.view.PageUtilisateurs;

public class Main {
    public static void main(String[] args) throws BLLException {

//        PageConnexionBO pcbo = new PageConnexionBO();
//        pcbo.initialiserComposants();
//        pcbo.setVisible(true);

        PageHubAdmin ha = new PageHubAdmin();
        ha.setVisible(true);

//        PageUtilisateurs pu = new PageUtilisateurs();
//        pu.setVisible(true);

    }
}
