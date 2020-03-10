package com.dsi.model.dal;

import com.dsi.model.beans.Commentaire;
import java.util.List;

/**
 * Interface DAO_Commentaire
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
public interface DAO_Commentaire extends DAO<Commentaire> {
    List<Commentaire> selectByIdAnnonce(int commentaire_annonce_id) throws DALException;
    boolean deleteByIdAnnonce(int commentaire_annonce_id) throws DALException;

}
