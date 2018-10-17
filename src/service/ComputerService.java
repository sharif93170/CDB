package service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import dao.DaoComputer;
import model.Computer;

public class ComputerService {

	private static ComputerService cs = null;
	DaoComputer dc;

	private ComputerService() {
		dc = DaoComputer.getInstance();
	}

	public static ComputerService getInstance() {
		if (cs == null) {
			cs = new ComputerService();
		}
		return cs;
	}

	public void showDetails() throws SQLException {
		int idComputer;

		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir l'id du produit pour afficher ses détails : ");
		idComputer = sc.nextInt();

		dc.showDetails(idComputer);
	}

	public ArrayList<Computer> getListComputers() throws SQLException {
		return dc.getListComputers();
	}

	public void create() {
		String name;
		LocalDate introduced;
		LocalDate discontinued;
		int idCompany;

		int day, month, year;

		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir le nom de l'ordinateur à ajouter : ");
		name = sc.nextLine();

		System.out.println("Saisie de la date d'introdution : ");
		System.out.println("Veuillez saisir le jour d'introduction :");
		day = sc.nextInt();

		System.out.println("Veuillez saisir le mois d'introduction :");
		month = sc.nextInt();

		System.out.println("Veuillez saisir l'année d'introduction :");
		year = sc.nextInt();

		introduced = LocalDate.of(year, month, day);

		System.out.println("Saisie de la date d'arret : ");
		System.out.println("Veuillez saisir le jour d'arret : ");
		day = sc.nextInt();

		System.out.println("Veuillez saisir le mois d'arret : ");
		month = sc.nextInt();

		System.out.println("Veuillez saisir l'année d'arret : ");
		year = sc.nextInt();

		discontinued = LocalDate.of(year, month, day);

		System.out.println("Veuillez saisir l'id de la compagnie");
		idCompany = sc.nextInt();

		dc.create(new Computer(name, introduced, discontinued, idCompany));

	}

	public void update() {
		int id;
		String name;
		LocalDate introduced;
		LocalDate discontinued;
		int idCompany;

		int day, month, year;

		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir l'id du produit à modifier : ");
		id = sc.nextInt();
		sc.nextLine();

		System.out.println("Veuillez saisir le nouveau nom du produit : ");
		name = sc.nextLine();

		System.out.println("Modification de la date d'introdution : ");
		System.out.println("Veuillez saisir le nouveau jour d'introduction :");
		day = sc.nextInt();

		System.out.println("Veuillez saisir le nouveau mois d'introduction :");
		month = sc.nextInt();

		System.out.println("Veuillez saisir la nouvelle année d'introduction :");
		year = sc.nextInt();

		introduced = LocalDate.of(year, month, day);

		System.out.println("Modification de la date d'arret : ");
		System.out.println("Veuillez saisir le nouveau jour d'arret : ");
		day = sc.nextInt();

		System.out.println("Veuillez saisir le nouveau  mois d'arret : ");
		month = sc.nextInt();

		System.out.println("Veuillez saisir la nouvelle année d'arret : ");
		year = sc.nextInt();

		discontinued = LocalDate.of(year, month, day);

		System.out.println("Veuillez saisir l'id de la compagnie");
		idCompany = sc.nextInt();

		dc.update(id, new Computer(name, introduced, discontinued, idCompany));
	}

	public void deleteByName() {
		String nameToDelete;

		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir le nom de l'ordinateur à supprimer : ");
		nameToDelete = sc.nextLine();

		dc.deleteByName(nameToDelete);
	}

	public void deleteById() {
		int idToDelete;

		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir l'id de l'ordinateur à supprimer : ");
		idToDelete = sc.nextInt();

		dc.deleteById(idToDelete);
	}

}
