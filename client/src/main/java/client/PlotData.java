
import java.util.List;

import javafx.scene.chart.XYChart;
import javafx.collections.ObservableList;

/**
 * The data used for the scatter plot.
 */
class PlotData {

	/**
	 * An auto-incrementing id.
	 */
	private int id;

	/**
	 * The label of the series.
	 */
	private String label;

	/**
	 * The series of XY projected samples.
	 */
	private ObservableList<XYChart.Series<Number, Number>> xySeries;

	/**
	 * The series of XY projected samples.
	 */
	private ObservableList<XYChart.Series<Number, Number>> yzSeries;

	/**
	 * The series of XY projected samples.
	 */
	private ObservableList<XYChart.Series<Number, Number>> xzSeries;

	/**
	 * Construct a PlotData.
	 * @param label The label of the series
	 */
	PlotData(String label) {
		this.label = label;
		xySeries = new SeriesData();
		yzSeries = new SeriesData();
		xzSeries = new SeriesData();
	}

	/**
	 * Get the XY series data.
	 * @return The XY series data
	 */
	ObservableList<XYChart.Series<Number, Number>> getXYSeries() {
		return xySeries;
	}

	/**
	 * Get the YZ series data.
	 * @return The YZ series data
	 */
	ObservableList<XYChart.Series<Number, Number>> getYZSeries() {
		return yzSeries;
	}

	/**
	 * Get the XZ series data.
	 * @return The XZ series data
	 */
	ObservableList<XYChart.Series<Number, Number>> getXZSeries() {
		return xzSeries;
	}

	/**
	 * Add a serie of samples to the plot data.
	 * @param samples The serie of samples to add
	 */
	void addSerie(List<double[]> samples) {
		XYChart.Series xySerie = new XYChart.Series();
		XYChart.Series yzSerie = new XYChart.Series();
		XYChart.Series xzSerie = new XYChart.Series();

		xySerie.setName(label + id);
		yzSerie.setName(label + id);
		xzSerie.setName(label + id);

		for (double[] xyz : samples) {
			xySerie.getData().add(new XYChart.Data(xyz[0], xyz[1]));
			yzSerie.getData().add(new XYChart.Data(xyz[1], xyz[2]));
			xzSerie.getData().add(new XYChart.Data(xyz[0], xyz[2]));
		}

		xySeries.add(xySerie);
		yzSeries.add(yzSerie);
		xzSeries.add(xzSerie);

		id++;
	}
}
