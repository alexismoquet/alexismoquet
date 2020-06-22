package com.dsi.model.beans;

import java.util.Date;

/**
 * Classe Annonce
 *
 * @author Alexis Moquet
 * @since Créé le 24/02/2020
 */
public class Commentaire {

    //#################
    //### Attributs ###
    //#################

    private int commentaire_id;
    private int commentaire_annonce_id;
    private int commentaire_utilisateur_id;
    private int commentaire_note;
    private String commentaire_message;
    private Date commentaire_date_parution;

    //#################
    //### Constructeurs
    //#################

    /**
     * Constructeur par defaut
     * @param commentaire_id
     * @param commentaire_annonce_id
     * @param commentaire_utilisateur_id
     * @param commentaire_note
     * @param commentaire_message
     * @param commentaire_date_parution
     */
    public Commentaire(int commentaire_id, int commentaire_annonce_id, int commentaire_utilisateur_id, int commentaire_note, String commentaire_message, java.sql.Date commentaire_date_parution) {
        this.commentaire_id = commentaire_id;
        this.commentaire_annonce_id = commentaire_annonce_id;
        this.commentaire_utilisateur_id = commentaire_utilisateur_id;
        this.commentaire_note = commentaire_note;
        this.commentaire_message = commentaire_message;
        this.commentaire_date_parution = commentaire_date_parution;
    }

    /**
     * Constructeur
     *
     * @param commentaire_id
     * @param commentaire_annonce_id
     * @param commentaire_note
     * @param commentaire_message
     * @param commentaire_date_parution
     */
    public Commentaire(int commentaire_id, int commentaire_annonce_id, int commentaire_note, String commentaire_message, Date commentaire_date_parution) {
        this.commentaire_id = commentaire_id;
        this.commentaire_annonce_id = commentaire_annonce_id;
        this.commentaire_note = commentaire_note;
        this.commentaire_message = commentaire_message;
        this.commentaire_date_parution = commentaire_date_parution;
    }

    //#######################
    //### Getters and setters
    //#######################

    /**
     * Retourne l'identifiant du commentaire
     *
     * @return int: Identifiant commentaire
     */
    public int getCommentaire_id() {
        return commentaire_id;
    }

    /**
     * Défini l'identifiant du commentaire
     *
     * @param commentaire_id
     */
    public void setCommentaire_id(int commentaire_id) {
        this.commentaire_id = commentaire_id;
    }

    /**
     * Retourne l'identifiant de l'annonce du commentaire
     *
     * @return int: Identifiant annonce commentaire
     */
    public int getCommentaire_annonce_id() {
        return commentaire_annonce_id;
    }

    /**
     * Défini l'identifiant de l'annonce du commentaire
     *
     * @param commentaire_annonce_id
     */
    public void setCommentaire_annonce_id(int commentaire_annonce_id) {
        this.commentaire_annonce_id = commentaire_annonce_id;
    }

    /**
     * Retourne la note du commentaire
     *
     * @return int: note commentaire
     */
    public int getCommentaire_note() {
        return commentaire_note;
    }

    /**
     * Défini la note du commentaire
     *
     * @param commentaire_note
     */
    public void setCommentaire_note(int commentaire_note) {
        this.commentaire_note = commentaire_note;
    }

    /**
     * Retourne le message du commentaire
     *
     * @return string: message commentaire
     */
    public String getCommentaire_message() {
        return commentaire_message;
    }

    /**
     * Défini le message du commentaire
     *
     * @param commentaire_message
     */
    public void setCommentaire_message(String commentaire_message) {
        this.commentaire_message = commentaire_message;
    }

    /**
     * Retourne la date de parution du commentaire
     *
     * @return date: date parution commentaire
     */
    public Date getCommentaire_date_parution() {
        return commentaire_date_parution;
    }

    /**
     * Défini la date de parution du commentaire
     *
     * @param commentaire_date_parution
     */
    public void setCommentaire_date_parution(Date commentaire_date_parution) {
        this.commentaire_date_parution = commentaire_date_parution;
    }

    public int getCommentaire_utilisateur_id() {
        return commentaire_utilisateur_id;
    }

    public void setCommentaire_utilisateur_id(int commentaire_utilisateur_id) {
        this.commentaire_utilisateur_id = commentaire_utilisateur_id;
    }

}//fin class
