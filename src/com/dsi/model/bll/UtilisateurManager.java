package com.dsi.model.bll;

import com.dsi.model.beans.Utilisateur;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Factory;
import com.dsi.model.dal.DAO_Utilisateur;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe UtilisateurManager
 *
 * @author Christophe Michard
 * @since Créé le 05/02/2020
 */
public class UtilisateurManager implements Manager<Utilisateur> {

    private static UtilisateurManager instance = null;
    private DAO_Utilisateur dao;
    private Utilisateur utilisateur;
    private List<Utilisateur> utilisateurs;

    /**
     * Constructeur
     */
    public UtilisateurManager() {
        dao = DAO_Factory.getDAO_Utilisateur();
    }

    /**
     * Singleton
     * @return
     */
    public static UtilisateurManager getInstance(){
        if (instance == null){
            instance = new UtilisateurManager();
        }

        return instance;
    }

    @Override
    public void insert(Utilisateur pObj) throws BLLException {
        try {
            dao.insert(pObj);
        } catch (DALException e) {
            throw new BLLException("Un prblème est survenu lors de l'enregistrement de l'utilisateur", e);
        }
    }

    @Override
    public void update(Utilisateur pObj) throws BLLException {
        try {
            dao.update(pObj);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la modification de l'utilisateur", e);
        }
    }

    @Override
    public void delete(Utilisateur pObj) throws BLLException {
        try {
            dao.delete(pObj);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la suppression de l'utilisateur", e);
        }
    }

    public void delete(int pIdUtilisateur) throws BLLException {
        try {
            dao.deleteById(pIdUtilisateur);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la suppression de l'utilisateur", e);
        }
    }

    @Override
    public List <Utilisateur> SelectAll() throws BLLException {
        utilisateurs = new ArrayList<>();
        try {
            utilisateurs = dao.selectAll();
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération des utilisateurs", e);
        }

        return utilisateurs;
    }

    @Override
    public Utilisateur SelectById(int pId) throws BLLException {
        utilisateur = null;

        try {
            utilisateur = dao.selectById(pId);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération de l'utilisateurs n° : "+pId, e);
        }

        return utilisateur;
    }
}
