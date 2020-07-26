package com.dsi.model.dal.mysql;

import com.dsi.librairies.FonctionsDate;
import com.dsi.model.beans.*;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Sortie;

import java.sql.*;
import java.util.ArrayList;
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
    private String SQL_SelectByIdMateriel = "SELECT * FROM sorties WHERE sortie_materiel_id=?;";
    private String SQL_Insert = "INSERT INTO sorties (sortie_materiel_id, sortie_etat, sortie_date_sortie, sortie_date_retour) VALUES(?,?,?,?);";
    private String SQL_Update = "UPDATE sorties SET sortie_materiel_id=?, sortie_etat=?, sortie_date_sortie=?, sortie_date_retour=? WHERE sortie_id=?;";
    private String SQL_Delete = "DELETE FROM sorties WHERE sortie_id=?;";

    private Sortie sortie;
    private List<Sortie> sorties;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;

    @Override
    public void insert(Sortie pObj) throws DALException {
        pstmt = null;
        rs = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_Insert);
            pstmt.setString(1, String.valueOf(pObj.getSortie_materiel_id()));
            pstmt.setString(2, (pObj.getSortie_etat()));
            pstmt.setDate(3, FonctionsDate.utilDateVersSqlDate(pObj.getSortie_date_sortie()));
            pstmt.setDate(4, FonctionsDate.utilDateVersSqlDate(pObj.getSortie_date_retour()));

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
    public void update(Sortie pObj) throws DALException {
        pstmt = null;
        rs = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_Update);
            pstmt.setInt(1,Integer.parseInt(String.valueOf(pObj.getSortie_materiel_id())));
            pstmt.setString(2, (pObj.getSortie_etat()));
            pstmt.setDate(3, FonctionsDate.utilDateVersSqlDate(pObj.getSortie_date_sortie()));
            pstmt.setDate(4, FonctionsDate.utilDateVersSqlDate(pObj.getSortie_date_retour()));
            pstmt.setInt(5, pObj.getSortie_id());

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
    public void delete(Sortie pObj) throws DALException {
        pstmt = null;
        rs = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_Delete);
            pstmt.setString(1, String.valueOf(pObj.getSortie_id()));


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
    public List<Sortie> selectAll() throws DALException {
        stmt = null;
        rs = null;
        sortie = null;
        sorties = new ArrayList<>();
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            stmt =cnx.createStatement();
            rs = stmt.executeQuery(SQL_SelectAll);

            //Récupération des enregistrements
            while (rs.next()) {
                sortie = new Sortie (
                        rs.getInt("sortie_id"),
                        rs.getInt("sortie_materiel_id"),
                        rs.getString("sortie_etat"),
                        rs.getDate("sortie_date_sortie"),
                        rs.getDate("sortie_date_retour")
                );
                sorties.add(sortie);
            }

            if (sorties.size() == 0) {
                throw new DALException("Aucune sortie trouvée");
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

        return sorties;
    }

    @Override
    public Sortie selectById(int pId) throws DALException {
        pstmt = null;
        rs = null;
        sortie = null;
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

                sortie = new Sortie(
                        rs.getInt("sortie_id"),
                        rs.getInt("sortie_materiel_id"),
                        rs.getString("sortie_etat"),
                        rs.getDate("sortie_date_sortie"),
                        rs.getDate("sortie_date_retour")
                );

            } else {
                throw new DALException("Aucune sortie trouvée avec l'identifiant : " + pId);
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
        return sortie;
    }

    @Override
    public List<Sortie> selectByIdMateriel(int pId) throws DALException {

        pstmt = null;
        rs = null;
        sortie = null;
        sorties = new ArrayList<>();
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_SelectByIdMateriel);
            pstmt.setInt(1, pId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                //Récupération des enregistrements

                sortie = new Sortie(
                        rs.getInt("sortie_id"),
                        rs.getInt("sortie_materiel_id"),
                        rs.getString("sortie_etat"),
                        rs.getDate("sortie_date_sortie"),
                        rs.getDate("sortie_date_retour")
                );
                sorties.add(sortie);

            } else {
                throw new DALException("Aucune sortie trouvée avec l'identifiant : " + pId);
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
        return sorties;
    }


}//fin class



