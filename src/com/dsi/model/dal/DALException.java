package com.dsi.model.dal;

/**
 * Classe DALException
 *
 * @author Christophe Michard
 * @since Créé le 05/02/2020
 */
public class DALException extends Exception {

	/*******************************
	 *** Constructeurs *************
	 *******************************/

	/**
	 * Constructeur par défaut
	 */
	public DALException() { super(); }

	/**
	 * Constructeur
	 *
	 * @param message
	 */
	public DALException(String message) { super(message); }

	/**
	 * Constructeur
	 *
	 * @param message
	 * @param exception
	 */
	public DALException(String message, Throwable exception) { super(message, exception); }

	/*******************************
	 *** Méthodes ******************
	 *******************************/

	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer("Couche DAL - ");
		sb.append(super.getMessage());
		
		return sb.toString() ;
	}
	
	
}
