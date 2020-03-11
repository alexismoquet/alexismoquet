package com.dsi.model.dal;

import com.dsi.model.beans.Adresse;
import com.dsi.model.beans.Utilisateur;

import java.util.List;

/**
 * Interface DAO_Adresse
 *
 * @author Christophe Michard
 * @since Créé le 06/02/2020
 */
public interface DAO_Adresse extends DAO<Adresse> {

    List<Adresse> selectByIdUtilisateur(int pIdUtilisateur) throws DALException;

}
