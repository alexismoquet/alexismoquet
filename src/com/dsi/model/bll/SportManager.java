package com.dsi.model.bll;

import com.dsi.model.beans.Sport;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Factory;
import com.dsi.model.dal.DAO_Sport;

import java.util.List;

public class SportManager implements Manager<Sport> {

    private static SportManager instance = null;
    private DAO_Sport dao = null;

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
        try {
            dao.insert(pObj);
        } catch (DALException e) {
            throw new BLLException("Un prblème est survenu lors de l'enregistrement du sport", e);
        }
    }

    @Override
    public void update(Sport pObj) throws BLLException {
        try {
            dao.update(pObj);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la modification du sport", e);
        }
    }

    @Override
    public void delete(Sport pObj) throws BLLException {
        try {
            dao.delete(pObj);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la modification du sport", e);
        }
    }

    @Override
    public List<Sport> SelectAll() throws BLLException {
        List <Sport> sports;
        try {
            sports = dao.selectAll();
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération des sports", e);
        }

        return sports;
    }

    @Override
    public Sport SelectById(int pId) throws BLLException {
        Sport sport = null;

        try {
            sport = dao.selectById(pId);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération du sport n° : "+pId, e);
        }

        return sport;
    }
}
