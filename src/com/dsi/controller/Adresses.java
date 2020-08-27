package com.dsi.controller;

import com.dsi.model.beans.Adresse;
import com.dsi.model.bll.AdresseManager;
import com.dsi.model.bll.BLLException;

import java.util.List;

public class Adresses {
    public static List<Adresse> remplirJTableWithAdressesIdUser(int idUtilisateur) throws BLLException {
        return AdresseManager.getInstance().SelectByIdUtilisateur(idUtilisateur);
    }

    public static List<Adresse> remplirJTableWithAllAdresses() throws BLLException {
        return AdresseManager.getInstance().SelectAll();
    }
}
