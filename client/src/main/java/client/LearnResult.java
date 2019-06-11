
import java.util.List;
import java.util.LinkedList;

/**
 * The learn result computed by the server.
 */
class LearnResult {

	/**
	 * The retrieved data string.
	 */
	private String data;

	/**
	 * The samples to plot.
	 */
	private List<PlotData> samples;

	/**
	 * Construct a LearnResult.
	 */
	LearnResult() {
		data = new String("");
		samples = new LinkedList<PlotData>();
	}

	/**
	 * Get the retrieved data string.
	 * @return The retrieved data string
	 */
	String getData() {
		return data;
	}

	/**
	 * Get the samples to plot.
	 * @return The samples to plot
	 */
	List<PlotData> getSamples() {
		return samples;
	}

	/**
	 * Set the retrieved data string.
	 * @param data The string to set
	 */
	void setData(String data) {
		this.data = data;
	}

	/**
	 * Add a new PlotData to the list of data to plot.
	 * @param plotData The data to plot to add
	 */
	void addPlotData(PlotData plotData) {
		samples.add(plotData);
	}
}

