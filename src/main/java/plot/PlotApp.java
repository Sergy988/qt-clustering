
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

import data.PCA;

import stats.Point3D;

/**
 * The scatter-plot plotter application.
 */
public class PlotApp extends Application {

	/**
	 * The width of the window.
	 */
	private static final int WIN_WIDTH = 800;

	/**
	 * The height of the window.
	 */
	private static final int WIN_HEIGHT = 800;

	/**
	 * The length of the semiaxes.
	 */
	private static final int SAXIS_LENGTH = 4;

	/**
	 * The step of the semiaxes.
	 */
	private static final double SAXIS_STEP = 0.1;

	/**
	 * The PCA reference.
	 */
	private static PCA pca;

	/**
	 * The cluster set reference.
	 */
	private static ClusterSet clusterSet;

	/**
	 * Launch the application.
	 * @param pca The pca reference
	 * @param clusterSet The cluster set reference
	 */
	public static void launch(final PCA pca, final ClusterSet clusterSet) {
		PlotApp.pca = pca;
		PlotApp.clusterSet = clusterSet;

		PlotApp.launch(PlotApp.class);
	}

	/**
	 * Start the application.
	 * @param stage The JavaFX stage
	 */
	@Override
	public void start(Stage stage) {
		stage.setTitle("QTMiner scatter plot");

		NumberAxis xAxis = new NumberAxis(
			-SAXIS_LENGTH,
			+SAXIS_LENGTH,
			 SAXIS_STEP
		);

		NumberAxis yAxis = new NumberAxis(
			-SAXIS_LENGTH,
			+SAXIS_LENGTH,
			 SAXIS_STEP
		);

		ScatterChart<Number, Number> chart = new ScatterChart<Number, Number>(
			xAxis, yAxis
		);

		int clusterId = 0;

		for (Cluster cluster : clusterSet) {
			XYChart.Series series = new XYChart.Series();
			series.setName("Cluster #" + clusterId);

			for (Integer i : cluster) {
				Point3D p = pca.get(i);
				series.getData().add(new XYChart.Data(p.getX(), p.getY()));
			}

			chart.getData().add(series);

			clusterId++;
		}

		Scene scene = new Scene(chart, WIN_WIDTH, WIN_HEIGHT);

		stage.setScene(scene);
		stage.show();
	}
}
