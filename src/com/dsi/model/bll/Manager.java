package com.dsi.model.bll;

import java.util.List;

/**
 * Interface Manager
 * @param <T>: Beans à utiliser
 *
 * @author Christophe Michard
 * @since Créé le 05/02/2020
 */
public interface Manager<T> {

    void insert(T pObj) throws BLLException;
    void update(T pObj) throws BLLException;
    void delete(T pObj) throws BLLException;

    void delete(int pIdMateriel) throws BLLException;

    List<T> SelectAll() throws BLLException;
    T SelectById(int pId) throws BLLException;

}
