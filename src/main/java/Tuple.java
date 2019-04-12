
/**
 * Sequence of items
 */
public class Tuple {
	/**
	 * The item array
	 */
	private Item[] tuple;

	/**
	 * Construct a tuple
	 * @param size The number of items
	 */
	public Tuple(int size) {
		tuple = new Item[size];
	}

	/**
	 * Get the length
	 * @return The length
	 */
	public int getLength() {
		return tuple.length;
	}

	/**
	 * Get an item
	 * @param i The position of the item
	 * @return The item at position i
	 */
	public Item get(int i) {
		return tuple[i];
	}

	/**
	 * Add a new item
	 * @param item The item to add
	 * @param i The position in which place the item
	 */
	public void add(Item item, int i) {
		tuple[i] = item;
	}

	/**
	 * Get the distance from another tuple
	 * @param tuple The tuple from calculate the distance
	 * @return The actual distance
	 */
	public double getDistance(Tuple obj) {
		double distance = 0.0;

		for(int i = 0; i < getLength(); i++) {
			distance += tuple[i].distance(obj.get(i));
		}

		return distance;
	}

	/**
	 * Get the average distance from the items of a source data
	 * @param data The source data
	 * @param clustereData The indices of the indices from which calculate the distance
	 * @return The actual average distance
	 */
	public double avgDistance(Data data, int clusteredData[]) {
		double sumDist = 0.0;

		for(int i = 0; i < clusteredData.length; i++) {
			sumDist += getDistance(data.getItemSet(clusteredData[i]));
		}

		return sumDist / clusteredData.length;
	}
}
