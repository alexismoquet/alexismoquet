package com.dsi.model.bll;

import com.dsi.model.beans.Materiel;
import com.dsi.model.beans.Visuel;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Factory;
import com.dsi.model.dal.DAO_Visuel;

import java.util.ArrayList;
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
        try {
            dao.insert(pObj);
        } catch (DALException e) {
            throw new BLLException("Un prblème est survenu lors de l'enregistrement de l'annonce", e);
        }
    }

    @Override
    public void update(Visuel pObj) throws BLLException {
        try {
            dao.update(pObj);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la modification de l'annonce", e);
        }
    }

    @Override
    public void delete(Visuel pObj) throws BLLException {
        try {
            dao.delete(pObj);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la modification de l'annonce", e);
        }
    }

    @Override
    public List<Visuel> SelectAll() throws BLLException {
        visuels = new ArrayList<>();
        try {
            visuels = dao.selectAll();
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération des utilisateurs", e);
        }

        return visuels;
    }

    @Override
    public Visuel SelectById(int pId) throws BLLException {
        visuel = null;

        try {
            visuel = dao.selectById(pId);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération de l'annonce n° : "+pId, e);
        }

        return visuel;
    }

    public List<Visuel> SelectByIdMateriel(int pId) throws BLLException {
        visuels = null;

        try {
            visuels = dao.selectByIdMateriel(pId);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération de l'annonce n° : "+pId, e);
        }

        return visuels;
    }

}//fin class
