
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
 * The client result UI.
 */
class ResultUI extends ClientUI {

	/**
	 * The result text area.
	 */
	private TextArea area;

	/**
	 * Construct a client result ui.
	 * @param client A reference to the client
	 */
	ResultUI(ClientMain client) {
		super(client);

		add(new Label("Result:"), 0, 0);

		area = new TextArea();
		area.setPrefColumnCount(48);
		area.setPrefRowCount(32);

		add(area, 0, 1);
	}

	/**
	 * Set the content of the text area.
	 * @param content The content to place in the text area
	 */
	void setContent(String content) {
		area.setText(content);
	}
}

