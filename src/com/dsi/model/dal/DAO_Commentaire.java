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

    List<Commentaire> selectByIdAnnonce(int pIdAnnonce) throws DALException;
    List<Commentaire> selectByIdUtilisateur(int pIdUtilisateur) throws DALException;

}
