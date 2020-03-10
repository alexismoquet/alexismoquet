package com.dsi.model.dal.mysql;

import com.dsi.model.beans.Adresse;
import com.dsi.model.beans.Categorie;
import com.dsi.model.beans.Commentaire;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Commentaire;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAOCommentaire_mysql_impl
 *
 * @author Alexis Moquet
 * @since Créé le 25/02/2020
 */
public class DAOCommentaire_mysql_impl implements DAO_Commentaire {

    private String SQL_SelectAll = "SELECT * FROM commentaires;";
    private String SQL_SelectById = "SELECT * FROM commentaires WHERE commentaire_id = ?;";
    private String SQL_Insert = "INSERT INTO commentaires(commentaire_annonce_id, commentaire_note, commentaire_message, commentaire_date_parution) VALUES(?,?,?,?);";
    private String SQL_Update = "UPDATE commentaires SET commentaire_annonce_id=?, commentaire_note=?, commentaire_message=?, commentaire_date_parution=?  WHERE commentaire_id=?;";
    private String SQL_Delete = "DELETE FROM commentaires WHERE commentaires_id=?;";
    private String SQL_DeleteByIdAnnonce = "DELETE FROM commentaires WHERE commentaire_annonce_id=?;";

    private Commentaire commentaire ;
    private List <Commentaire> commentaires;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;


    /**
     * Constructeur par defaut
     */
    public DAOCommentaire_mysql_impl() {
    }


    @Override
    public void insert(Commentaire pObj) throws DALException {
        pstmt = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt =cnx.prepareStatement(SQL_Insert);
            pstmt.setInt(1, pObj.getCommentaire_id());
            pstmt.setInt(2, pObj.getCommentaire_annonce_id());
            pstmt.setInt(3, pObj.getCommentaire_note());
            pstmt.setString(4, pObj.getCommentaire_message());
            pstmt.setDate(5, (Date) pObj.getCommentaire_date_parution());

            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()){
                pObj.setCommentaire_id(rs.getInt(1));
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
    }

    @Override
    public void update(Commentaire pObj) throws DALException {
        pstmt = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_Update);
            pstmt.setInt(1, pObj.getCommentaire_id());
            pstmt.setInt(2, pObj.getCommentaire_annonce_id());
            pstmt.setInt(3, pObj.getCommentaire_note());
            pstmt.setString(4, pObj.getCommentaire_message());
            pstmt.setDate(5, (Date) pObj.getCommentaire_date_parution());

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
    public void delete(Commentaire pObj) throws DALException {
        pstmt = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_Delete);
            pstmt.setInt(1, pObj.getCommentaire_id());
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
    public List<Commentaire> selectAll() throws DALException {
        stmt = null;
        rs = null;
        commentaire = null;
        commentaires = new ArrayList<>();
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            stmt = cnx.createStatement();
            rs = stmt.executeQuery(SQL_SelectAll);

            while (rs.next()) {
                commentaire = new Commentaire(
                        rs.getInt("commentaire_id"),
                        rs.getInt("commentaire_annonce_id"),
                        rs.getInt("commentaire_note"),
                        rs.getString("commentaire_message"),
                        rs.getDate("commentaire_date_parution")
                );

                commentaires.add(commentaire);
            }

            if (commentaires.size() == 0) {
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
        return commentaires;
    }

    @Override
    public Commentaire selectById(int pId) throws DALException {
        pstmt = null;
        rs = null;
        commentaire = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt =cnx.prepareStatement(SQL_SelectById);
            pstmt.setInt(1, pId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                commentaire = new Commentaire(
                        rs.getInt("commentaire_id"),
                        rs.getInt("commentaire_annonce_id"),
                        rs.getInt("commentaire_note"),
                        rs.getString("commentaire_message"),
                        rs.getDate("commentaire_date_parution")
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

        return commentaire;
    }


    public List<Commentaire> selectByIdUtilisateur (int commentaire_annonce_id) throws DALException {
        return null;
    }

    @Override
    public List<Commentaire> selectByIdAnnonce(int commentaire_annonce_id) throws DALException {
        return null;
    }

    @Override
    public boolean deleteByIdAnnonce(int pIdAnnonce) throws DALException {
        pstmt = null;
        boolean res = false;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_DeleteByIdAnnonce);
            pstmt.setInt(1, pIdAnnonce);

            pstmt.executeUpdate();

            res = true;
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

        return res;
    }

}//fin class
