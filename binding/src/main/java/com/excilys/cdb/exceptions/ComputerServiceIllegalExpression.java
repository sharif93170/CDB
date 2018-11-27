package com.excilys.cdb.exceptions;

public class ComputerServiceIllegalExpression extends CdbException {

	private static final long serialVersionUID = 1L;

	public ComputerServiceIllegalExpression(String message) {
		super(message + " Mauvais carecteres dans le nom !");
	}
}