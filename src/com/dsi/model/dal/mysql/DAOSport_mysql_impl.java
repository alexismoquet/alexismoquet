package com.dsi.model.dal.mysql;

import com.dsi.model.beans.Sport;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Sport;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAOSport_mysql_impl
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
public class DAOSport_mysql_impl implements DAO_Sport {
    private String SQL_SelectAll    = "SELECT * FROM sports;";
    private String SQL_SelectById   = "SELECT * FROM sports WHERE sport_id=?;";
    private String SQL_Insert       = "INSERT INTO sports(sport_libelle, sport_id) VALUES(?, ?);";
    private String SQL_Update       = "UPDATE sports SET sport_libelle=?  WHERE sport_id=?;";
    private String SQL_Delete       = "DELETE FROM sports WHERE sport_id=?;";

    private Sport sport;
    private List <Sport> sports;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;


    @Override
    public void insert(Sport pObj) throws DALException {
        pstmt = null;
        rs = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_Insert);
            pstmt.setString(1, pObj.getSport_libelle());
            pstmt.setInt(2, pObj.getSport_id());

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
    public void update(Sport pObj) throws DALException {
        pstmt = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_Update);
            pstmt.setString(1, pObj.getSport_libelle());
            pstmt.setInt(2, pObj.getSport_id());

            pstmt.executeUpdate();
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
                cnx.close();
            } catch (SQLException e) {
                throw new DALException("Problème lors de la fermeture de la connexion à la base de données !", e);
            }
        }
    }

    @Override
    public void delete(Sport pObj) throws DALException {
        pstmt = null;
        boolean res = false;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_Delete);
            pstmt.setInt(1, pObj.getSport_id());

            //Suppression des annonces
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
                cnx.close();
            } catch (SQLException e) {
                throw new DALException("Problème lors de la fermeture de la connexion à la base de données !", e);
            }
        }
    }

    @Override
    public List<Sport> selectAll() throws DALException {
        stmt = null;
        rs = null;
        sport = null;
        sports = new ArrayList<>();
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            stmt =cnx.createStatement();
            rs = stmt.executeQuery(SQL_SelectAll);

            //Récupération des enregistrements
            while (rs.next()) {
                sport = new Sport(
                        rs.getInt("sport_id"),
                        rs.getString("sport_libelle")
                );

                sports.add(sport);
            }

            if (sports.size() == 0) {
                throw new DALException("Aucun utilisateur trouvé");
            }
        } catch (SQLException e) {
            throw new DALException("Problème lors de la connexion à la base de données !", e);
        } finally {
            //Fermeture du statement
            if (stmt != null) {
                try {
                    stmt.close();
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

        return sports;
    }

    @Override
    public Sport selectById(int pId) throws DALException {
        pstmt = null;
        rs = null;
        sport = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_SelectById);
            pstmt.setInt(1, pId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                //Récupération des enregistrements

                sport = new Sport(
                        rs.getInt("sport_id"),
                        rs.getString("sport_libelle")
                );
            } else {
                throw new DALException("Aucune sport trouvé avec l'identifiant : " + pId);
            }
        } catch (SQLException e) {
            throw new DALException("Problème lors de la connexion à la base de données !", e);
        } finally {
            //Fermeture du statement
            if (stmt != null) {
                try {
                    stmt.close();
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
        return sport;
    }
}
