package com.dsi.model.bll;

import com.dsi.model.beans.Visuel;
import com.dsi.model.dal.DAO_Factory;
import com.dsi.model.dal.DAO_Visuel;

import java.util.List;

public class VisuelManager implements Manager<Visuel> {

    private static VisuelManager instance = null;

    private DAO_Visuel dao = null;
    private Visuel visuel;
    private List<Visuel> visuels;

    /**
     * Constructeur
     */
    private VisuelManager() { dao = DAO_Factory.getDAO_Visuel(); }

    /**
     * Singleton
     * @return
     */
    public static VisuelManager getInstance(){
        if (instance == null){
            instance = new VisuelManager();
        }

        return instance;
    }
    @Override
    public void insert(Visuel pObj) throws BLLException {

    }

    @Override
    public void update(Visuel pObj) throws BLLException {

    }

    @Override
    public void delete(Visuel pObj) throws BLLException {

    }

    @Override
    public List<Visuel> SelectAll() throws BLLException {
        return null;
    }

    @Override
    public Visuel SelectById(int pId) throws BLLException {
        return null;
    }
}
