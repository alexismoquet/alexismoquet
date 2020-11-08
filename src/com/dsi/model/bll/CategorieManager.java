package com.dsi.model.bll;

import com.dsi.model.beans.Categorie;
import com.dsi.model.dal.DALException;
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
    private final DAO_Categorie dao;

    /**
     * Constructeur
     */
    public CategorieManager() { dao = DAO_Factory.getDAO_Categorie(); }

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
        try {
            dao.insert(pObj);
        } catch (DALException e) {
            throw new BLLException("Un prblème est survenu lors de l'enregistrement de la catégorie", e);
        }
    }

    @Override
    public void update(Categorie pObj) throws BLLException {
        try {
            dao.update(pObj);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la modification de la catégorie", e);
        }
    }

    @Override
    public void delete(Categorie pObj) throws BLLException {
        try {
            dao.delete(pObj);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la modification de la catégorie", e);
        }
    }


    @Override
    public List<Categorie> SelectAll() throws BLLException {
        List<Categorie> categories;
        try {
            categories = dao.selectAll();
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération des catégories", e);
        }

        return categories;
    }

    @Override
    public Categorie SelectById(int pId) throws BLLException {
        Categorie categorie ;

        try {
            categorie = dao.selectById(pId);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération de la catégorie n° : "+pId, e);
        }

        return categorie;
    }
}
