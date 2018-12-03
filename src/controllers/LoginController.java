package controllers;

import application.Main;
import models.LoginModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginController {

	@FXML
	private TextField txtUsername;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private Label lblError;

	private LoginModel model;

	public LoginController() {
		model = new LoginModel();

	}

	public void login() {
		String username = this.txtUsername.getText();
		String password = this.txtPassword.getText();

		// Validations
		if (username == null || username.trim().equals("")) {
			lblError.setText("Username Cannot be empty or spaces");
			return;

		}
		if (password == null || password.trim().equals("")) {
			lblError.setText("Password Cannot be empty or spaces");
			return;
		}
		if (username == null || username.trim().equals("") && (password == null || password.trim().equals(""))) {
			lblError.setText("User name / Password Cannot be empty or spaces");
			return;
		}

		// authentication check
		checkCredentials(username, password);
	}

	public void checkCredentials(String username, String password) {
		Boolean isValid = model.authenticate(username, password);
		if (!isValid) {
			lblError.setText("Wrong username or password!!.");
			return;
		}
		try {
			AnchorPane root;
			// If user is customer, inflate customer view
			CustomerController.setUser(model.getUserId());
			CustomerController.setAdmin(model.isAdmin());
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/CustomerView.fxml"));
			Main.stage.setTitle("Super Server Management: Minion View");

			Scene scene = new Scene(root);
			Main.stage.setScene(scene);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error occured while inflating view: " + e);
		}

	}
}