package com.excilys.cdb.ui;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import com.excilys.cdb.exception.DernierePageException;
import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

public class Main {

	public static void main(String[] args) throws SQLException, PremierePageException, DernierePageException {

		System.out.println("##### Bienvenue sur Computer Database #####");
		int choix;
		boolean continuer = true;
		ComputerService computerService;
		CompanyService companyService;

		while (continuer) {
			System.out.println("\n##### Menu - Saisissez votre choix : #####");
			System.out.println("1 - Lister tous les Computers");
			System.out.println("2 - Lister toutes les Companies");
			System.out.println("3 - Afficher les détails d'un Computer");
			System.out.println("4 - Créer un nouveau Computer");
			System.out.println("5 - Modifier un Computer");
			System.out.println("6 - Supprimer un Computer par id");
			System.out.println("7 - Supprimer un Computer par nom");
			System.out.println("8 - Quitter");

			System.out.println("\nVotre choix : ");

			Scanner sc = new Scanner(System.in);
			choix = sc.nextInt();
			sc.nextLine();

			switch (choix) {

			case 1:
				computerService = ComputerService.getInstance();
				System.out.println("Liste de tous les Computers : ");
				Page.pagination(computerService.findAll()).stream().forEach(System.out::println);
				String choix2;
				boolean continuer2 = true;
				while (continuer2) {
					try {
						System.out.println("Appuyez S pour page suivante, P pour page precedente, Q pour quitter.");
						choix2 = sc.nextLine();
						if ("s".equals(choix2) || "S".equals(choix2)) {
							Page.setPage(Page.getPage() + 1);
							Page.pagination(computerService.findAll()).stream().forEach(System.out::println);
						} else if ("p".equals(choix2) || "P".equals(choix2)) {
							Page.setPage(Page.getPage() - 1);
							Page.pagination(computerService.findAll()).stream().forEach(System.out::println);
						} else if ("q".equals(choix2) || "Q".equals(choix2)) {
							continuer2 = false;
						} else {
							System.out.println("Mauvaise saisie, veuillez utiliser 'S', 'P' ou 'Q'.");
						}
					} catch (PremierePageException p) {
						System.out.println(p.getMessage());
					} catch (DernierePageException d) {
						System.out.println(d.getMessage());
					}
				}
				break;

			case 2:
				companyService = CompanyService.getInstance();
				System.out.println("Liste de toutes les Companies : ");
				Page.pagination(companyService.findAll()).stream().forEach(System.out::println);
				String choix3;
				boolean continuer3 = true;
				while (continuer3) {
					try {
						System.out.println("Appuyez S pour page suivante, P pour page precedente, Q pour quitter.");
						choix3 = sc.nextLine();
						if ("s".equals(choix3) || "S".equals(choix3)) {
							Page.setPage(Page.getPage() + 1);
							Page.pagination(companyService.findAll()).stream().forEach(System.out::println);
						} else if ("p".equals(choix3) || "P".equals(choix3)) {
							Page.setPage(Page.getPage() - 1);
							Page.pagination(companyService.findAll()).stream().forEach(System.out::println);
						} else if ("q".equals(choix3) || "Q".equals(choix3)) {
							continuer3 = false;
						} else {
							System.out.println("Mauvaise saisie, veuillez utiliser 'S', 'P' ou 'Q'.");
						}
					} catch (PremierePageException p) {
						System.out.println(p.getMessage());
					} catch (DernierePageException d) {
						System.out.println(d.getMessage());
					}
				}
				break;

			case 3:
				computerService = ComputerService.getInstance();
				System.out.println("Veuillez saisir l'id du produit pour afficher ses détails : ");
				System.out.println("Détails d'un Computer : ");
				int idComputer = sc.nextInt();
				sc.nextLine();
				Computer cpt = computerService.showDetails(idComputer);
				System.out.println("Id = " + cpt.getId() + ", Name = " + cpt.getName() + ", Introduced = "
						+ cpt.getIntroducedDate() + ", Discontinued = " + cpt.getDiscontinuedDate() + ", IdCompany = "
						+ cpt.getCompany().getId() + ", CompanyName = " + cpt.getCompany().getName() + ".");
				break;

			case 4:
				computerService = ComputerService.getInstance();

				System.out.println("Ajout d'un Computer :");

				String createName;
				LocalDate createIntroduced;
				LocalDate createDiscontinued;
				Long createIdCompany;

				int createDay, createMonth, createYear;

				System.out.println("Veuillez saisir le nom de l'ordinateur à ajouter : ");
				createName = sc.nextLine();

				System.out.println("Saisie de la date d'introduction : ");
				System.out.println("Veuillez saisir le jour d'introduction :");
				createDay = sc.nextInt();

				System.out.println("Veuillez saisir le mois d'introduction :");
				createMonth = sc.nextInt();

				System.out.println("Veuillez saisir l'année d'introduction :");
				createYear = sc.nextInt();

				createIntroduced = LocalDate.of(createYear, createMonth, createDay);

				System.out.println("Saisie de la date d'arret : ");
				System.out.println("Veuillez saisir le jour d'arret : ");
				createDay = sc.nextInt();

				System.out.println("Veuillez saisir le mois d'arret : ");
				createMonth = sc.nextInt();

				System.out.println("Veuillez saisir l'année d'arret : ");
				createYear = sc.nextInt();

				createDiscontinued = LocalDate.of(createYear, createMonth, createDay);

				System.out.println("Veuillez saisir l'id de la compagnie");
				createIdCompany = sc.nextLong();

				computerService.create(
						new Computer(createName, createIntroduced, createDiscontinued, new Company(createIdCompany)));
				break;

			case 5:
				computerService = ComputerService.getInstance();
				System.out.println("Modification d'un Computer : ");
				int updateId;
				String updateName;
				LocalDate updateIntroduced;
				LocalDate updateDiscontinued;
				Long updateIdCompany;

				int updateDay, updateMonth, updateYear;

				System.out.println("Veuillez saisir l'id du produit à modifier : ");
				updateId = sc.nextInt();
				sc.nextLine();

				System.out.println("Veuillez saisir le nouveau nom du produit : ");
				updateName = sc.nextLine();

				System.out.println("Modification de la date d'introdution : ");
				System.out.println("Veuillez saisir le nouveau jour d'introduction :");
				updateDay = sc.nextInt();

				System.out.println("Veuillez saisir le nouveau mois d'introduction :");
				updateMonth = sc.nextInt();

				System.out.println("Veuillez saisir la nouvelle année d'introduction :");
				updateYear = sc.nextInt();

				updateIntroduced = LocalDate.of(updateYear, updateMonth, updateDay);

				System.out.println("Modification de la date d'arret : ");
				System.out.println("Veuillez saisir le nouveau jour d'arret : ");
				updateDay = sc.nextInt();

				System.out.println("Veuillez saisir le nouveau  mois d'arret : ");
				updateMonth = sc.nextInt();

				System.out.println("Veuillez saisir la nouvelle année d'arret : ");
				updateYear = sc.nextInt();

				updateDiscontinued = LocalDate.of(updateYear, updateMonth, updateDay);

				System.out.println("Veuillez saisir l'id de la compagnie");
				updateIdCompany = sc.nextLong();
				computerService.update(updateId,
						new Computer(updateName, updateIntroduced, updateDiscontinued, new Company(updateIdCompany)));
				break;

			case 6:
				computerService = ComputerService.getInstance();
				System.out.println("Suppression d'un Computer par id : ");

				int idToDelete;

				System.out.println("Veuillez saisir l'id de l'ordinateur à supprimer : ");
				idToDelete = sc.nextInt();
				sc.nextLine();

				computerService.deleteById(idToDelete);
				break;

			case 7:
				computerService = ComputerService.getInstance();
				System.out.println("Suppression d'un Computer par nom : ");

				String nameToDelete;

				System.out.println("Veuillez saisir l'id de l'ordinateur à supprimer : ");
				nameToDelete = sc.nextLine();

				computerService.deleteByName(nameToDelete);
				break;

			case 8:
				continuer = false;
				System.out.println("Au revoir, à la prochaine !");
				break;

			default:
				System.out.println("Erreur de saisie");
				break;

			}

		}

	}

}