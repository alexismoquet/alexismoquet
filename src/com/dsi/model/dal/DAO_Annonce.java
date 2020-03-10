package com.dsi.model.dal;

import com.dsi.model.beans.Annonce;

import java.util.List;

/**
 * Interface DAO_Annonce
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
public interface DAO_Annonce extends DAO<Annonce> {

    List<Annonce> selectByIdUtilisateur(int pAnnonce_utilisateur_id) throws DALException;

    boolean deleteByIdUtilisateur(int pIdUtilisateur) throws DALException;

    boolean deleteByIdMateriel(int materiel_utilisateur_id) throws DALException;
}
