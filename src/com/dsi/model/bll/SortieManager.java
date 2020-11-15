package com.dsi.model.bll;

import com.dsi.model.beans.Sortie;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Factory;
import com.dsi.model.dal.DAO_Sortie;
import java.util.List;

public class SortieManager implements  Manager<Sortie> {

    private static SortieManager instance = null;
    private DAO_Sortie dao = null;

    /**
     * Constructeur
     */
    public SortieManager() { dao = DAO_Factory.getDAO_Sortie(); }

    /**
     * Singleton
     * @return
     */
    public static SortieManager getInstance(){
        if (instance == null){
            instance = new SortieManager();
        }

        return instance;
    }

    @Override
    public void insert(Sortie pObj) throws BLLException {
        try {
            dao.insert(pObj);
        } catch (DALException e) {
            throw new BLLException("Un prblème est survenu lors de l'enregistrement de la sortie", e);
        }

    }

    @Override
    public void update(Sortie pObj) throws BLLException {
        try {
            dao.update(pObj);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la modification de la sortie", e);
        }

    }

    @Override
    public void delete(Sortie pObj) throws BLLException {
        try {
            dao.delete(pObj);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la modification de la sortie", e);
        }

    }

    @Override
    public List<Sortie> SelectAll() throws BLLException {
        List<Sortie> sorties;
        try {
            sorties = dao.selectAll();
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération des sports", e);
        }

        return sorties;
    }

    @Override
    public Sortie SelectById(int pId) throws BLLException {
        Sortie sortie = null;

        try {
            sortie = dao.selectById(pId);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération de la sortie n° : "+pId, e);
        }

        return sortie;
    }


    public List<Sortie> SelectByIdMateriel(int pId) throws BLLException {
        List<Sortie> sorties;

        try {
            sorties = dao.selectByIdMateriel(pId);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération de la sortie n° : "+pId, e);
        }

        return sorties;
    }

}//fin class

