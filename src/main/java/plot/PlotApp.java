
package plot;

import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.LinkedList;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.application.Application;

import mining.Cluster;
import mining.ClusterSet;

import data.DataProjector;

import stats.Point2D;

public class PlotApp extends Application {

	private static List<Set<Point2D>> clustersPoints;

	public static void launch(
		final ClusterSet clusterSet, final DataProjector dataProj) {
		clustersPoints = new LinkedList<Set<Point2D>>();

		for (Cluster c : clusterSet) {
			Set<Point2D> points = new TreeSet<Point2D>();

			for (Integer i : c) {
				points.add(dataProj.getPoint2D(i));
			}

			clustersPoints.add(points);
		}

		PlotApp.launch(PlotApp.class);
	}

	@Override
	public void start(Stage stage) {
		stage.setTitle("QTMiner scatter plot");

		NumberAxis xAxis = new NumberAxis(-50, +50, +5);
		NumberAxis yAxis = new NumberAxis(-50, +50, +5);

		ScatterChart<Number, Number> chart = new ScatterChart<Number, Number>(
			xAxis, yAxis
		);

		int clusterId = 0;

		for (Set<Point2D> points : clustersPoints) {
			XYChart.Series series = new XYChart.Series();
			series.setName("Cluster #" + clusterId);

			for (Point2D p : points) {
				series.getData().add(new XYChart.Data(p.getX(), p.getY()));
			}

			chart.getData().add(series);

			clusterId++;
		}

		Scene scene = new Scene(chart, 800, 600);

		stage.setScene(scene);
		stage.show();
	}
}
