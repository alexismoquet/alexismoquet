package com.dsi.model.dal;

import com.dsi.model.beans.Annonce;

import java.util.List;

/**
 * Interface générique pour l'accès aux données
 * @param <T>: Beans à utiliser
 *
 * @author Christophe Michard
 * @since Créé le 05/02/2020
 */
public interface DAO<T> {

    void insert(T pObj) throws DALException;
    void update(T pObj) throws DALException;
    void delete(T pObj) throws DALException;
    List<T> selectAll() throws DALException;
    T selectById(int pId) throws DALException;

}
