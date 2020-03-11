package com.dsi.model.dal.mysql;

import com.dsi.model.beans.Materiel;
import com.dsi.model.beans.Visuel;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Factory;
import com.dsi.model.dal.DAO_Visuel;

import java.sql.*;
import java.util.List;

/**
 * Classe DAOVisuel_mysql_impl
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
public class DAOVisuel_mysql_impl implements DAO_Visuel {

    private String SQL_SelectAll                = "SELECT * FROM visuels;";
    private String SQL_SelectById               = "SELECT * FROM visuels WHERE visuel_id = ?;";
    private String SQL_Insert                   = "INSERT INTO visuels (visuel_materiel_id, visuel_nom_fichier) VALUES (?,?);";
    private String SQL_Update                   = "UPDATE visuels SET visuel_nom_fichier=?,  WHERE visuel_id=?;";
    private String SQL_Delete                   = "DELETE FROM visuels WHERE visuel_id=?;";

    private Visuel visuel;
    private List<Visuel> visuels;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;


    @Override
    public void insert(Visuel pObj) throws DALException {

    }

    @Override
    public void update(Visuel pObj) throws DALException {

    }

    @Override
    public void delete(Visuel pObj) throws DALException {
        pstmt = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt =cnx.prepareStatement(SQL_Delete);
            pstmt.setInt(1, pObj.getVisuel_id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DALException("Problème lors de la connexion à la base de données !", e);
        }finally {
            //Fermeture du statement
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    throw new DALException("Problème lors de la fermeture du statement !", e);
                }
            }

            //Fermeture de la connexion
            try {
               cnx.close();
            } catch (SQLException e) {
                throw new DALException("Problème lors de la fermeture de la connexion à la base de données !", e);
            }
        }
    }

    @Override
    public List<Visuel> selectAll() throws DALException {
        return null;
    }

    @Override
    public Visuel selectById(int pId) throws DALException {
        return null;
    }
}
