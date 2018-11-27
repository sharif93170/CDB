package com.excilys.cdb.exceptions;

public class ComputerServiceNameTooShortException extends CdbException {

	private static final long serialVersionUID = 1L;

	public ComputerServiceNameTooShortException(String message) {
		super(message + " Il faut 5 caracteres minimum !");
	}

}