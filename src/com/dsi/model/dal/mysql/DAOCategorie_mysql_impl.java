package com.dsi.model.dal.mysql;

import com.dsi.model.beans.Categorie;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Categorie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAOCategorie_mysql_impl
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
public class DAOCategorie_mysql_impl implements DAO_Categorie {

    private String SQL_SelectAll = "SELECT * FROM categories;";
    private String SQL_SelectById = "SELECT * FROM categories WHERE categorie_id = ?;";
    private String SQL_Insert = "INSERT INTO categories(categorie_libelle) VALUES(?);";
    private String SQL_Update = "UPDATE categories SET categorie_libelle=? WHERE categorie_id=?;";
    private String SQL_Delete = "DELETE FROM categories WHERE categorie_id=?;";

    private Categorie categorie;
    private List <Categorie> categories;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;


    /**
     * Constructeur par defaut
     */
    public DAOCategorie_mysql_impl() {
    }

    @Override
    public void insert(Categorie pObj) throws DALException {
        pstmt = null;
        rs = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt =cnx.prepareStatement(SQL_Insert);
            pstmt.setString(1, pObj.getCategorie_libelle());

            pstmt.executeUpdate();
           // rs = pstmt.getGeneratedKeys();>

//            if (rs.next()) {
//                pObj.setCategorie_id(rs.getInt(1));
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
    public void update(Categorie pObj) throws DALException {
        pstmt = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_Update);
            pstmt.setString(1, pObj.getCategorie_libelle());
            pstmt.setInt(2, pObj.getCategorie_id());

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
    public void delete(Categorie pObj) throws DALException {
        pstmt = null;
        boolean res = false;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_Delete);
            pstmt.setInt(1, pObj.getCategorie_id());

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
    public List<Categorie> selectAll() throws DALException {
        stmt = null;
        rs = null;
        categorie = null;
        categories = new ArrayList<>();
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            stmt = cnx.createStatement();
            rs = stmt.executeQuery(SQL_SelectAll);

            //Récupération des enregistrements
            while (rs.next()) {
                categorie = new Categorie(
                        rs.getInt("categorie_id"),
                        rs.getString("categorie_libelle")
                );

                categories.add(categorie);
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
        return categories;
    }

    @Override
    public Categorie selectById(int pId) throws DALException {
        pstmt = null;
        rs = null;
        categorie = null;
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

                    categorie = new Categorie(
                            rs.getInt("categorie_id"),
                            rs.getString("categorie_libelle")
                    );
                    categories.add(categorie);

            }else {
                throw new DALException("Aucune catégorie trouvée avec l'identifiant : " + pId);
            }
        } catch(SQLException e){
            throw new DALException("Problème lors de la connexion à la base de données !", e);
        } finally{
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

        return categorie;
    }
}
