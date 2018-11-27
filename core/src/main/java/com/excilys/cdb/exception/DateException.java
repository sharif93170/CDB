package com.excilys.cdb.exception;

public class DateException extends DataException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2589724910747212308L;

	public DateException() {
		super("Introduced doit être antérieur à Discontinued");
	}

}
