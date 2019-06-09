
import java.util.List;

import java.io.IOException;

import javafx.scene.layout.HBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

import javafx.collections.ObservableList;

/**
 * The result plotter UI.
 */
class PlotUI extends ClientUI {

	/**
	 * The preferred scatter plot size.
	 */
	private static final int PLOT_SIZE = 640;

	/**
	 * The scatter plot chart.
	 */
	private ScatterChart<Number, Number> chart;

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
	 * The toggle coordinates group.
	 */
	private ToggleGroup group;

	/**
	 * The XY radio button.
	 */
	private RadioButton xyButton;

	/**
	 * The YZ radio button.
	 */
	private RadioButton yzButton;

	/**
	 * The XZ radio button.
	 */
	private RadioButton xzButton;

	/**
	 * Construct a result plotter UI.
	 * @param client A reference to the client
	 */
	PlotUI(ClientMain client) {
		super(client);

		group = new ToggleGroup();
		xyButton = new RadioButton("XY");
		yzButton = new RadioButton("YZ");
		xzButton = new RadioButton("XZ");
		xyButton.setToggleGroup(group);
		yzButton.setToggleGroup(group);
		xzButton.setToggleGroup(group);
		xyButton.setSelected(true);

		xyButton.setOnAction(event -> chart.setData(xySeries));
		yzButton.setOnAction(event -> chart.setData(yzSeries));
		xzButton.setOnAction(event -> chart.setData(xzSeries));

		HBox hbox = new HBox(32.0, xyButton, yzButton, xzButton);

		add(hbox, 0, 0);

		NumberAxis xAxis = new NumberAxis(-3.0, +3.0, 0.1);
		NumberAxis yAxis = new NumberAxis(-3.0, +3.0, 0.1);

		chart = new ScatterChart(xAxis, yAxis);
		chart.setTitle("Scatter plot:");
		chart.setPrefWidth(PLOT_SIZE);
		chart.setPrefHeight(PLOT_SIZE);
		add(chart, 0, 1);
	}

	/**
	 * Set the data samples to plot.
	 * @param samplesList The samples list
	 */
	void setData(List<PlotData> samplesList) {
		xySeries = new SeriesData();
		yzSeries = new SeriesData();
		xzSeries = new SeriesData();

		int id = 0;

		for (PlotData data : samplesList) {
			XYChart.Series xySerie = new XYChart.Series();
			XYChart.Series yzSerie = new XYChart.Series();
			XYChart.Series xzSerie = new XYChart.Series();

			xySerie.setName("Cluster #" + id);
			yzSerie.setName("Cluster #" + id);
			xzSerie.setName("Cluster #" + id);

			for (double[] xyz : data) {
				xySerie.getData().add(new XYChart.Data(xyz[0], xyz[1]));
				yzSerie.getData().add(new XYChart.Data(xyz[1], xyz[2]));
				xzSerie.getData().add(new XYChart.Data(xyz[0], xyz[2]));
			}

			xySeries.add(xySerie);
			yzSeries.add(yzSerie);
			xzSeries.add(xzSerie);
		}

		resetButtons();
		chart.setData(xySeries);
	}

	/**
	 * Clear the data.
	 */
	void clearData() {
		chart.getData().clear();
	}

	/**
	 * Reset the buttons state.
	 */
	void resetButtons() {
		xyButton.setSelected(true);
	}
}
