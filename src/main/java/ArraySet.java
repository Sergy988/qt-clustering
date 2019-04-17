
import java.util.Arrays;

/**
 * Integer array implementation as boolean array
 */
public class ArraySet {

	/**
	 * The size of the array set
	 */
	private int size;

	/**
	 * The array set
	 */
	private boolean set[];

	/**
	 * Instantiate a new array set
	 */
	public ArraySet() {
		set = new boolean[64];
		Arrays.fill(set, false);
	}

	/**
	 * Add a new integer
	 * @param i The integer to add
	 * @return true if the integer wasn't present in the set, false otherwise
	 */
	public boolean add(int i) {
		while(i >= set.length)
		{
			boolean tmp[] = new boolean[set.length * 2];
			Arrays.fill(tmp, false);
			System.arraycopy(set, 0, tmp, 0, set.length);
			set = tmp;
		}

		if(set[i]) {
			return false;
		}

		set[i] = true;
		size++;

		return true;
	}

	/**
	 * Delete an integer
	 * @param i The integer to delete
	 * @return true if the integer has been removed, false otherwise
	 */
	public boolean delete(int i){
		if(i < set.length) {
			if(!set[i]) {
				return false;
			}

			set[i] = false;
			size--;

			return true;
		}

		return false;
	}

	/**
	 * Check if the set contains the integer
	 * @param i The integer
	 * @return true if the set contains the integer, false otherwise
	 */
	public boolean get(int i) {
		return set[i];
	}

	/**
	 * Get the size of the array set
	 * @return The size of the array set
	 */
	public int size() {
		return size;
	}

	/**
	 * Convert the array set (boolean array) to an array of integers
	 * @return An array of integer
	 */
	public int[] toArray() {
		int array[] = new int[0];

		for(int i = 0; i < set.length; i++) {
			if(get(i)) {
				int tmp[] = new int[array.length + 1];
				System.arraycopy(array, 0, tmp, 0, array.length);
				array = tmp;
				array[array.length - 1] = i;
			}
		}

		return array;
	}
}
