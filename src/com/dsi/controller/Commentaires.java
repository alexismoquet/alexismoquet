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
}
