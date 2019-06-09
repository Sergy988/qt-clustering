
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * The data used for the scatter plot.
 */
class PlotData implements Iterable<double[]> {

	/**
	 * The samples data.
	 */
	private List<double[]> samples;

	/**
	 * Set the samples data.
	 * @param samples The samples data
	 */
	PlotData(List<double[]> samples) {
		this.samples = samples;
	}

	/**
	 * Get an iterator to the coordinates of the samples.
	 */
	@Override
	public Iterator<double[]> iterator() {
		return samples.iterator();
	}
}
