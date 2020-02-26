package com.dsi.model.bll;

import com.dsi.model.beans.Categorie;
import com.dsi.model.dal.DAO_Categorie;
import com.dsi.model.dal.DAO_Factory;

import java.util.List;

/**
 * Classe CategorieManager
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
public class CategorieManager implements Manager<Categorie> {

    private static CategorieManager instance = null;

    private DAO_Categorie dao = null;
    private Categorie categorie;
    private List<Categorie> categories;

    /**
     * Constructeur
     */
    private CategorieManager() { dao = DAO_Factory.getDAO_Categorie(); }

    /**
     * Singleton
     * @return
     */
    public static CategorieManager getInstance(){
        if (instance == null){
            instance = new CategorieManager();
        }

        return instance;
    }

    @Override
    public void insert(Categorie pObj) throws BLLException {

    }

    @Override
    public void update(Categorie pObj) throws BLLException {

    }

    @Override
    public void delete(Categorie pObj) throws BLLException {

    }

    @Override
    public List<Categorie> SelectAll() throws BLLException {
        return null;
    }

    @Override
    public Categorie SelectById(int pId) throws BLLException {
        return null;
    }
}
