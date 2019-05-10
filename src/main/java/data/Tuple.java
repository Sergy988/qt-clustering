
package data;

import java.util.Set;

/**
 * Sequence of items.
 */
public class Tuple {
	/**
	 * The item array.
	 */
	private Item[] tuple;

	/**
	 * Construct a tuple.
	 * @param size The number of items
	 */
	public Tuple(int size) {
		tuple = new Item[size];
	}

	/**
	 * Get the length.
	 * @return The length
	 */
	public int getLength() {
		return tuple.length;
	}

	/**
	 * Get an item.
	 * @param i The position of the item
	 * @return The item at position i
	 */
	public Item get(int i) {
		return tuple[i];
	}

	/**
	 * Add a new item.
	 * @param item The item to add
	 * @param i The position in which place the item
	 */
	public void add(Item item, int i) {
		tuple[i] = item;
	}

	/**
	 * Get the distance from another tuple.
	 * @param tuple The tuple from calculate the distance
	 * @return The actual distance
	 */
	public double getDistance(Tuple tuple) {
		double distance = 0.0;

		for (int i = 0; i < getLength(); i++) {
			distance += get(i).distance(tuple.get(i).getValue());
		}

		return distance;
	}

	/**
	 * Get the average distance from the items of a source data.
	 * @param data The source data
	 * @param clusteredData The indices of the indices
	 * from which calculate the distance
	 * @return The actual average distance
	 */
	public double avgDistance(Data data, Set<Integer> clusteredData) {
		double sumDist = 0.0;

		for (Integer i : clusteredData) {
			sumDist += getDistance(data.getItemSet(i));
		}

		return sumDist / clusteredData.size();
	}
}
