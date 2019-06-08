
import java.io.IOException;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.layout.GridPane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * The client connection UI.
 */
class ConnectionUI extends ClientUI {

	/**
	 * The server ip text field.
	 */
	private TextField ipField;

	/**
	 * The port number text field.
	 */
	private TextField portField;

	/**
	 * The connect button.
	 */
	private Button connectButton;

	/**
	 * The disconnect button.
	 */
	private Button disconnectButton;

	/**
	 * Construct a client connection ui.
	 * @param client A reference to the client
	 */
	ConnectionUI(ClientMain client) {
		super(client);

		add(new Label("Server Connection:"), 0, 0);

		add(new Label("Server ip:"), 0, 1);

		ipField = new TextField("localhost");
		setPrefWidth(144);
		add(ipField, 1, 1);

		add(new Label("Server port:"), 0, 2);

		portField = new TextField("8080");
		add(portField, 1, 2);

		connectButton = new Button("Connect");
		connectButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (client.isConnected()) {
					ClientUI.showInformation(
						"You are already connected to the server"
					);
					return;
				}

				int port = 0;
				String ip = ipField.getText();

				try {
					port = Integer.parseInt(portField.getText());

					if (port < 0 || port > 65545) {
						throw new NumberFormatException("Invalid port");
					}
				} catch (NumberFormatException e) {
					ClientUI.showError(
						"Invalid port format", e.getMessage()
					);
					return;
				}

				try {
					client.connect(ip, port);
				} catch (IOException e) {
					ClientUI.showError(
						"Connection to the server failed", e.getMessage()
					);
					return;
				}

				ClientUI.showInformation(
					"Connection to the server enstablished"
				);
			}
		});
		add(connectButton, 0, 3);

		disconnectButton = new Button("Disconnect");
		disconnectButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!client.isConnected()) {
					ClientUI.showInformation(
						"You are already disconnected"
					);
					return;
				}

				try {
					client.disconnect();
				} catch (IOException e) {
					ClientUI.showError(
						"Failed to disconnect from the server", e.getMessage()
					);
					return;
				}

				ClientUI.showInformation(
					"Successfully disconnected from the server"
				);
			}
		});
		add(disconnectButton, 1, 3);
	}
}
