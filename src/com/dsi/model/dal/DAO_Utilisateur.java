package com.dsi.model.dal;

import com.dsi.model.beans.Utilisateur;

/**
 * Interface UtilisateurManager
 *
 * @author Christophe Michard
 * @since Créé le 05/02/2020
 */
public interface DAO_Utilisateur extends DAO<Utilisateur> {
    boolean deleteById(int pIdUtilisateur) throws DALException;
}
