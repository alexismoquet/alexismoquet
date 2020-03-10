package com.dsi.model.bll;

import com.dsi.model.beans.Adresse;
import com.dsi.model.beans.Annonce;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Adresse;
import com.dsi.model.dal.DAO_Annonce;
import com.dsi.model.dal.DAO_Factory;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe AnnonceManager
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
public class AnnonceManager implements Manager<Annonce> {

    private static AnnonceManager instance = null;

    private DAO_Annonce dao = null;
    private Annonce annonce;
    private List<Annonce> annonces;

    /**
     * Constructeur
     */
    public AnnonceManager() { dao = DAO_Factory.getDAO_Annonce(); }

    /**
     * Singleton
     * @return
     */
    public static AnnonceManager getInstance(){
        if (instance == null){
            instance = new AnnonceManager();
        }

        return instance;
    }
    @Override
    public void insert(Annonce pObj) throws BLLException {
        try {
            dao.insert(pObj);
        } catch (DALException e) {
            throw new BLLException("Un prblème est survenu lors de l'enregistrement de l'annonce", e);
        }
    }

    @Override
    public void update(Annonce pObj) throws BLLException {
        try {
            dao.update(pObj);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la modification de l'annonce", e);
        }
    }

    @Override
    public void delete(Annonce pObj) throws BLLException {
        try {
            dao.update(pObj);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la modification de l'annonce", e);
        }
    }

    @Override
    public void delete(int pIdUtilisateur) throws BLLException {
        try {
            dao.deleteByIdUtilisateur(pIdUtilisateur);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la modification de l'annonce", e);
        }
    }


    @Override
    public List<Annonce> SelectAll() throws BLLException {
        annonces = new ArrayList<>();

        try {
            annonces = dao.selectAll();
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération des annonces", e);
        }

        return annonces;
    }

    @Override
    public Annonce SelectById(int pId) throws BLLException {
        annonce = null;

        try {
            annonce = dao.selectById(pId);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération de l'annonce n° : "+pId, e);
        }

        return annonce;
    }

    public List<Annonce> SelectByIdUtilisateur(int pIdUtilisateur) throws BLLException {
        annonces = new ArrayList<>();

        try {
            annonces = dao.selectByIdUtilisateur(pIdUtilisateur);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération des annonces de l'utilisateur n° : "+pIdUtilisateur, e);
        }

        return annonces;
    }
}
