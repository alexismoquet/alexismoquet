package com.dsi.model.bll;

import com.dsi.model.beans.Commentaire;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Commentaire;
import com.dsi.model.dal.DAO_Factory;
import java.util.List;

/**
 * Classe CommentaireManager
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
public class CommentaireManager implements Manager<Commentaire> {

    private static CommentaireManager instance = null;
    private DAO_Commentaire dao = null;

    /**
     * Constructeur
     */
    public CommentaireManager() { dao = DAO_Factory.getDAO_Commentaire(); }

    /**
     * Singleton
     * @return
     */
    public static CommentaireManager getInstance(){
        if (instance == null){
            instance = new CommentaireManager();
        }

        return instance;
    }

    @Override
    public void insert(Commentaire pObj) throws BLLException {
        try {
            dao.insert(pObj);
        } catch (DALException e) {
            throw new BLLException("Un prblème est survenu lors de l'enregistrement du commentaire", e);
        }
    }

    @Override
    public void update(Commentaire pObj) throws BLLException {
        try {
            dao.update(pObj);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la modification du commentaire", e);
        }
    }

    @Override
    public void delete(Commentaire pObj) throws BLLException {
        try {
            dao.delete(pObj);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la modification du commentaire", e);
        }
    }

    @Override
    public List<Commentaire> SelectAll() throws BLLException {
        List <Commentaire> commentaires;

        try {
            commentaires = dao.selectAll();
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération des commentaires", e);
        }

        return commentaires;
    }

    @Override
    public Commentaire SelectById(int pId) throws BLLException {
        Commentaire commentaire;

        try {
            commentaire = dao.selectById(pId);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération du commentaire n° : "+pId, e);
        }

        return commentaire;
    }


    public List <Commentaire> SelectByIdAnnonce(int pId) throws BLLException {
        List <Commentaire> commentaires;

        try {
            commentaires = dao.selectByIdAnnonce(pId);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération du commentaire n° : "+pId, e);
        }

        return commentaires;
    }

    public List <Commentaire> SelectByIdUtilisateur(int pId) throws BLLException {
        List <Commentaire> commentaires;

        try {
            commentaires = dao.selectByIdUtilisateur(pId);
        } catch (DALException e) {
            throw new BLLException("Un problème est survenu lors de la récupération du commentaire n° : "+pId, e);
        }

        return commentaires;
    }



}//fin class

