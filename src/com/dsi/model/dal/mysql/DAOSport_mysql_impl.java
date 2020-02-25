package com.dsi.model.dal.mysql;

import com.dsi.model.beans.Sport;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Sport;

import java.util.List;

/**
 * Classe DAOSport_mysql_impl
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
public class DAOSport_mysql_impl implements DAO_Sport {
    @Override
    public void insert(Sport pObj) throws DALException {

    }

    @Override
    public void update(Sport pObj) throws DALException {

    }

    @Override
    public void delete(Sport pObj) throws DALException {

    }

    @Override
    public List<Sport> selectAll() throws DALException {
        return null;
    }

    @Override
    public Sport selectById(int pId) throws DALException {
        return null;
    }
}
