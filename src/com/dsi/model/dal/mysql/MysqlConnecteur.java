package com.dsi.model.dal.mysql;

import com.dsi.Parametres;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe MysqlConnecteur
 *
 * @author Christophe Michard
 * @since Créé 05/02/2020
 */
public class MysqlConnecteur {
    private String bddUrl;
    private String bddUser;
    private String bddPassword;

    public MysqlConnecteur() throws SQLException {
        try {
            Class.forName(Parametres.getProperties("bddDriver_mariadb"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        bddUrl = Parametres.getProperties("bddUrl_mariadb");
        bddUser = Parametres.getProperties("bddUser");
        bddPassword = Parametres.getProperties("bddPassword");
    }

    public Connection getConnexion() throws SQLException {
		return DriverManager.getConnection(bddUrl, bddUser, bddPassword);
	}
}
