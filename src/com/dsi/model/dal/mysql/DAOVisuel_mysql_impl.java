package com.dsi.model.dal.mysql;

import com.dsi.model.beans.Annonce;
import com.dsi.model.beans.Materiel;
import com.dsi.model.beans.Sport;
import com.dsi.model.beans.Visuel;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Factory;
import com.dsi.model.dal.DAO_Visuel;

import java.sql.*;
import java.util.ArrayList;
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
    private String SQL_SelectByIdMateriel       = "SELECT * FROM visuels WHERE visuel_materiel_id = ?;";
    private String SQL_Insert                   = "INSERT INTO visuels (visuel_materiel_id, visuel_nom_fichier) VALUES (?,?);";
    private String SQL_Update                   = "UPDATE visuels SET visuel_nom_fichier=?, visuel_materiel_id=?  WHERE visuel_id=?;";
    private String SQL_Delete                   = "DELETE FROM visuels WHERE visuel_id=?;";

    private Visuel visuel;
    private List<Visuel> visuels;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;


    @Override
    public void insert(Visuel pObj) throws DALException {
        pstmt = null;
        rs = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt =cnx.prepareStatement(SQL_Insert);
            pstmt.setInt(1, pObj.getVisuel_materiel_id());
            pstmt.setString(2, pObj.getVisuel_nom_fichier());

            pstmt.executeUpdate();
//            rs = pstmt.getGeneratedKeys();
//
//            if (rs.next()) {
//                pObj.setVisuel_id(rs.getInt(1));
//            }
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
    public void update(Visuel pObj) throws DALException {
        pstmt = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_Update);
            pstmt.setString(1, pObj.getVisuel_nom_fichier());
            pstmt.setInt(2, pObj.getVisuel_materiel_id());
            pstmt.setInt(3, pObj.getVisuel_id());

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

        stmt = null;
        rs = null;
        visuel = null;
        visuels = new ArrayList<>();
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            stmt =cnx.createStatement();
            rs = stmt.executeQuery(SQL_SelectAll);

            //Récupération des enregistrements
            while (rs.next()) {
                visuel = new Visuel (
                        rs.getInt("visuel_id"),
                        rs.getInt("visuel_materiel_id"),
                        rs.getString("visuel_nom_fichier")
                );

                visuels.add(visuel);
            }

            if (visuels.size() == 0) {
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

        return visuels;
    }

    @Override
    public Visuel selectById(int pId) throws DALException {
        pstmt = null;
        rs = null;
        visuel = null;
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

                visuel = new Visuel (
                        rs.getInt("visuel_id"),
                        rs.getInt("visuel_materiel_id"),
                        rs.getString("visuel_nom_fichier")
                );

            } else {
                throw new DALException("Aucune annonce trouvée avec l'identifiant : " + pId);
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
        return visuel;
    }

    @Override
    public List<Visuel> selectByIdMateriel( int pId) throws DALException {
        pstmt = null;
        rs = null;
        visuel = null;
        visuels = new ArrayList<>();
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_SelectByIdMateriel);
            pstmt.setInt(1, pId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                visuel = new Visuel(
                        rs.getInt("visuel_id"),
                        rs.getInt("visuel_materiel_id"),
                        rs.getString("visuel_nom_fichier")

                );

                visuels.add(visuel);
            }

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
                cnx.setAutoCommit(true);
                cnx.close();
            } catch (SQLException e) {
                throw new DALException("Problème lors de la fermeture de la connexion à la base de données !", e);
            }
        }

        return visuels;
    }
}
