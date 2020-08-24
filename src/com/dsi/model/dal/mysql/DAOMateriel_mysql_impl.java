package com.dsi.model.dal.mysql;

import com.dsi.model.beans.Annonce;
import com.dsi.model.beans.Materiel;
import com.dsi.model.dal.DALException;
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
    private String SQL_SelectByIdAdresse        = "SELECT * FROM materiels WHERE materiel_adresse_id = ?;";
    private String SQL_SelectByIdCategorie      = "SELECT * FROM materiels WHERE materiel_categorie_id = ?;";
    private String SQL_SelectByIdSport          = "SELECT * FROM materiels WHERE materiel_sport_id = ?;";
    private String SQL_Insert                   = "INSERT INTO materiels (materiel_categorie_id, materiel_sport_id, materiel_adresse_id, materiel_nom, materiel_description, materiel_prix, materiel_caution_prix) VALUES (?,?,?,?,?,?,?);";
    private String SQL_Update                   = "UPDATE materiels SET materiel_categorie_id=?, materiel_sport_id=?,materiel_adresse_id=?, materiel_nom=?, materiel_description=?, materiel_prix=?, materiel_caution_prix=? WHERE materiel_id=?;";
    private String SQL_Delete                   = "DELETE FROM materiels WHERE materiel_id=?;";
    private Materiel materiel;
    private List<Materiel> materiels;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;

    public DAOMateriel_mysql_impl() {}
    
    @Override
    public void insert(Materiel pObj) throws DALException {
        pstmt = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_Insert);
            pstmt.setInt(1, pObj.getMateriel_categorie_id());
            pstmt.setInt(2, pObj.getMateriel_sport_id());
            pstmt.setInt(3, pObj.getMateriel_adresse_id());
            pstmt.setString(4, pObj.getMateriel_nom());
            pstmt.setString(5, pObj.getMateriel_description());
            pstmt.setDouble(6, pObj.getMateriel_prix());
            pstmt.setDouble(7, pObj.getMateriel_caution_prix());

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
    public void update(Materiel pObj) throws DALException {
        pstmt = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_Update);
            pstmt.setInt(1, pObj.getMateriel_categorie_id());
            pstmt.setInt(2, pObj.getMateriel_sport_id());
            pstmt.setInt(3, pObj.getMateriel_adresse_id());
            pstmt.setString(4, pObj.getMateriel_nom());
            pstmt.setString(5, pObj.getMateriel_description());
            pstmt.setDouble(6, pObj.getMateriel_prix());
            pstmt.setDouble(7, pObj.getMateriel_caution_prix());
            pstmt.setInt(8, pObj.getMateriel_id());


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
    public Materiel selectById(int pId) throws DALException {
        rs = null;
        materiel = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_SelectById);
            pstmt.setInt(1, pId);
            rs = pstmt.executeQuery();

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

        return materiel;
    }

    @Override
    public List<Materiel> selectByIdCategorie(int pIdCategorie) throws DALException {
        rs = null;
        materiel = null;
        materiels = new ArrayList<>();
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_SelectByIdCategorie);
            pstmt.setInt(1, pIdCategorie);
            rs = pstmt.executeQuery();

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

        return materiels;
    }

    @Override
    public List<Materiel> selectByIdSport(int pIdSport) throws DALException {
        rs = null;
        materiel = null;
        materiels = new ArrayList<>();
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_SelectByIdSport);
            pstmt.setInt(1, pIdSport);
            rs = pstmt.executeQuery();

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

        return materiels;
    }

    @Override
    public List<Materiel> selectByIdAdresse(int pId) throws DALException { pstmt = null;
        rs = null;
        materiel = null;
        materiels = new ArrayList<>();
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_SelectByIdAdresse);
            pstmt.setInt(1, pId);
            rs = pstmt.executeQuery();

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

        return materiels;
    }
}
