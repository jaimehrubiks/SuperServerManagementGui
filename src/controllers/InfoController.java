package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

public class InfoController implements Initializable{

	@FXML
	private TextArea infoText;
	
	@FXML
	private Label minionIdLabel;

	@FXML
	private Label typeLabel;

	@FXML
	private ScrollPane scroller;

	private int minionId;
	private String type;
	private String text;
	
	public InfoController() {
		
	}

	public void configure() {
		this.infoText.appendText(this.text);
		this.infoText.setEditable(false);

		this.minionIdLabel.setText(String.valueOf(minionId));
		System.out.println("we have type: " + type);
		this.typeLabel.setText(type);
		
		this.scroller.setPrefWidth(600);
		this.scroller.maxWidth(600);
		this.scroller.setFitToWidth(true);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

	}

	/**
	 * @return the minionId
	 */
	public int getMinionId() {
		return minionId;
	}

	/**
	 * @param minionId the minionId to set
	 */
	public void setMinionId(int minionId) {
		this.minionId = minionId;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}



}