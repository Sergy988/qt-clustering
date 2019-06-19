
package client;

import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.layout.GridPane;

/**
 * The abstract client UI class.
 */
abstract class ClientUI extends GridPane {

	/**
	 * A reference to the client.
	 */
	protected ClientMain client;

	/**
	 * Construct a client UI.
	 * @param client A reference to the client
	 */
	ClientUI(ClientMain client) {
		this.client = client;
		setHgap(8);
		setVgap(8);
	}

	/**
	 * Show an error popup.
	 * @param headerText The text of the header
	 * @param contentText The text of the content
	 */
	static void showError(String headerText, String contentText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.setResizable(true);
		alert.showAndWait();
	}

	/**
	 * Show a information popup.
	 * @param text The text to show
	 */
	static void showInformation(String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(text);
		alert.setResizable(true);
		alert.showAndWait();
	}
}
