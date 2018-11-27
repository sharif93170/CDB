package com.excilys.cdb.page;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Page<T> {
	private List<T> t;
	private int indexFirstPageElement;
	private int numerosPage;
	private int nombreElementPerPage;
	private int nombreElementTotal;

	public int getNombreElementTotal() {
		return nombreElementTotal;
	}

	public void setNombreElementTotal(int nombreElementTotal) {
		this.nombreElementTotal = nombreElementTotal;
	}

	static final Logger LOGGER = LoggerFactory.getLogger(Page.class);

	public Page(List<T> t, int nombreElementParPage, int nombreElementTotal) {
		this.numerosPage = 1;
		this.nombreElementPerPage = nombreElementParPage;
		this.indexFirstPageElement = 0;
		this.nombreElementTotal = nombreElementTotal;
		this.setObjetT(t);
	}

	public int getNombreElementPerPage() {
		return nombreElementPerPage;
	}

	public void setNombreElementPerPage(int nombreElementParPage) {
		this.nombreElementPerPage = nombreElementParPage;
		numerosPage = (this.nombreElementPerPage + indexFirstPageElement) / this.nombreElementPerPage;
	}

	public int getIndexFirstPageElement() {
		return indexFirstPageElement;
	}

	public void setIndexFirstPageElement(int indexFirstPageElement) {
		this.indexFirstPageElement = indexFirstPageElement;
	}

	public int getNumerosPage() {
		return numerosPage;
	}

	public void setNumerosPage(int numerosPage) {
		this.numerosPage = numerosPage;
	}

	public int nextPage() {
		int indexRetour = indexFirstPageElement;
		if (indexRetour < (nombreElementTotal - nombreElementPerPage)) {
			numerosPage++;
			indexRetour = indexFirstPageElement += nombreElementPerPage;

		} else {
			LOGGER.warn("Operation impossible, vous êtes à la dernière page");
		}
		return indexRetour;

	}

	public int previousPage() {
		int indexRetour = indexFirstPageElement;
		LOGGER.debug(" " + numerosPage);
		if (numerosPage > 1) {
			numerosPage--;
			indexRetour = indexFirstPageElement -= nombreElementPerPage;
		} else {
			LOGGER.warn("Operation impossible, vous êtes à la première page");
		}
		return indexRetour;
	}

	public void setCurrentPage(int pageToReach) {
		if (pageToReach > numerosPage) {
			if (pageToReach <= (nombreElementTotal / nombreElementPerPage) + 1) {
				numerosPage = pageToReach;
				indexFirstPageElement = (numerosPage - 1) * nombreElementPerPage;
			} else {
				LOGGER.warn("the page to reach is out of superior bound : " + pageToReach);
			}
		} else {
			if (pageToReach > 0) {
				numerosPage = pageToReach;
				indexFirstPageElement = (numerosPage - 1) * nombreElementPerPage;
			} else {
				LOGGER.warn("the page to reach is out of inferior bound : " + pageToReach);
			}
		}

	}

	public List<T> getObjetT() {
		return t;
	}

	public void setObjetT(List<T> objetT) {
		this.t = objetT;
	}

}