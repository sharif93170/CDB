package com.excilys.cdb.exception;

public class DernierePageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9011441314161090676L;

	public DernierePageException() {
		super("Il s'agit de la derniere page, pas de page suivante !");
	}

}
