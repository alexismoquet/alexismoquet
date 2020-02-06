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
	private static String bddUrl;
	private static String bddUser;
	private static String bddPassword;
	private static Connection cnx = null;

	/**
	 * Chargement du driver
	 */
	static {
		try {
			Class.forName(Parametres.getProperties("bddDriver"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		bddUrl = Parametres.getProperties("bddUrl");
		bddUser = Parametres.getProperties("bddUser");
		bddPassword = Parametres.getProperties("bddPassword");
	}

	/**
	 * Obtention de la connexion
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException{
		if (cnx == null) {
			cnx = DriverManager.getConnection(bddUrl, bddUser, bddPassword);
		}

		return cnx;
	}

	/**
	 * Fermeture de la connexion
	 */
	public static void closeConnexion() throws SQLException {
		if (cnx != null){
			cnx.close();
		}
	}
}
