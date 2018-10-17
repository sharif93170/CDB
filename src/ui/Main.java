package ui;

import java.sql.SQLException;
import java.util.Scanner;

import service.CompanyService;
import service.ComputerService;

public class Main {

	public static void main(String[] args) throws SQLException {

		System.out.println("##### Bienvenue sur Computer Database #####");
		int choix;
		boolean continuer = true;
		ComputerService cptServ;
		CompanyService cpnServ;

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
			
			switch (choix) {

			case 1:
				cptServ = ComputerService.getInstance();
				System.out.println("Liste de tous les Computers : ");
				cptServ.getListComputers().stream().forEach(System.out::println);
				break;

			case 2:
				cpnServ = CompanyService.getInstance();
				System.out.println("Liste de toutes les Companies : ");
				cpnServ.getListCompanies().stream().forEach(System.out::println);
				break;

			case 3:
				cptServ = ComputerService.getInstance();
				System.out.println("Détails d'un Computer : ");
				cptServ.showDetails();
				break;

			case 4:
				cptServ = ComputerService.getInstance();
				System.out.println("Ajout d'un Computer :");
				cptServ.create();
				break;

			case 5:
				cptServ = ComputerService.getInstance();
				System.out.println("Modification d'un Computer : ");
				cptServ.update();
				break;

			case 6:
				cptServ = ComputerService.getInstance();
				System.out.println("Suppression d'un Computer par id : ");
				cptServ.deleteById();
				break;

			case 7:
				cptServ = ComputerService.getInstance();
				System.out.println("Suppression d'un Computer par nom : ");
				cptServ.deleteByName();
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
