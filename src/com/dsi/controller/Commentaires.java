package com.dsi.controller;

import com.dsi.model.beans.Commentaire;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.CommentaireManager;

import java.util.List;

public class Commentaires {
    public static List<Commentaire> remplirJTableWithCommentaires() throws BLLException {
        return CommentaireManager.getInstance().SelectAll();
    }

    public static List<Commentaire> remplirJTableWithCommentairesIdAnnonce(int idAnnonce) throws BLLException {
        return CommentaireManager.getInstance().SelectByIdAnnonce(idAnnonce);
    }

    public static List<Commentaire> remplirJTableWithCommentairesIdUtilisateur(int idUtilisateur) throws BLLException {
        return CommentaireManager.getInstance().SelectByIdUtilisateur(idUtilisateur);
    }
}
