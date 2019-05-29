
package database;

import java.util.ArrayList;
import java.util.List;

/**
 * The example tuple class.
 */
public class Example implements Comparable<Example> {

	/**
	 * The array list of objects.
	 */
	private List<Object> example = new ArrayList<Object>();

	/**
	 * Add a new object to the example.
	 * @param o The object to add
	 */
	public void add(Object o) {
		example.add(o);
	}

	/**
	 * Get the object at index i.
	 * @param i The index of the object
	 * @return The object at index i
	 */
	public Object get(int i) {
		return example.get(i);
	}

	/**
	 * Get the number of elements in the example.
	 * @return The number of elements
	 */
	public int size() {
		return example.size();
	}

	/**
	 * Compare to another example.
	 * @param ex The other example
	 * @return 1 if this example is > than ex,
	 *        -1 if this example is < than ex,
	 *         0 otherwise
	 */
	public int compareTo(Example ex) {
		int i = 0;

		for (Object o : ex.example) {
			if (!o.equals(this.example.get(i))) {
				return ((Comparable) o).compareTo(example.get(i));
			}

			i++;
		}

		return 0;
	}

	/**
	 * Convert the example to a string.
	 * @return The string rappresentation of the example
	 */
	public String toString() {
		String str = "";

		for (Object o : example) {
			str += o.toString() + " ";
		}

		return str;
	}
}
