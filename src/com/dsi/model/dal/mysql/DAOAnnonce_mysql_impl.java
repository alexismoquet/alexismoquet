package com.dsi.model.dal.mysql;

import com.dsi.librairies.FonctionsDate;
import com.dsi.model.beans.Annonce;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Annonce;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAOAnnonce_mysql_impl
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
public class DAOAnnonce_mysql_impl implements DAO_Annonce {

    private String SQL_SelectAll = "SELECT * FROM annonces;";
    private String SQL_SelectById = "SELECT * FROM annonces WHERE annonce_id = ?;";
    private String SQL_SelectByIdUtilisateur = "SELECT * FROM annonces WHERE annonce_utilisateur_id = ?;";
    private String SQL_SelectByIdMateriel = "SELECT * FROM annonces WHERE annonce_materiel_id = ?;";
    private String SQL_Insert = "INSERT INTO annonces(annonce_utilisateur_id, annonce_materiel_id, annonce_titre, annonce_description, annonce_date_parution) VALUES(?,?,?,?,?);";
    private String SQL_Update = "UPDATE annonces SET annonce_titre=?, annonce_description=?, annonce_date_parution=?, annonce_utilisateur_id=? WHERE annonce_id=?;";
    private String SQL_Delete = "DELETE FROM annonces WHERE annonce_id=?;";


    private Annonce annonce;
    private List<Annonce> annonces;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;

    /**
     * Constructeur par defaut
     */
    public DAOAnnonce_mysql_impl() {
    }

    @Override
    public void insert(Annonce pObj) throws DALException {
        pstmt = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_Insert);
            pstmt.setInt(1, pObj.getAnnonce_utilisateur_id());
            pstmt.setInt(2, pObj.getAnnonce_materiel_id());
            pstmt.setString(3, pObj.getAnnonce_titre());
            pstmt.setString(4, pObj.getAnnonce_description());
            pstmt.setDate(5, FonctionsDate.utilDateVersSqlDate(pObj.getAnnonce_date_parution()));

            pstmt.executeUpdate();
//            rs = pstmt.getGeneratedKeys();
//            if (rs.next()) {
//                pObj.setAnnonce_id(rs.getInt(1));
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
    public void update(Annonce pObj) throws DALException {
        pstmt = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_Update);
            pstmt.setString(1, pObj.getAnnonce_titre());
            pstmt.setString(2, pObj.getAnnonce_description());
            pstmt.setDate(3, FonctionsDate.utilDateVersSqlDate(pObj.getAnnonce_date_parution()));
            pstmt.setString(4, String.valueOf(pObj.getAnnonce_utilisateur_id()));
            pstmt.setInt(5, pObj.getAnnonce_id());

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
    public void delete(Annonce pObj) throws DALException {
        pstmt = null;
        boolean res = false;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_Delete);
            pstmt.setInt(1, pObj.getAnnonce_id());

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
    public List<Annonce> selectAll() throws DALException {
        stmt = null;
        rs = null;
        annonce = null;
        annonces = new ArrayList<>();
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            stmt = cnx.createStatement();
            rs = stmt.executeQuery(SQL_SelectAll);

            //Récupération des enregistrements
            while (rs.next()) {
                annonce = new Annonce(
                        rs.getInt("annonce_id"),
                        rs.getInt("annonce_utilisateur_id"),
                        rs.getInt("annonce_materiel_id"),
                        rs.getString("annonce_titre"),
                        rs.getString("annonce_description"),
                        rs.getDate("annonce_date_parution"),
                        rs.getBoolean("annonce_valider")
                );

                annonces.add(annonce);
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
        return annonces;
    }

    @Override
    public Annonce selectById(int pId) throws DALException {
        pstmt = null;
        rs = null;
        annonce = null;
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

                annonce = new Annonce(
                        rs.getInt("annonce_id"),
                        rs.getInt("annonce_utilisateur_id"),
                        rs.getInt("annonce_materiel_id"),
                        rs.getString("annonce_titre"),
                        rs.getString("annonce_description"),
                        rs.getDate("annonce_date_parution"),
                        rs.getBoolean("annonce_valider")
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
        return annonce;
    }

    @Override
    public List<Annonce> selectByIdUtilisateur(int pAnnonce_utilisateur_id) throws DALException {
        pstmt = null;
        rs = null;
        annonce = null;
        annonces = new ArrayList<>();
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_SelectByIdUtilisateur);
            pstmt.setInt(1, pAnnonce_utilisateur_id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                annonce = new Annonce(
                        rs.getInt("annonce_id"),
                        rs.getInt("annonce_utilisateur_id"),
                        rs.getInt("annonce_materiel_id"),
                        rs.getString("annonce_titre"),
                        rs.getString("annonce_description"),
                        rs.getDate("annonce_date_parution"),
                        rs.getBoolean("annonce_valider")
                );

                annonces.add(annonce);
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

        return annonces;
    }



    @Override
    public List<Annonce> selectByIdMateriel(int pIdMateriel) throws DALException {
        pstmt = null;
        rs = null;
        annonce = null;
        annonces = new ArrayList<>();
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_SelectByIdMateriel);
            pstmt.setInt(1, pIdMateriel);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                annonce = new Annonce(
                        rs.getInt("annonce_id"),
                        rs.getInt("annonce_utilisateur_id"),
                        rs.getInt("annonce_materiel_id"),
                        rs.getString("annonce_titre"),
                        rs.getString("annonce_description"),
                        rs.getDate("annonce_date_parution"),
                        rs.getBoolean("annonce_valider")
                );
                annonces.add(annonce);
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

        return annonces;
    }


}//fin class

