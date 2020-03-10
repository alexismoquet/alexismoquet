package com.dsi.model.dal;

import com.dsi.model.beans.Annonce;
import com.dsi.model.beans.Visuel;

import java.util.List;

/**
 * Interface DAO_Visuel
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
public interface DAO_Visuel extends DAO<Visuel> {

    boolean deleteByIdMateriel(int materiel_id) throws DALException;
}
