
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
	 * The learn clusters result.
	 */
	private LearnResult clustersResult;

	/**
	 * The learn centroids result.
	 */
	private LearnResult centroidsResult;

	/**
	 * The must update clustersResult flag.
	 */
	private boolean updateClusters;

	/**
	 * The must update centroidsResult flag.
	 */
	private boolean updateCentroids;

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
		updateClusters = updateCentroids = true;

		add(new Label("Data mining"), 0, 0);

		add(new Label("Table name:"), 0, 1);

		tableNameField = new TextField("table");
		tableNameField.textProperty().addListener(
			(o, old, val) -> updateClusters = updateCentroids = true
		);
		add(tableNameField, 1, 1);

		add(new Label("Radius:"), 0, 2);

		radiusField = new TextField("1.0");
		radiusField.textProperty().addListener(
			(o, old, val) -> updateClusters = updateCentroids = true
		);
		add(radiusField, 1, 2);

		learnDataButton = new Button("Learn from database");
		learnDataButton.setOnAction(event -> {
			if (!updateClusters) {
				updateClustersUI();
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
				clustersResult = client.learnFromData(tableName, radius);
			} catch (IOException
				| ClassNotFoundException
				| ServerException e) {
				ClientUI.showError(
					"Learning from data failed", e.getMessage()
				);
				return;
			}

			updateClustersUI();
			updateClusters = false;
		});
		add(learnDataButton, 0, 3);

		learnFileButton = new Button("Learn from file");
		learnFileButton.setOnAction(event -> {
			if (!updateCentroids) {
				updateCentroidsUI();
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
				centroidsResult = client.learnFromFile(tableName, radius);
			} catch (IOException
				| ClassNotFoundException
				| ServerException e) {
				ClientUI.showError(
					"Learning from data failed", e.getMessage()
				);
				return;
			}

			updateCentroidsUI();
			updateClusters = false;
		});
		add(learnFileButton, 1, 3);
	}

	/**
	 * Update the result UI and the plot UI placing clusters data.
	 */
	private void updateClustersUI() {
		resultUI.setContent(clustersResult.getData());
		plotUI.setData(clustersResult.getPlotData());
	}

	/**
	 * Update the result UI and the plot UI placing centroids data.
	 */
	private void updateCentroidsUI() {
		resultUI.setContent(centroidsResult.getData());
		plotUI.setData(centroidsResult.getPlotData());
	}
}
