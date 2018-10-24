package com.excilys.cdb.model;

import java.util.List;

import com.excilys.cdb.exception.DernierePageException;
import com.excilys.cdb.exception.PremierePageException;

public class PageNew {

	private static int page;
	private static int pageSize = 10;

	public static <T> List<T> pagination(List<T> sourceList, int page, int pageSize)
			throws PremierePageException, DernierePageException {
		if (pageSize <= 0 || page <= 0) {
			throw new PremierePageException();
		}

		int fromIndex = (page - 1) * pageSize;
		if (sourceList == null || sourceList.size() < fromIndex) {
			throw new DernierePageException();
		}
		return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
	}

	public static int getPage() {
		return page;
	}

	public static int getPageSize() {
		return pageSize;
	}

	public static void setPage(int page) {
		PageNew.page = page;
	}

	public static void setPageSize(int pageSize) {
		PageNew.pageSize = pageSize;
	}

}
