package com.dsi.model.bll;

/**
 * Classe BLLException
 *
 * @author Christophe Michard
 * @since Créé le 05/02/2020
 */
public class BLLException extends Exception {

	/*******************************
	 *** Constructeurs *************
	 *******************************/

	/**
	 * Constructeur par défaut
	 */
	public BLLException() { super(); }

	/**
	 * Constructeur
	 *
	 * @param message
	 */
	public BLLException(String message) { super(message); }

	/**
	 * Constructeur
	 *
	 * @param message
	 * @param exception
	 */
	public BLLException(String message, Throwable exception) { super(message, exception); }


	/*******************************
	 *** Méthodes ******************
	 *******************************/

	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer("Couche BLL - ");
		sb.append(super.getMessage());

		return sb.toString() ;
	}
}
