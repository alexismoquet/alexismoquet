package com.dsi.model.dal.mysql;

import com.dsi.librairies.Roles;
import com.dsi.model.beans.UtilisateurBo;
import com.dsi.model.dal.DALException;
import com.dsi.model.dal.DAO_UtilisateurBo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAOUtilisateurBo_mysql_impl
 *
 * @author Christophe Michard
 * @since Créé le 05/02/2020
 */
public class DAOUtilisateurBo_mysql_impl implements DAO_UtilisateurBo {

    private String SQL_SelectAll        = "SELECT * FROM utilisateurs_bo;";
    private String SQL_SelectById       = "SELECT * FROM utilisateurs_bo WHERE utilisateur_bo_id=?;";
    private String SQL_Insert           = "INSERT INTO utilisateurs_bo(utilisateur_bo_login, utilisateur_bo_mot_de_passe, utilisateur_bo_role) VALUES(?,?,?);";
    private String SQL_Update           = "UPDATE utilisateurs_bo SET utilisateur_bo_login=?, utilisateur_bo_mot_de_passe=?, utilisateur_bo_role=? WHERE utilisateur_bo_id=?;";
    private String SQL_Delete           = "DELETE FROM utilisateurs_bo WHERE utilisateur_bo_id=?;";
    private String SQL_SelectByLogin    = "SELECT * FROM utilisateurs_bo WHERE utilisateur_bo_login=?;";

    private UtilisateurBo utilisateur;
    private Roles role;
    private List<UtilisateurBo> utilisateurs;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;

    public DAOUtilisateurBo_mysql_impl() {}

    @Override
    public void insert(UtilisateurBo pObj) throws DALException {
        pstmt = null;
        rs = null;

        try {
            //Execution de la requête
            pstmt = MysqlConnecteur.getConnection().prepareStatement(SQL_Insert);
            pstmt.setString(1, pObj.getLogin());
            pstmt.setString(2, pObj.getMdp());
            pstmt.setString(3, pObj.getRole().getLibelle());

            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            if (rs.next()){
                pObj.setIdUtilisateur(rs.getInt(1));
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
                MysqlConnecteur.closeConnexion();
            } catch (SQLException e) {
                throw new DALException("Problème lors de la fermeture de la connexion à la base de données !", e);
            }
        }
    }

    @Override
    public void update(UtilisateurBo pObj) throws DALException {
        pstmt = null;

        try {
            //Execution de la requête
            pstmt = MysqlConnecteur.getConnection().prepareStatement(SQL_Update);
            pstmt.setString(1, pObj.getLogin());
            pstmt.setString(2, pObj.getMdp());
            pstmt.setString(3, pObj.getRole().getLibelle());
            pstmt.setInt(4, pObj.getIdUtilisateur());

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
                MysqlConnecteur.closeConnexion();
            } catch (SQLException e) {
                throw new DALException("Problème lors de la fermeture de la connexion à la base de données !", e);
            }
        }
    }

    @Override
    public void delete(UtilisateurBo pObj) throws DALException {
        pstmt = null;

        try {
            //Execution de la requête
            pstmt = MysqlConnecteur.getConnection().prepareStatement(SQL_Delete);
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
                MysqlConnecteur.closeConnexion();
            } catch (SQLException e) {
                throw new DALException("Problème lors de la fermeture de la connexion à la base de données !", e);
            }
        }
    }

    @Override
    public UtilisateurBo selectById(int pId) throws DALException {
        pstmt = null;
        rs = null;
        utilisateur = null;

        try {
            //Execution de la requête
            pstmt = MysqlConnecteur.getConnection().prepareStatement(SQL_SelectById);
            pstmt.setInt(1, pId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                utilisateur = new UtilisateurBo(
                        rs.getInt("utilisateur_bo_id"),
                        rs.getString("utilisateur_bo_login"),
                        rs.getString("utilisateur_bo_mot_de_passe"),
                        chaineVersRoles(rs.getString("utilisateur_bo_role"))
                );
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
                MysqlConnecteur.closeConnexion();
            } catch (SQLException e) {
                throw new DALException("Problème lors de la fermeture de la connexion à la base de données !", e);
            }
        }

        return utilisateur;
    }

    @Override
    public List<UtilisateurBo> selectAll() throws DALException {
        stmt = null;
        rs = null;
        utilisateur = null;
        utilisateurs = new ArrayList<>();

        try {
            //Execution de la requête
            stmt = MysqlConnecteur.getConnection().createStatement();
            rs = stmt.executeQuery(SQL_SelectAll);

            //Récupération des enregistrements
            while (rs.next()) {
                utilisateur = new UtilisateurBo(
                        rs.getInt("utilisateur_bo_id"),
                        rs.getString("utilisateur_bo_login"),
                        rs.getString("utilisateur_bo_mot_de_passe"),
                        chaineVersRoles(rs.getString("utilisateur_bo_role"))
                );

                utilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            throw new DALException("Problème lors de la connexion à la base de données !", e);
        }finally {
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
                MysqlConnecteur.closeConnexion();
            } catch (SQLException e) {
                throw new DALException("Problème lors de la fermeture de la connexion à la base de données !", e);
            }
        }

        return utilisateurs;
    }

    @Override
    public UtilisateurBo selectByLogin(String pLogin) throws DALException {
        pstmt = null;
        rs = null;
        utilisateur = null;

        try {
            //Execution de la requête
            pstmt = MysqlConnecteur.getConnection().prepareStatement(SQL_SelectByLogin);
            pstmt.setString(1, pLogin);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                utilisateur = new UtilisateurBo(
                        rs.getInt("utilisateur_bo_id"),
                        rs.getString("utilisateur_bo_login"),
                        rs.getString("utilisateur_bo_mot_de_passe"),
                        chaineVersRoles(rs.getString("utilisateur_bo_role"))
                );
            }else {
                throw new DALException("Aucun utilisateur trouvé avec le login : "+pLogin);
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
                MysqlConnecteur.closeConnexion();
            } catch (SQLException e) {
                throw new DALException("Problème lors de la fermeture de la connexion à la base de données !", e);
            }
        }

        return utilisateur;
    }

    /**
     * Converti une chaine de caratères de role en enumération Role
     * @param role
     * @return
     */
    private Roles chaineVersRoles(String role){
        Roles r = null;

        if (role.equalsIgnoreCase(Roles.ADMIN.getLibelle())) {
            r = Roles.ADMIN;
        } else if (role.equalsIgnoreCase(Roles.MODERATEUR.getLibelle())) {
            r = Roles.MODERATEUR;
        }

        return r;
    }
}
