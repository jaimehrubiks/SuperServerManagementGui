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
import models.MinionLogModel;

public class MinionLogController  implements Initializable {

	private MinionLogModel model;

	@FXML
	private TableView<MinionLogModel> tableMinionLog;
	@FXML
	private TableColumn<MinionLogModel, String> id;
	@FXML
	private TableColumn<MinionLogModel, String> minionId;
	@FXML
	private TableColumn<MinionLogModel, String> messageType;
	@FXML
	private TableColumn<MinionLogModel, String> date;
	@FXML
	private TableColumn<MinionLogModel, String> message;

	public MinionLogController() {
		model = new MinionLogModel();
	}
	
	public void launchMinionLogWindow() {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/MinionLogView.fxml"));

		Parent root;
		try {
			root = (Parent) fxmlLoader.load();
			MinionLogController controller = fxmlLoader.<MinionLogController>getController();
//			controller.setText(text);
//			controller.setMinionId(minionId);
//			System.out.println("setting type " + type);
//			controller.setType(type);
//			controller.configure();
			controller.queryMinionLogs();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Super Server Management: Minion Log Viewer (admin)");
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
		id.setCellValueFactory(new PropertyValueFactory<MinionLogModel, String>("id"));
		minionId.setCellValueFactory(new PropertyValueFactory<MinionLogModel, String>("minionId"));
		messageType.setCellValueFactory(new PropertyValueFactory<MinionLogModel, String>("messageType"));
		date.setCellValueFactory(new PropertyValueFactory<MinionLogModel, String>("Date"));
		message.setCellValueFactory(new PropertyValueFactory<MinionLogModel, String>("message"));
		
		customResize(tableMinionLog);

//		id.prefWidthProperty().bind(tableMinionLog.widthProperty().divide(5/4));
//		minionId.prefWidthProperty().bind(tableMinionLog.widthProperty().divide(5/4));
//		messageType.prefWidthProperty().bind(tableMinionLog.widthProperty().divide(4));
//		date.prefWidthProperty().bind(tableMinionLog.widthProperty().divide(5*4));
//		message.prefWidthProperty().bind(tableMinionLog.widthProperty().divide(5*4));

		tableMinionLog.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tableMinionLog.setVisible(true); // set invisible initially
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


	public void queryMinionLogs() {
		List<MinionLogModel> minionLogs = model.queryMinionLogs();
		tableMinionLog.getItems().setAll(minionLogs);
	}

}
