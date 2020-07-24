package com.dsi.model.bll;

import com.dsi.model.beans.Materiel;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Factory;
import com.dsi.model.dal.DAO_Materiel;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe MaterielManager
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
public class MaterielManager implements  Manager<Materiel> {

    private static MaterielManager instance = null;

    private DAO_Materiel dao;
    private List<Materiel> materiel;
    private List<Materiel> materiels;

    /**
     * Constructeur
     */
    public MaterielManager() { dao = DAO_Factory.getDAO_Materiel(); }

    /**
     * Singleton
     * @return
     */
    public static MaterielManager getInstance(){
        if (instance == null){
            instance = new MaterielManager();
        }

        return instance;
    }
    @Override
    public void insert(Materiel pObj) throws BLLException {

    }

    @Override
    public void update(Materiel pObj) throws BLLException {
        try {
            dao.update(pObj);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la mise à jour du materiel", e);
        }
    }

    @Override
    public void delete(Materiel pObj) throws BLLException {
            try {
                dao.delete(pObj);
            } catch (DALException e) {
                throw new BLLException("Un problème est survenu lors de la suppression du materiel", e);
            }
        }

    @Override
    public List<Materiel> SelectAll() throws BLLException {
        materiels = new ArrayList<>();
        try {
            materiels = dao.selectAll();
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération des materiels", e);
        }

        return materiels;
    }

    @Override
    public Materiel SelectById(int pId) throws BLLException {
        materiel = null;

        try {
            materiel = dao.selectById(pId);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération du materiel n° : "+pId, e);
        }
        return (Materiel) materiel;
    }

    public List<Materiel> SelectByIdSport(int pId) throws BLLException {
        materiel =  null;
        try {
            materiel = dao.selectByIdSport(pId);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération du materiel : "+pId, e);
        }

        return materiel;
    }

    public List<Materiel> SelectByIdAdresse(int pId) throws BLLException {
        materiel = null;

        try {
            materiel = dao.selectByIdAdresse(pId);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération du materiel : "+pId, e);
        }

        return materiel;
    }

    public List<Materiel> SelectByIdCategorie(int pId) throws BLLException {
        materiel = null;

        try {
            materiel = dao.selectByIdCategorie(pId);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération du materiel : "+pId, e);
        }

        return materiel;
    }
}//fin class
