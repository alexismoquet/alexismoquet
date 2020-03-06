package com.dsi.model.bll;

import com.dsi.model.beans.Sport;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Factory;
import com.dsi.model.dal.DAO_Sport;

import java.util.ArrayList;
import java.util.List;

public class SportManager implements Manager<Sport> {

    private static SportManager instance = null;
    private DAO_Sport dao = null;
    private Sport sport;
    private List<Sport> sports;

    /**
     * Constructeur
     */
    public SportManager() { dao = DAO_Factory.getDAO_Sport(); }

    /**
     * Singleton
     * @return
     */
    public static SportManager getInstance(){
        if (instance == null){
            instance = new SportManager();
        }

        return instance;
    }

    @Override
    public void insert(Sport pObj) throws BLLException {

    }

    @Override
    public void update(Sport pObj) throws BLLException {

    }

    @Override
    public void delete(Sport pObj) throws BLLException {

    }

    @Override
    public List<Sport> SelectAll() throws BLLException {
        sports = new ArrayList<>();
        try {
            sports = dao.selectAll();
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération des utilisateurs", e);
        }

        return sports;
    }

    @Override
    public Sport SelectById(int pId) throws BLLException {
        return null;
    }
}
