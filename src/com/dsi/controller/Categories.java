package com.dsi.controller;

import com.dsi.model.beans.Categorie;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.CategorieManager;
import java.util.List;


public class Categories {

    public static List<Categorie> remplirJTableWithAllCategories() throws BLLException {
        return CategorieManager.getInstance().SelectAll();
    }
}
