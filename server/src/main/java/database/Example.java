
package database;

import java.util.ArrayList;
import java.util.List;

/**
 * The example tuple class.
 */
public class Example {

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
}
