package com.dsi.model.bll;

import com.dsi.model.beans.UtilisateurBo;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Factory;
import com.dsi.model.dal.DAO_UtilisateurBo;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe UtilisateurBoManager
 *
 * @author Christophe Michard
 * @since Créé le 05/02/2020
 */
public class UtilisateurBoManager implements Manager<UtilisateurBo> {

    private static UtilisateurBoManager instance = null;

    private DAO_UtilisateurBo dao;
    private UtilisateurBo utilisateur;
    private List<UtilisateurBo> utilisateurs;

    /**
     * Constructeur
     */
    private UtilisateurBoManager() { dao = DAO_Factory.getDAO_UtilisateurBo(); }

    /**
     * Singleton
     * @return
     */
    public static UtilisateurBoManager getInstance(){
        if (instance == null){
            instance = new UtilisateurBoManager();
        }

        return instance;
    }

    @Override
    public void insert(UtilisateurBo pObj) throws BLLException {
        try {
            dao.insert(pObj);
        } catch (DALException e) {
            throw new BLLException("Un prblème est survenue lors de l'enregistrement de l'utilisateur", e);
        }
    }

    @Override
    public void update(UtilisateurBo pObj) throws BLLException {
        try {
            dao.update(pObj);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenue lors de la modification de l'utilisateur", e);
        }
    }

    @Override
    public void delete(UtilisateurBo pObj) throws BLLException {
        try {
            dao.delete(pObj);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenue lors de la suppression de l'utilisateur", e);
        }
    }

    @Override
    public List<UtilisateurBo> SelectAll() throws BLLException {
        try {
            utilisateurs = dao.selectAll();
        } catch (DALException e) {
            throw new BLLException("Un problème est survenue lors de la récupération des utilisateurs", e);
        }

        return utilisateurs;
    }

    @Override
    public UtilisateurBo SelectById(int pId) throws BLLException {
        try {
            utilisateur = dao.selectById(pId);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenue lors de la récupération de l'utilisateurs n° : "+pId, e);
        }

        return utilisateur;
    }

    public UtilisateurBo selectByLogin(String pLogin) throws BLLException {
        try {
            utilisateur = dao.selectByLogin(pLogin);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenue lors de la récupération de l'utilisateurs : "+pLogin, e);
        }

        return utilisateur;
    }
}
