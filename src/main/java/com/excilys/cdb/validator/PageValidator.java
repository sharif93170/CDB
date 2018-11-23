//package com.excilys.cdb.validator;
//
//import java.util.List;
//
//import com.excilys.cdb.exception.DernierePageException;
//import com.excilys.cdb.exception.PremierePageException;
//import com.excilys.cdb.model.Computer;
//import com.excilys.cdb.model.Page;
//
//public class PageValidator {
//
//	public static void previousPageValidator() throws PremierePageException {
//		if (Page.getPageSize() <= 0 || Page.getPage() <= 0) {
//			throw new PremierePageException();
//		}
//	}
//
//	public static void nextPageValidator(List<Computer> sourceList) throws DernierePageException {
//		if (Page.getPage() > 1 && sourceList.size() == 0) {
//			throw new DernierePageException();
//		}
//	}
//
//}
