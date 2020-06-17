package com.dsi.model.dal.mysql;

import com.dsi.librairies.FonctionsDate;
import com.dsi.librairies.Roles;
import com.dsi.model.beans.Adresse;
import com.dsi.model.beans.Annonce;
import com.dsi.model.beans.Utilisateur;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_Adresse;
import com.dsi.model.dal.DAO_Factory;
import com.dsi.model.dal.DAO_Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAOUtilisateur_mysql_impl
 *
 * @author Christophe Michard
 * @since Créé le 05/02/2020
 */
public class DAOUtilisateur_mysql_impl implements DAO_Utilisateur {

    private String SQL_SelectAll    = "SELECT * FROM utilisateurs;";
    private String SQL_SelectById   = "SELECT * FROM utilisateurs WHERE utilisateur_id=?;";
    private String SQL_Insert       = "INSERT INTO utilisateurs(utilisateur_nom, utilisateur_prenom, utilisateur_tel_fix, utilisateur_tel_mob, utilisateur_email, utilisateur_mot_de_passe, utilisateur_date_inscription) VALUES(?,?,?,?,?,?,?);";
    private String SQL_Update       = "UPDATE utlisateurs SET utilisateur_nom=?, utilisateur_prenom=?, utilisateur_tel_fix=?, utilisateur_tel_mob=?, utilisateur_email=?, utilisateur_mot_de_passe=?, utilisateur_date_inscription=? WHERE utilisateur_id=?;";
    private String SQL_Delete       = "DELETE FROM utilisateurs WHERE utilisateur_id=?;";

    private Utilisateur utilisateur;
    private Roles role;
    private List <Utilisateur> utilisateurs;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;

    public DAOUtilisateur_mysql_impl() {}

    @Override
    public void insert(Utilisateur pObj) throws DALException {
        pstmt = null;
        rs = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_Insert);
            pstmt.setString(1, pObj.getNom());
            pstmt.setString(2, pObj.getPrenom());
            pstmt.setString(3, pObj.getTelFix());
            pstmt.setString(4, pObj.getTelMob());
            pstmt.setString(5, pObj.getEmail());
            pstmt.setString(6, pObj.getMotDePasse());
            pstmt.setDate(7, (Date) pObj.getDateInscription());

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
    public void update(Utilisateur pObj) throws DALException {
        pstmt = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_Update);
            pstmt.setString(1, pObj.getNom());
            pstmt.setString(2, pObj.getPrenom());
            pstmt.setString(3, pObj.getTelFix());
            pstmt.setString(4, pObj.getTelMob());
            pstmt.setString(5, pObj.getEmail());
            pstmt.setString(6, pObj.getMotDePasse());
            pstmt.setDate(7, (Date) pObj.getDateInscription()); //parser en date sql -> new java.sql.Date(pObj.getDateInscription().getTime()));
            pstmt.setInt(8, pObj.getIdUtilisateur());

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
    public void delete(Utilisateur pObj) throws DALException {
        pstmt = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_Delete);
            pstmt.setInt(1, pObj.getIdUtilisateur());
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
    public Utilisateur selectById(int pId) throws DALException {
        pstmt = null;
        rs = null;
        utilisateur = null;
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            pstmt = cnx.prepareStatement(SQL_SelectById);
            pstmt.setInt(1, pId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                utilisateur = new Utilisateur(
                        rs.getInt("utilisateur_id"),
                        rs.getString("utilisateur_nom"),
                        rs.getString("utilisateur_prenom"),
                        rs.getString("utilisateur_tel_fix"),
                        rs.getString("utilisateur_tel_mob"),
                        rs.getString("utilisateur_email"),
                        rs.getString("utilisateur_mot_de_passe"),
                        FonctionsDate.sqlDateVersJavaDate(rs.getDate("utilisateur_date_inscription"))
                );

                DAO_Adresse dao = DAO_Factory.getDAO_Adresse();

                try {
                    List<Adresse> a = dao.selectByIdUtilisateur(rs.getInt("utilisateur_id"));
                    utilisateur.setAdresses(a);
                }catch (DALException e){
                    //On ne fait rien ici
                }

            }else {
                throw new DALException("Aucun utilisateur trouvé avec l'identifiant : "+pId);
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

        return utilisateur;
    }

    @Override
    public List<Utilisateur> selectAll() throws DALException {
        stmt = null;
        rs = null;
        utilisateur = null;
        utilisateurs = new ArrayList<>();
        Connection cnx = null;

        try {
            //Obtention de la connexion
            cnx = new MysqlConnecteur().getConnexion();

            //Execution de la requête
            stmt = cnx.createStatement();
            rs = stmt.executeQuery(SQL_SelectAll);

            //Récupération des enregistrements
            while (rs.next()) {
                utilisateur = new Utilisateur(
                        rs.getInt("utilisateur_id"),
                        rs.getString("utilisateur_nom"),
                        rs.getString("utilisateur_prenom"),
                        rs.getString("utilisateur_tel_fix"),
                        rs.getString("utilisateur_tel_mob"),
                        rs.getString("utilisateur_email"),
                        rs.getString("utilisateur_mot_de_passe"),
                        FonctionsDate.sqlDateVersJavaDate(rs.getDate("utilisateur_date_inscription"))
                );

                System.out.println(rs.getString("utilisateur_id"));

                DAO_Adresse dao = DAO_Factory.getDAO_Adresse();

                try {
                    List<Adresse> a = dao.selectByIdUtilisateur(rs.getInt("utilisateur_id"));
                    utilisateur.setAdresses(a);

                }catch (DALException e){
                    //On ne fait rien ici
                }

                utilisateurs.add(utilisateur);
            }

            if (utilisateurs.size() == 0) {
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
            if (cnx != null) {
                try {
                    cnx.close();
                } catch (SQLException e) {
                    throw new DALException("Problème lors de la fermeture de la connexion à la base de données !", e);
                }
            }
        }
        return utilisateurs;
    }
}//fin class
