
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
	 * Construct a client connection UI.
	 * @param client A reference to the client
	 * @param resultUI The result UI where to write the learning result.
	 */
	LearningUI(ClientMain client, ResultUI resultUI) {
		super(client);
		this.resultUI = resultUI;

		add(new Label("Data mining"), 0, 0);

		add(new Label("Table name:"), 0, 1);

		tableNameField = new TextField("table");
		add(tableNameField, 1, 1);

		add(new Label("Radius:"), 0, 2);

		radiusField = new TextField("1.0");
		add(radiusField, 1, 2);

		learnDataButton = new Button("Learn from database");
		learnDataButton.setOnAction(event -> {
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
		});
		add(learnFileButton, 1, 3);
	}

}
