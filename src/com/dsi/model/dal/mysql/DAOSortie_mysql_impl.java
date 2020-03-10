package com.dsi.model.dal.mysql;

import com.dsi.librairies.Roles;
import com.dsi.model.beans.Annonce;
import com.dsi.model.beans.Materiel;
import com.dsi.model.beans.Sortie;
import com.dsi.model.beans.Utilisateur;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Factory;
import com.dsi.model.dal.DAO_Materiel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Classe DAOSortie_mysql_impl
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
public class DAOSortie_mysql_impl implements DAO_Materiel {

    private String SQL_SelectAll = "SELECT * FROM sorties;";
    private String SQL_SelectById = "SELECT * FROM sorties WHERE sortie_id=?;";
    private String SQL_Insert = "INSERT INTO sorties(sortie_materiel_id, sortie_etat, sortie_date_sortie, sortie_date_retour) VALUES(?,?,?,?);";
    private String SQL_Update = "UPDATE sorties SET sortie_materiel_id=?, sortie_etat=?, sortie_date_sortie=?, sortie_date_retour=? WHERE materiel_id=?;";
    private String SQL_Delete = "DELETE FROM sorties WHERE sortie_id=?;";
    private String SQL_DeleteByIdMateriel = "DELETE FROM sorties WHERE sortie_materiel_id=?;";

    private Sortie sortie;
    private List<Sortie> sorties;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;

    public DAOSortie_mysql_impl() {
    }

    @Override
    public void insert(Materiel pObj) throws DALException {

    }

    @Override
    public void update(Materiel pObj) throws DALException {

    }

    @Override
    public void delete(Materiel pObj) throws DALException {

    }

    @Override
    public List<Materiel> selectAll() throws DALException {
        return null;
    }

    @Override
    public Materiel selectById(int pId) throws DALException {
        return null;
    }


    @Override
    public boolean deleteById(int pIdMateriel) throws DALException {
        return false;
    }

    public boolean deleteByIdMateriel(int pIdMateriel) throws DALException {
        pstmt = null;
        boolean res = false;

        try {
            //Execution de la requête
            pstmt = MysqlConnecteur.getConnection().prepareStatement(SQL_DeleteByIdMateriel);
            pstmt.setInt(1, pIdMateriel);
            pstmt.executeUpdate();

            res = true;
        } catch (SQLException e) {

            throw new DALException("Problème lors de la connexion à la base de données !", e);
        } finally {
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
                MysqlConnecteur.closeConnexion();
            } catch (SQLException e) {
                throw new DALException("Problème lors de la fermeture de la connexion à la base de données !", e);
            }


            return res;
        }

    }
}


