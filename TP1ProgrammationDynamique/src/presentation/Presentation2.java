package presentation;

import dao.IDao;
import metier.IMetier;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Presentation2 {
	public static void main(String[] args) throws Exception {
		File configFile = new File("config.txt");

		try (Scanner scanner = new Scanner(configFile)) {
			if (!scanner.hasNextLine()) {
				throw new IllegalStateException("Le fichier config.txt est vide.");
			}
			String daoClassName = scanner.nextLine().trim();

			if (!scanner.hasNextLine()) {
				throw new IllegalStateException("Il manque la classe métier dans config.txt.");
			}
			String metierClassName = scanner.nextLine().trim();

			Class<?> cDao = Class.forName(daoClassName);
			IDao dao = (IDao) cDao.getDeclaredConstructor().newInstance();

			Class<?> cMetier = Class.forName(metierClassName);
			IMetier metier = (IMetier) cMetier.getDeclaredConstructor().newInstance();

			Method setDaoMethod = cMetier.getMethod("setDao", IDao.class);
			setDaoMethod.invoke(metier, dao);

			System.out.println("Résultats = " + metier.calcul());
		}
	}
}
