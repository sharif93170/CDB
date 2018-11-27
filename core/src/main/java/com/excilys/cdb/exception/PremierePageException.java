package com.excilys.cdb.exception;

public class PremierePageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9185317948162943903L;

	public PremierePageException() {
		super("Il s'agit de la premiere page, pas de page precedente !");
	}

}
