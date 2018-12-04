package models;

import java.io.IOException;

import controllers.InfoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InfoSpawn {

	public void spawnInfoView(int minionId, String type, String text) {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/InfoView.fxml"));

		Parent root;
		try {
			root = (Parent) fxmlLoader.load();
			InfoController controller = fxmlLoader.<InfoController>getController();
			controller.setText(text);
			controller.setMinionId(minionId);
			System.out.println("setting type " + type);
			controller.setType(type);
			controller.configure();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Super Server Management: Info Window");
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InfoSpawn is = new InfoSpawn();
		is.spawnInfoView(1, "a", "abc");

	}

}
