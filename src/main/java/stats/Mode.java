
package stats;

import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

/**
 * Mode processor class.
 */
class Mode {

	/**
	 * Calculate the mode from samples.
	 * @param samples The samples data
	 * @return The mode
	 */
	static Object mode(Object[] samples) {
		Map<Object, Integer> occurences = new HashMap<Object, Integer>();

		for (Object o : samples) {
			if (occurences.containsKey(o)) {
				occurences.put(o, occurences.get(o) + 1);
			} else {
				occurences.put(o, 1);
			}
		}

		Object mode = null;
		Integer max = Integer.MIN_VALUE;

		for (Entry<Object, Integer> e : occurences.entrySet()) {
			if (e.getValue() > max) {
				mode = e.getKey();
				max = e.getValue();
			}
		}

		return mode;
	}
}

