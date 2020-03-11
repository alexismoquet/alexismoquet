package com.dsi.model.dal.mysql;

import com.dsi.librairies.Roles;
import com.dsi.model.beans.Annonce;
import com.dsi.model.beans.Materiel;
import com.dsi.model.beans.Sortie;
import com.dsi.model.beans.Utilisateur;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Factory;
import com.dsi.model.dal.DAO_Materiel;
import com.dsi.model.dal.DAO_Sortie;

import java.sql.*;
import java.util.List;

/**
 * Classe DAOSortie_mysql_impl
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
public class DAOSortie_mysql_impl implements DAO_Sortie {

    private String SQL_SelectAll = "SELECT * FROM sorties;";
    private String SQL_SelectById = "SELECT * FROM sorties WHERE sortie_id=?;";
    private String SQL_Insert = "INSERT INTO sorties(sortie_materiel_id, sortie_etat, sortie_date_sortie, sortie_date_retour) VALUES(?,?,?,?);";
    private String SQL_Update = "UPDATE sorties SET sortie_materiel_id=?, sortie_etat=?, sortie_date_sortie=?, sortie_date_retour=? WHERE materiel_id=?;";
    private String SQL_Delete = "DELETE FROM sorties WHERE sortie_id=?;";

    private Sortie sortie;
    private List<Sortie> sorties;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;

    @Override
    public void insert(Sortie pObj) throws DALException {

    }

    @Override
    public void update(Sortie pObj) throws DALException {

    }

    @Override
    public void delete(Sortie pObj) throws DALException {

    }

    @Override
    public List<Sortie> selectAll() throws DALException {
        return null;
    }

    @Override
    public Sortie selectById(int pId) throws DALException {
        return null;
    }
}


