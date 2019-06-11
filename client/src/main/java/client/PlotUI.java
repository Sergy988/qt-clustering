
import java.util.List;

import java.io.IOException;

import javafx.scene.layout.HBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;

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

		DropShadow shadow = new DropShadow();
		shadow.setOffsetX(4);
		shadow.setOffsetY(4);
		shadow.setColor(Color.GREY);
		chart.setEffect(shadow);

		add(chart, 0, 1);
	}

	/**
	 * Set the data samples to plot.
	 * @param samplesList The samples list
	 * @param label The string prefix of the labels
	 */
	void setData(List<PlotData> samplesList, String label) {
		xySeries = new SeriesData();
		yzSeries = new SeriesData();
		xzSeries = new SeriesData();

		int id = 0;

		for (PlotData data : samplesList) {
			XYChart.Series xySerie = new XYChart.Series();
			XYChart.Series yzSerie = new XYChart.Series();
			XYChart.Series xzSerie = new XYChart.Series();

			xySerie.setName(label + id);
			yzSerie.setName(label + id);
			xzSerie.setName(label + id);

			for (double[] xyz : data) {
				xySerie.getData().add(new XYChart.Data(xyz[0], xyz[1]));
				yzSerie.getData().add(new XYChart.Data(xyz[1], xyz[2]));
				xzSerie.getData().add(new XYChart.Data(xyz[0], xyz[2]));
			}

			xySeries.add(xySerie);
			yzSeries.add(yzSerie);
			xzSeries.add(xzSerie);

			id++;
		}

		updateChart();
	}

	/**
	 * Update the chart based on the pressed button.
	 */
	private void updateChart() {
		RadioButton selected = (RadioButton) group.getSelectedToggle();

		if (selected == xyButton) {
			chart.setData(xySeries);
		} else if (selected == yzButton) {
			chart.setData(yzSeries);
		} else {
			chart.setData(xzSeries);
		}
	}
}
