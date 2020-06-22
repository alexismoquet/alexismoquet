package com.dsi.controller;

import com.dsi.model.beans.Commentaire;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.CommentaireManager;

import java.util.List;

public class Commentaires {
    public static List<Commentaire> remplirJTableWithCommentaires() throws BLLException {
        CommentaireManager cm = CommentaireManager.getInstance();
        return cm.SelectAll();
    }

    public static List<Commentaire> remplirJTableWithCommentairesIdAnnonce(int idAnnonce) throws BLLException {
        CommentaireManager cm = CommentaireManager.getInstance();
        return cm.SelectByIdAnnonce(idAnnonce);
    }

    public static List<Commentaire> remplirJTableWithCommentairesIdUtilisateur(int idUtilisateur) throws BLLException {
        CommentaireManager cm = CommentaireManager.getInstance();
        return cm.SelectByIdUtilisateur(idUtilisateur);
    }
}
