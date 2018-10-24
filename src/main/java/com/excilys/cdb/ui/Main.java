package com.excilys.cdb.ui;

import java.sql.SQLException;
import java.util.Scanner;

import com.excilys.cdb.exception.DernierePageException;
import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.model.Computer;
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
				computerService.findAll(1).stream().forEach(System.out::println);
				String choix2;
				boolean continuer2 = true;
				int i = 1;
				while (continuer2) {
					System.out.println("Appuyez S pour page suivante, P pour page precedente, Q pour quitter.");
					choix2 = sc.nextLine();
					if ("s".equals(choix2) || "S".equals(choix2)) {
						computerService.findAll(i += 1).stream().forEach(System.out::println);
					} else if ("p".equals(choix2) || "P".equals(choix2)) {
						computerService.findAll(i -= 1).stream().forEach(System.out::println);
					} else if ("q".equals(choix2) || "Q".equals(choix2)) {
						continuer2 = false;
					} else {
						System.out.println("erreur");
					}
				}
				break;

			case 2:
				companyService = CompanyService.getInstance();
				System.out.println("Liste de toutes les Companies : ");
				companyService.findAll().stream().forEach(System.out::println);
				String choix3;
				boolean continuer3 = true;
				int j = 1;
//				while (continuer3) {
//					System.out.println("Appuyez S pour page suivante, P pour page precedente, Q pour quitter.");
//					choix3 = sc.nextLine();
//					if ("s".equals(choix3) || "S".equals(choix3)) {
//						companyService.findAll(j += 1).stream().forEach(System.out::println);
//					} else if ("p".equals(choix3) || "P".equals(choix3)) {
//						companyService.findAll(j -= 1).stream().forEach(System.out::println);
//					} else if ("q".equals(choix3) || "Q".equals(choix3)) {
//						continuer3 = false;
//					} else {
//						System.out.println("erreur");
//					}
//				}
				break;

			case 3:
				computerService = ComputerService.getInstance();
				System.out.println("Détails d'un Computer : ");
				Computer cpt = computerService.showDetails();
				System.out.println("Id = " + cpt.getId() + ", Name = " + cpt.getName() + ", Introduced = "
						+ cpt.getIntroducedDate() + ", Discontinued = " + cpt.getDiscontinuedDate() + ", IdCompany = "
						+ cpt.getCompany().getId() + ", CompanyName = " + cpt.getCompany().getName() + ".");
				break;

			case 4:
				computerService = ComputerService.getInstance();
				System.out.println("Ajout d'un Computer :");
				computerService.create();
				break;

			case 5:
				computerService = ComputerService.getInstance();
				System.out.println("Modification d'un Computer : ");
				computerService.update();
				break;

			case 6:
				computerService = ComputerService.getInstance();
				System.out.println("Suppression d'un Computer par id : ");
				computerService.deleteById();
				break;

			case 7:
				computerService = ComputerService.getInstance();
				System.out.println("Suppression d'un Computer par nom : ");
				computerService.deleteByName();
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
