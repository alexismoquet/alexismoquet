package com.dsi.model.dal;

import com.dsi.model.beans.Materiel;

import java.util.List;

/**
 * Interface DAO_Materiel
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
public interface DAO_Materiel extends DAO<Materiel> {

    List<Materiel> selectByIdAdresse(int pIdAdresse) throws DALException;

    Materiel selectById(int pId) throws DALException;

    List<Materiel> selectByIdCategorie(int pIdCategorie) throws DALException;


}
