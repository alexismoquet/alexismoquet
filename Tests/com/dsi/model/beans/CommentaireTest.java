package com.dsi.model.beans;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Classe CommentaireTest
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
class CommentaireTest {

    Commentaire commentaire = new Commentaire();

    @Test
    void getCommentaire_id() {
        commentaire.setCommentaire_id(1);
        assertEquals(1, commentaire.getCommentaire_id());
    }

    @Test
    void getCommentaire_annonce_id() {
        commentaire.setCommentaire_annonce_id(1);
        assertEquals(1, commentaire.getCommentaire_annonce_id());
    }

    @Test
    void getCommentaire_note() {
        commentaire.setCommentaire_note(10);
        assertEquals(10, commentaire.getCommentaire_note());
    }

    @Test
    void getCommentaire_message() {
        commentaire.setCommentaire_message("message");
        assertEquals("message", commentaire.getCommentaire_message());
    }

    @Test
    void getCommentaire_date_parution() {
        commentaire.setCommentaire_date_parution(new Date());
        assertEquals(new Date(),  commentaire.getCommentaire_date_parution());
    }

}//fin class