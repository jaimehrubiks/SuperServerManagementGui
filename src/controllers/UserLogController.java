package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.UserLogModel;

public class UserLogController  implements Initializable {

	private UserLogModel model;

	@FXML
	private TableView<UserLogModel> tableUserLog;
	@FXML
	private TableColumn<UserLogModel, String> id;
	@FXML
	private TableColumn<UserLogModel, String> userId;
	@FXML
	private TableColumn<UserLogModel, String> messageType;
	@FXML
	private TableColumn<UserLogModel, String> date;
	@FXML
	private TableColumn<UserLogModel, String> message;

	public UserLogController() {
		model = new UserLogModel();
	}
	
	public void launchUserLogWindow() {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/UserLogView.fxml"));

		Parent root;
		try {
			root = (Parent) fxmlLoader.load();
			UserLogController controller = fxmlLoader.<UserLogController>getController();
			controller.queryUserLogs();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Super Server Management: User Log Viewer (admin)");
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.setupTable();

	}

	private void setupTable() {
		id.setCellValueFactory(new PropertyValueFactory<UserLogModel, String>("id"));
		userId.setCellValueFactory(new PropertyValueFactory<UserLogModel, String>("userId"));
		messageType.setCellValueFactory(new PropertyValueFactory<UserLogModel, String>("messageType"));
		date.setCellValueFactory(new PropertyValueFactory<UserLogModel, String>("Date"));
		message.setCellValueFactory(new PropertyValueFactory<UserLogModel, String>("message"));
		
		customResize(tableUserLog);

//		id.prefWidthProperty().bind(tableUserLog.widthProperty().divide(5/4));
//		userId.prefWidthProperty().bind(tableUserLog.widthProperty().divide(5/4));
//		messageType.prefWidthProperty().bind(tableUserLog.widthProperty().divide(4));
//		date.prefWidthProperty().bind(tableUserLog.widthProperty().divide(5*4));
//		message.prefWidthProperty().bind(tableUserLog.widthProperty().divide(5*4));

		tableUserLog.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tableUserLog.setVisible(true); // set invisible initially
	}

	public void customResize(TableView<?> view) {

		AtomicLong width = new AtomicLong();
		view.getColumns().forEach(col -> {
			width.addAndGet((long) col.getWidth());
		});
		double tableWidth = view.getWidth();

		if (tableWidth > width.get()) {
			view.getColumns().forEach(col -> {
				col.setPrefWidth(col.getWidth() + ((tableWidth - width.get()) / view.getColumns().size()));
			});
		}
	}


	public void queryUserLogs() {
		List<UserLogModel> userLogs = model.queryUserLogs();
		tableUserLog.getItems().setAll(userLogs);
	}

}
