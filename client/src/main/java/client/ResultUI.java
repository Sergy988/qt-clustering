
package client;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

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

