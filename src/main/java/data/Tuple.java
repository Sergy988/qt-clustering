
package data;

import java.util.Set;
import java.io.Serializable;

/**
 * Sequence of items.
 */
public class Tuple implements Serializable {
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
	 * Get the difference from another tuple.
	 * @param tuple The tuple from calculate the difference
	 * @return The actual difference
	 */
	public double getDifference(Tuple tuple) {
		double difference = 0.0;

		for (int i = 0; i < getLength(); i++) {
			difference += get(i).difference(tuple.get(i).getValue());
		}

		return difference;
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

	/**
	 * Convert the tuple to a string.
	 * @return The string rappresentation of the tuple
	 */
	public String toString() {
		String str = "[";

		int i = 0;

		for (; i < getLength() - 1; i++) {
			str += tuple[i] + ", ";
		}

		str += tuple[i] + "]";

		return str;
	}
}
