package com.dsi.model.dal.mysql;

import com.dsi.model.beans.Adresse;
import com.dsi.model.beans.Annonce;
import com.dsi.model.beans.Materiel;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Factory;
import com.dsi.model.dal.DAO_Materiel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Classe DAOMateriel_mysql_impl
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
public class DAOMateriel_mysql_impl implements DAO_Materiel {

    private String SQL_SelectAll                = "SELECT * FROM materiels;";
    private String SQL_SelectById               = "SELECT * FROM materiels WHERE materiel_id = ?;";
    private String SQL_Insert                   = "INSERT INTO materiels (materiel_categorie_id, materiel_sport_id, materiel_materiel_id, materiel_nom, materiel_description, materiel_prix, materiel_caution, materiel_caution_prix) VALUES (?,?,?,?,?,?,?,?,?);";
    private String SQL_Update                   = "UPDATE materiels SET materiel_nom=?, materiel_description=?, materiel_prix=?, materiel_caution=?, materiel_caution_prix=? WHERE materiel_id=?;";
    private String SQL_Delete                   = "DELETE FROM materiels WHERE materiel_id=?;";

    private Materiel materiel;
    private List<Materiel> materiels;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;

    public DAOMateriel_mysql_impl() {}
    
    @Override
    public void insert(Materiel pObj) throws DALException {

    }

    @Override
    public void update(Materiel pObj) throws DALException {

    }

    @Override
    public void delete(Materiel pObj) throws DALException {
        pstmt = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_Delete);
            pstmt.setInt(1, pObj.getMateriel_id());
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
    public List<Materiel> selectAll() throws DALException {
        stmt = null;
        rs = null;
        materiel = null;
        materiels = new ArrayList<>();
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            stmt = cnx.createStatement();
            rs = stmt.executeQuery(SQL_SelectAll);

            while (rs.next()) {
                materiel = new Materiel(
                        rs.getInt("materiel_id"),
                        rs.getInt("materiel_categorie_id"),
                        rs.getInt("materiel_sport_id"),
                        rs.getInt("materiel_adresse_id"),
                        rs.getString("materiel_nom"),
                        rs.getString("materiel_description"),
                        rs.getFloat("materiel_prix"),
                        rs.getBoolean("materiel_caution"),
                        rs.getFloat("materiel_caution_prix")
                );

                materiels.add(materiel);
            }

            if (materiels.size() == 0) {
                throw new DALException("Aucun materiel trouvé");
            }
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

        return materiels;
    }

    @Override
    public Materiel selectById(int pIdMateriel) throws DALException {
        return null;
    }

}