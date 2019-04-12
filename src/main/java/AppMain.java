
public class AppMain {

	public static void main(String[] args) {
		Data data = new Data();
		System.out.println(data);

		double radius = 2.0;
		QTMiner miner = new QTMiner(radius);

		int numIter = miner.compute(data);

		System.out.println("Number of clusters: " + numIter);
		System.out.println(miner.getClusterSet().toString(data));		
	}
}
