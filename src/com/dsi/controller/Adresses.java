package com.dsi.controller;

import com.dsi.model.beans.Adresse;
import com.dsi.model.bll.AdresseManager;
import com.dsi.model.bll.BLLException;

import java.util.List;

public class Adresses {
    public List<Adresse> remplirTableAdresse() throws BLLException {
        AdresseManager am = AdresseManager.getInstance();
        return am.SelectAll();
    }
}
