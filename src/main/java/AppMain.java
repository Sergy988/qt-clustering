
import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.SQLException;

import keyboardinput.Keyboard;

import mining.ClusteringRadiusException;
import mining.QTMiner;

import data.Data;
import data.EmptyDatasetException;

import database.EmptySetException;
import database.DatabaseConnectionException;

/**
 * The main application class.
 */
public class AppMain {
	/**
	 * The program entry point.
	 * @param args The input arguments
	 */
	public static void main(String[] args) {
		AppMain main = new AppMain();

		while (true) {
			int menuAnswer = main.menu();

			switch (menuAnswer) {
				case 1:
					main.learningFromFile();
					break;
				case 2:
					main.learningFromData();
					break;
				default:
					System.out.println("Invalid option!");
					break;
			}

			System.out.print(
				"Would you choose another option from the menu? (y/n): "
			);

			if (Character.toUpperCase(Keyboard.readChar()) != 'Y') {
				break;
			}
		}
	}

	/**
	 * Print the menu and ask the user for a choice.
	 * @return The choice (`1` for load clusters from file or
	 *                     `2` for load data)
	 */
	private int menu() {
		int answer;

		do {
			System.out.println("(1) Load Clusters from File");
			System.out.println("(2) Load Data");
			System.out.print("(1/2): ");

			answer = Keyboard.readInt();
		} while (answer < 1 || answer > 2);

		return answer;
	}

	/**
	 * Load a cluster set from file.
	 */
	private void learningFromFile() {
		System.out.print("File name: ");
		String filename = Keyboard.readString();

		QTMiner qt = null;

		try {
			qt = new QTMiner(filename + ".dmp");
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
		}

		if (qt != null) {
			System.out.println(qt);
		}
	}

	/**
	 * Calculate a cluster set from data.
	 */
	private void learningFromData() {
		System.out.print("Insert database table: ");
		String table = Keyboard.readString();

		Data data = null;

		try {
			data = new Data(table);
		} catch (ClassNotFoundException e) {
			System.err.println("Class " + e.getMessage() + " not found");
			return;
		} catch (SQLException e) {
			System.err.println("Invalid table: " + table);
			return;
		} catch (DatabaseConnectionException e) {
			System.err.println(e.getMessage());
			return;
		} catch (EmptySetException e) {
			System.err.println(e.getMessage());
			return;
		}

		System.out.println(data);

		char answer = 'y';

		while (Character.toUpperCase(answer) == 'Y') {
			double radius = 0.0;

			do {
				System.out.print("Insert radius (> 0.0): ");
				radius = Keyboard.readDouble();
			} while (radius < 1e-12);

			QTMiner qt = new QTMiner(radius);

			try {
				int numClusters = qt.compute(data);

				System.out.println("Number of clusters: " + numClusters);
				System.out.println(qt.getClusterSet().toString(data));
				System.out.print("Backup file name: ");

				String filename = Keyboard.readString() + ".dmp";

				System.out.println("Saving clusters in " + filename);

				try {
					qt.save(filename);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				System.out.println("Saving transaction ended!");
			} catch (EmptyDatasetException | ClusteringRadiusException e) {
				System.err.println(e.getMessage());
			}

			System.out.print("New execution? (y/n): ");
			answer = Keyboard.readChar();
		}
	}
}
