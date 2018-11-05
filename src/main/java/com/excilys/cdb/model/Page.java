package com.excilys.cdb.model;

import java.util.List;

import com.excilys.cdb.exception.DernierePageException;
import com.excilys.cdb.exception.PremierePageException;

public class Page {

	private static int page = 1;
	private static int pageSize = 10;

//	public static <T> List<T> pagination(List<T> sourceList) throws PremierePageException, DernierePageException {
//		if (pageSize <= 0 || page <= 0) {
//			throw new PremierePageException();
//		}
//
//		int fromIndex = (page - 1) * pageSize;
//		if (sourceList == null || sourceList.size() < fromIndex) {
//			throw new DernierePageException();
//		}
//		return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
//	}

	public static int getPage() {
		return page;
	}

	public static int getPageSize() {
		return pageSize;
	}

	public static void setPage(String page, String size) {
		if (page != null) {
			Page.page = Integer.parseInt(page);
		} else {
			Page.page = 1;
		}
		if (size != null) {
			Page.pageSize = Integer.parseInt(size);
		}
	}

//	public static void setPageSize(int pageSize) {
//		Page.pageSize = pageSize;
//	}

	public static void pagePlus() {
		Page.page += 1;
	}

	public static void pageMinus() {
		Page.page -= 1;
	}

}