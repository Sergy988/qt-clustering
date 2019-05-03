
import keyboardinput.Keyboard;

/**
 * Main Application.
 */
public class AppMain {

	/**
	 * Program entry point.
	 * @param args Program arguments
	 */
	public static void main(String[] args) {
		Data data = new Data();
		System.out.println(data);

		while (true) {
			double radius = 0.0;

			do {
				System.out.print("Insert radius (>0): ");
				radius = Keyboard.readDouble();
			} while (radius < 1e-12);

			QTMiner miner = new QTMiner(radius);

			try {
				int numIter = miner.compute(data);
				System.out.println("Number of clusters: " + numIter);
			} catch (EmptyDatasetException e) {
				System.out.println(e);
				break;
			} catch (ClusteringRadiusException e) {
				System.out.println(e);
				continue;
			}

			System.out.println(miner.getClusterSet().toString(data));

			System.out.print("New execution? (y/n): ");

			if (Keyboard.readChar() != 'y')
				break;
		}
	}
}
