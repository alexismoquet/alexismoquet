package com.dsi.model.dal;

import com.dsi.model.beans.UtilisateurBo;

/**
 * Interface DAO_UtilisateurBo
 *
 * @author Christophe Michard
 * @since Créé le 05/02/2020
 */
public interface DAO_UtilisateurBo extends DAO<UtilisateurBo> {
    UtilisateurBo selectByLogin(String pLogin) throws DALException;
}
