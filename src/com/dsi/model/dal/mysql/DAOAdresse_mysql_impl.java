package com.dsi.model.dal.mysql;

import com.dsi.model.beans.Adresse;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Adresse;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAOAdresse_mysql_impl
 *
 * @author Christophe Michard
 * @since Créé le 06/02/2020
 */
public class DAOAdresse_mysql_impl implements DAO_Adresse {

    private String SQL_SelectAll                = "SELECT * FROM adresses;";
    private String SQL_SelectById               = "SELECT * FROM adresses WHERE adresse_id = ?;";
    private String SQL_SelectByIdUtilisateur    = "SELECT * FROM adresses WHERE adresse_utilisateur_id = ?;";
    private String SQL_Insert                   = "INSERT INTO adresses(adresse_utilisateur_id, adresse_adresse, adresse_complement, adresse_code_postal, adresse_ville, adresse_departement, adresse_pays, adresse_longitude, adresse_latitude) VALUES(?,?,?,?,?,?,?,?,?);";
    private String SQL_Update                   = "UPDATE adresses SET adresse_utilisateur_id=?, adresse_adresse=?, adresse_complement=?, adresse_code_postal=?, adresse_ville=?, adresse_departement=?, adresse_pays=?, adresse_longitude=?, adresse_latitude=? WHERE adresse_id=?;";
    private String SQL_Delete                   = "DELETE FROM adresses WHERE adresse_id=?;";

    private Adresse adresse;
    private List<Adresse> adresses;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;

    public DAOAdresse_mysql_impl() {}

    @Override
    public void insert(Adresse pObj) throws DALException {
        pstmt = null;
        rs = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_Insert);
            pstmt.setInt(1, pObj.getIdUtilisateur());
            pstmt.setString(2, pObj.getAdresse());
            pstmt.setString(3, pObj.getComplement());
            pstmt.setString(4, pObj.getCodePostal());
            pstmt.setString(5, pObj.getVille());
            pstmt.setString(6, pObj.getDepartement());
            pstmt.setString(7, pObj.getPays());
            pstmt.setFloat(8, pObj.getLongitude());
            pstmt.setFloat(9, pObj.getLatitude());

            pstmt.executeUpdate();
//            rs = pstmt.getGeneratedKeys();
//            if (rs.next()){
//                pObj.setIdAdresse(rs.getInt(1));
//            }
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
    public void update(Adresse pObj) throws DALException {
        pstmt = null;
        rs = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_Update);
            pstmt.setInt(1, pObj.getIdUtilisateur());
            pstmt.setString(2, pObj.getAdresse());
            pstmt.setString(3, pObj.getComplement());
            pstmt.setString(4, pObj.getCodePostal());
            pstmt.setString(5, pObj.getVille());
            pstmt.setString(6, pObj.getDepartement());
            pstmt.setString(7, pObj.getPays());
            pstmt.setFloat(8, pObj.getLongitude());
            pstmt.setFloat(9, pObj.getLatitude());
            pstmt.setInt(10, pObj.getIdAdresse());

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
    public void delete(Adresse pObj) throws DALException {
        pstmt = null;
        rs = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_Delete);
            pstmt.setInt(1, pObj.getIdAdresse());
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
    public List<Adresse> selectAll() throws DALException {
        stmt = null;
        rs = null;
        adresse = null;
        adresses = new ArrayList<>();
        pstmt = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            stmt = cnx.createStatement();
            rs = stmt.executeQuery(SQL_SelectAll);

            while (rs.next()) {
                adresse = new Adresse(
                        rs.getInt("adresse_id"),
                        rs.getInt("adresse_utilisateur_id"),
                        rs.getString("adresse_adresse"),
                        rs.getString("adresse_complement"),
                        rs.getString("adresse_code_postal"),
                        rs.getString("adresse_ville"),
                        rs.getString("adresse_departement"),
                        rs.getString("adresse_pays"),
                        rs.getFloat("adresse_longitude"),
                        rs.getFloat("adresse_latitude")
                );
                adresses.add(adresse);
            }

            if (adresses.size() == 0) {
                throw new DALException("Aucune adresse trouvée");
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

        return adresses;
    }

    @Override
    public Adresse selectById(int pId) throws DALException {
        pstmt = null;
        rs = null;
        adresse = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_SelectById);
            pstmt.setInt(1, pId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                adresse = new Adresse(
                        rs.getInt("adresse_id"),
                        rs.getInt("adresse_utilisateur_id"),
                        rs.getString("adresse_adresse"),
                        rs.getString("adresse_complement"),
                        rs.getString("adresse_code_postal"),
                        rs.getString("adresse_ville"),
                        rs.getString("adresse_departement"),
                        rs.getString("adresse_pays"),
                        rs.getFloat("adresse_longitude"),
                        rs.getFloat("adresse_latitude")
                );
            }else {
                throw new DALException("Aucune adresse trouvée avec l'identifiant : "+pId);
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

        return adresse;
    }

    @Override
    public List<Adresse> selectByIdUtilisateur(int pIdUtilisateur) throws DALException {
        pstmt = null;
        rs = null;
        adresse = null;
        adresses = new ArrayList<>();
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_SelectByIdUtilisateur);
            pstmt.setInt(1, pIdUtilisateur);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                adresse = new Adresse(
                        rs.getInt("adresse_id"),
                        rs.getInt("adresse_utilisateur_id"),
                        rs.getString("adresse_adresse"),
                        rs.getString("adresse_complement"),
                        rs.getString("adresse_code_postal"),
                        rs.getString("adresse_ville"),
                        rs.getString("adresse_departement"),
                        rs.getString("adresse_pays"),
                        rs.getFloat("adresse_longitude"),
                        rs.getFloat("adresse_latitude")
                );
                adresses.add(adresse);
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

        return adresses;
    }
}
