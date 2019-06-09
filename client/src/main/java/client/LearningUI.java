
import java.util.List;

import java.io.IOException;

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * The status of the learning UI.
 */
enum LearningStatus {
	/**
	 * Settings are changed.
	 */
	SETTINGS_CHANGED,

	/**
	 * Just learned from data.
	 */
	LEARN_FROM_DATA,

	/**
	 * just learned from file.
	 */
	LEARN_FROM_FILE
}

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
	 * The learning UI status.
	 */
	private LearningStatus status;

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
		status = LearningStatus.SETTINGS_CHANGED;

		add(new Label("Data mining"), 0, 0);

		add(new Label("Table name:"), 0, 1);

		tableNameField = new TextField("table");
		tableNameField.textProperty().addListener(
			(o, old, val) -> status = LearningStatus.SETTINGS_CHANGED
		);
		add(tableNameField, 1, 1);

		add(new Label("Radius:"), 0, 2);

		radiusField = new TextField("2.0");
		radiusField.textProperty().addListener(
			(o, old, val) -> status = LearningStatus.SETTINGS_CHANGED
		);
		add(radiusField, 1, 2);

		learnDataButton = new Button("Learn from database");
		learnDataButton.setOnAction(event -> {
			if (!client.isConnected()) {
				ClientUI.showError(
					"Your are not connected to the server", ""
				);
				return;
			}

			if (status == LearningStatus.LEARN_FROM_DATA) {
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

			String clusterSet = "";

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

			resultUI.setContent(clusterSet);

			List<PlotData> plotData = null;

			try {
				plotData = client.receivePlotData();
			} catch (IOException
				| ClassNotFoundException
				| ServerException e) {
				ClientUI.showError(
					"Receive data to plot failed", e.getMessage()
				);
				return;
			}

			plotUI.setData(plotData);
			plotUI.setOn();

			status = LearningStatus.LEARN_FROM_DATA;
		});
		add(learnDataButton, 0, 3);

		learnFileButton = new Button("Learn from file");
		learnFileButton.setOnAction(event -> {
			if (!client.isConnected()) {
				ClientUI.showError(
					"Your are not connected to the server", ""
				);
				return;
			}

			if (status == LearningStatus.LEARN_FROM_FILE) {
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

			String centroids = "";

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

			resultUI.setContent(centroids);

			plotUI.setOff();

			status = LearningStatus.LEARN_FROM_FILE;
		});
		add(learnFileButton, 1, 3);
	}
}
