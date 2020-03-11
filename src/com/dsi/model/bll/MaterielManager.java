package com.dsi.model.bll;

import com.dsi.model.beans.Materiel;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Factory;
import com.dsi.model.dal.DAO_Materiel;

import java.util.List;

/**
 * Classe MaterielManager
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
public class MaterielManager implements  Manager<Materiel> {

    private static MaterielManager instance = null;

    private DAO_Materiel dao = null;
    private Materiel materiel;
    private List<Materiel> materiels;

    /**
     * Constructeur
     */
    private MaterielManager() { dao = DAO_Factory.getDAO_Materiel(); }

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

    }

    @Override
    public void delete(Materiel pObj) throws BLLException {
            try {
                dao.delete(pObj);
            } catch (DALException e) {
                throw new BLLException("Un problème est survenu lors de la suppression de l'utilisateur", e);
            }
        }

    @Override
    public List<Materiel> SelectAll() throws BLLException {
        return null;
    }

    @Override
    public Materiel SelectById(int pId) throws BLLException {
        return null;
    }

}//fin class
