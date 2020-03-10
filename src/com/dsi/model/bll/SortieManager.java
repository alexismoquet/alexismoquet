package com.dsi.model.bll;

import com.dsi.model.beans.Sortie;
import com.dsi.model.dal.DAO_Factory;
import com.dsi.model.dal.DAO_Sortie;

import java.util.List;

public class SortieManager implements  Manager<Sortie> {

    private static SortieManager instance = null;

    private DAO_Sortie dao = null;
    private Sortie sortie;
    private List<Sortie> sorties;

    /**
     * Constructeur
     */
    private SortieManager() { dao = DAO_Factory.getDAO_Sortie(); }

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

    }

    @Override
    public void update(Sortie pObj) throws BLLException {

    }

    @Override
    public void delete(Sortie pObj) throws BLLException {

    }

    @Override
    public void delete(int pIdMateriel) throws BLLException {

    }

    @Override
    public List<Sortie> SelectAll() throws BLLException {
        return null;
    }

    @Override
    public Sortie SelectById(int pId) throws BLLException {
        return null;
    }
}
