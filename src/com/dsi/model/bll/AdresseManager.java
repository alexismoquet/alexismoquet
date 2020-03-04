package com.dsi.model.bll;

import com.dsi.model.beans.Adresse;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Adresse;
import com.dsi.model.dal.DAO_Factory;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe AdresseManager
 *
 * @author Christophe Michard
 * @since Créé le 06/02/2020
 */
public class AdresseManager implements Manager<Adresse> {

    private static AdresseManager instance = null;
    private DAO_Adresse dao = null;
    private Adresse adresse;
    private List<Adresse> adresses;

    /**
     * Constructeur
     */
    private AdresseManager() { dao = DAO_Factory.getDAO_Adresse(); }

    /**
     * Singleton
     * @return
     */
    public static AdresseManager getInstance(){
        if (instance == null){
            instance = new AdresseManager();
        }

        return instance;
    }

    @Override
    public void insert(Adresse pObj) throws BLLException {
        try {
            dao.insert(pObj);
        } catch (DALException e) {
            throw new BLLException("Un prblème est survenu lors de l'enregistrement de l'adresse", e);
        }
    }

    @Override
    public void update(Adresse pObj) throws BLLException {
        try {
            dao.update(pObj);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la modification de l'adresse", e);
        }
    }

    @Override
    public void delete(Adresse pObj) throws BLLException {
        try {
            dao.delete(pObj);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la suppression de l'adresse", e);
        }
    }

    @Override
    public List<Adresse> SelectAll() throws BLLException {
        adresses = new ArrayList<>();
        try {
            adresses = dao.selectAll();
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération des adresses", e);
        }

        return adresses;
    }

    @Override
    public Adresse SelectById(int pId) throws BLLException {
        adresse = null;

        try {
            adresse = dao.selectById(pId);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération de l'adresse n° : "+pId, e);
        }

        return adresse;
    }

    public List<Adresse> SelectByIdUtilisateur(int pIdUtilisateur) throws BLLException {
        adresses = new ArrayList<>();

        try {
            adresses = dao.selectByIdUtilisateur(pIdUtilisateur);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération des adresses de l'utilisateur n° : "+pIdUtilisateur, e);
        }

        return adresses;
    }
}
