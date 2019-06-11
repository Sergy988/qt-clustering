
import java.util.List;

import java.io.IOException;

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * The client learning UI.
 */
class LearningUI extends ClientUI {

	/**
	 * The table name text field.
	 */
	private TextField tableNameField;

	/**
	 * The radius text field.
	 */
	private TextField radiusField;

	/**
	 * The learn from data button.
	 */
	private Button learnDataButton;

	/**
	 * The learning from file button.
	 */
	private Button learnFileButton;

	/**
	 * The result UI.
	 */
	private ResultUI resultUI;

	/**
	 * The scatter plot UI.
	 */
	private PlotUI plotUI;

	/**
	 * The flag that rappresents if the settings are changed.
	 */
	private boolean settingsChanged;

	/**
	 * The cluster set string.
	 */
	private String clusterSet;

	/**
	 * The centroids string.
	 */
	private String centroids;

	/**
	 * The data to plot of clusters.
	 */
	private List<PlotData> clustersPlotData;

	/**
	 * The data to plot of centroids.
	 */
	private List<PlotData> centroidsPlotData;

	/**
	 * Construct a client connection UI.
	 * @param client A reference to the client
	 * @param resultUI The result UI where to write the learning result.
	 * @param plotUI the result plot UI.
	 */
	LearningUI(ClientMain client, ResultUI resultUI, PlotUI plotUI) {
		super(client);
		this.resultUI = resultUI;
		this.plotUI = plotUI;
		settingsChanged = true;

		add(new Label("Data mining"), 0, 0);

		add(new Label("Table name:"), 0, 1);

		tableNameField = new TextField("table");
		tableNameField.textProperty().addListener(
			(o, old, val) -> settingsChanged = true
		);
		add(tableNameField, 1, 1);

		add(new Label("Radius:"), 0, 2);

		radiusField = new TextField("2.0");
		radiusField.textProperty().addListener(
			(o, old, val) -> settingsChanged = true
		);
		add(radiusField, 1, 2);

		learnDataButton = new Button("Learn from database");
		learnDataButton.setOnAction(event -> {
			if (!settingsChanged && clusterSet != null) {
				updateClusters();
				return;
			}

			if (!client.isConnected()) {
				ClientUI.showError(
					"Your are not connected to the server", ""
				);
				return;
			}

			double radius = 0.0;

			try {
				radius = Double.parseDouble(radiusField.getText());
			} catch (NumberFormatException e) {
				ClientUI.showError(
					"Invalid radius format", e.getMessage()
				);
				return;
			}

			String tableName = tableNameField.getText();

			try {
				clusterSet = client.learnFromData(tableName, radius);
			} catch (IOException
				| ClassNotFoundException
				| ServerException e) {
				ClientUI.showError(
					"Learning from data failed", e.getMessage()
				);
				return;
			}

			try {
				clustersPlotData = client.receiveProjectedClusters();
			} catch (IOException
				| ClassNotFoundException
				| ServerException e) {
				ClientUI.showError(
					"Receive data to plot failed", e.getMessage()
				);
				return;
			}

			updateClusters();
			settingsChanged = false;
		});
		add(learnDataButton, 0, 3);

		learnFileButton = new Button("Learn from file");
		learnFileButton.setOnAction(event -> {
			if (!settingsChanged && centroids != null) {
				updateCentroids();
				return;
			}

			if (!client.isConnected()) {
				ClientUI.showError(
					"Your are not connected to the server", ""
				);
				return;
			}

			double radius = 0.0;

			try {
				radius = Double.parseDouble(radiusField.getText());
			} catch (NumberFormatException e) {
				ClientUI.showError(
					"Invalid radius format", e.getMessage()
				);
				return;
			}

			String tableName = tableNameField.getText();

			try {
				centroids = client.learnFromFile(tableName, radius);
			} catch (IOException
				| ClassNotFoundException
				| ServerException e) {
				ClientUI.showError(
					"Learning from data failed", e.getMessage()
				);
				return;
			}

			try {
				centroidsPlotData = client.receiveProjectedCentroids();
			} catch (IOException
				| ClassNotFoundException
				| ServerException e) {
				ClientUI.showError(
					"Receive data to plot failed", e.getMessage()
				);
				return;
			}

			updateCentroids();
			settingsChanged = false;
		});
		add(learnFileButton, 1, 3);
	}

	/**
	 * Update the result UI and the plot UI placing clusters data.
	 */
	private void updateClusters() {
		resultUI.setContent(clusterSet);
		plotUI.setData(clustersPlotData, "Cluster #");
	}

	/**
	 * Update the result UI and the plot UI placing centroids data.
	 */
	private void updateCentroids() {
		resultUI.setContent(centroids);
		plotUI.setData(centroidsPlotData, "Centroid #");
	}
}
