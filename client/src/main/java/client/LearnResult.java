
package client;

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
	 * The data to plot.
	 */
	private PlotData plotData;

	/**
	 * Get the retrieved data string.
	 * @return The retrieved data string
	 */
	String getData() {
		return data;
	}

	/**
	 * Get the plot data.
	 * @return The data to plot
	 */
	PlotData getPlotData() {
		return plotData;
	}

	/**
	 * Set the retrieved data string.
	 * @param data The string to set
	 */
	void setData(String data) {
		this.data = data;
	}

	/**
	 * Set the plot data.
	 * @param plotData The data to set
	 */
	void setPlotData(PlotData plotData) {
		this.plotData = plotData;
	}
}

