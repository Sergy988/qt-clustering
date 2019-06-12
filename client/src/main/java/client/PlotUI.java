
import java.util.List;

import java.io.IOException;

import javafx.scene.layout.HBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;

import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;

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
	 * The plot data to show.
	 */
	private PlotData plotData;

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

		xyButton.setOnAction(event -> updateChart());
		yzButton.setOnAction(event -> updateChart());
		xzButton.setOnAction(event -> updateChart());

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
	 * Set the plot data to show.
	 * @param data The plot data to show
	 */
	void setData(PlotData data) {
		plotData = data;
		updateChart();
	}

	/**
	 * Update the chart based on the pressed button.
	 */
	private void updateChart() {
		if (plotData == null) {
			return;
		}

		RadioButton selected = (RadioButton) group.getSelectedToggle();

		if (selected == xyButton) {
			chart.setData(plotData.getXYSeries());
		} else if (selected == yzButton) {
			chart.setData(plotData.getYZSeries());
		} else {
			chart.setData(plotData.getXZSeries());
		}
	}
}
