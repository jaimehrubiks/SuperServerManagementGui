package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import Dao.DBConnect;
import application.Main;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import models.CustomerModel;
import models.InfoSpawn;

public class CustomerController  implements Initializable {

	static int user_id;
	static boolean admin;
	private CustomerModel cm;
	private Map<Integer, CustomerModel> minions;

	@FXML
	private Label userLabel;
	@FXML
	private Label adminLabel;
	@FXML
	private TitledPane adminPane;

	@FXML
	private TableView<CustomerModel> tableMinions;
	@FXML
	private TableColumn<CustomerModel, String> minionId;
	@FXML
	private TableColumn<CustomerModel, String> hostName;
	@FXML
	private TableColumn<CustomerModel, String> tag;
	@FXML
	private TableColumn<CustomerModel, String> publicIP;
	@FXML
	private TableColumn<CustomerModel, String> IP;
	@FXML
	private TableColumn<CustomerModel, String> CPU;
	@FXML
	private TableColumn<CustomerModel, String> RAM;
	@FXML
	private TableColumn<CustomerModel, String> online;
//	@FXML
//	private TableColumn<CustomerModel, String> select;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if (!CustomerController.admin) {
			adminPane.setDisable(true);
		}
		userLabel.setText(String.valueOf(CustomerController.user_id));
		adminLabel.setText(String.valueOf(CustomerController.admin));

		this.setupTable();

	}

	private void setupTable() {
		minionId.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("minionId"));
		hostName.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("hostName"));
		tag.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("tag"));
		publicIP.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("publicIP"));
		IP.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("IP"));
		CPU.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("CPU"));
		RAM.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("RAM"));
		online.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("online"));

		minionId.prefWidthProperty().bind(tableMinions.widthProperty().divide(8));
		hostName.prefWidthProperty().bind(tableMinions.widthProperty().divide(8));
		tag.prefWidthProperty().bind(tableMinions.widthProperty().divide(8));
		publicIP.prefWidthProperty().bind(tableMinions.widthProperty().divide(8));
		IP.prefWidthProperty().bind(tableMinions.widthProperty().divide(8));
		CPU.prefWidthProperty().bind(tableMinions.widthProperty().divide(8));
		RAM.prefWidthProperty().bind(tableMinions.widthProperty().divide(8));
		online.prefWidthProperty().bind(tableMinions.widthProperty().divide(8));

		tableMinions.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tableMinions.setVisible(true); // set invisible initially
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

	public void logOut() {
		System.out.println("Trying to log out");
		AnchorPane root;
		try {
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/LoginView.fxml"));
			Main.stage.setTitle("Super Server Management: Login");
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/styles.css").toExternalForm());
			Main.stage.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public CustomerController() {
		cm = new CustomerModel();

		/*
		 * Alert alert = new Alert(AlertType.INFORMATION);
		 * alert.setTitle("From Customer controller");
		 * alert.setHeaderText("Bank Of IIT- Chicago Main Branch");
		 * alert.setContentText("Welcome back!"); alert.showAndWait();
		 */

	}

	public static void setUser(int user_id) {
		CustomerController.user_id = user_id;
	}

	public void viewAccounts() throws IOException {

//		CustomerModel cm = new CustomerModel();
//    	tableMinions.getItems().setAll(cm.getAccounts(CustomerController.user_id )); //load table data from CustomerModel list
//		tableMinions.setVisible(true); //set tableview to visible
//		System.out.println(cm.getCustomerInfo());

	}

	public void queryMinions() {
		minions = cm.queryMinionList();
		tableMinions.getItems().setAll(minions.values());
	}

	public void queryMinionBasicInfo() {
		ObservableList<CustomerModel> selected = tableMinions.getSelectionModel().getSelectedItems();
		System.out.println(selected.size());

		for (int i = 0; i < selected.size(); i++) {
			System.out.println("looping");
			CustomerModel row = selected.get(i);
			int minionId = row.getMinionId();
			new Thread(() -> queryMinionBasicInfoById(minionId)).start();
//			queryMinionBasicInfoById(minionId);
		}

		tableMinions.getItems().setAll(minions.values());
	}

	public void queryMinionBasicInfoById(int minionId) {
		if (minions.containsKey(minionId)) {
			CustomerModel minion = cm.queryMinionBasicInfo(minionId);
			if (minion != null) {
				minions.put(minionId, minion);
				tableMinions.getItems().setAll(minions.values());
			}
		}
	}

	public void queryMinionsAndBasicInfo() {
		minions = cm.queryMinionList();
		minions.values().forEach((minionn) -> {
			int minionId = minionn.getMinionId();
			// if (minions.containsKey(minionId)) {
			new Thread(() -> queryMinionBasicInfoById(minionId)).start();
//				CustomerModel minion = cm.queryMinionBasicInfo(minionId);
//				if (minion != null) {
//					minions.put(minionId, minion);
//				}
//			}
		});
//		tableMinions.getItems().setAll(minions.values());
	}
	
	public void queryProcessList() {
		System.out.println("Querying process List");
		ObservableList<CustomerModel> selected = tableMinions.getSelectionModel().getSelectedItems();
		selected.forEach( minion -> {
			if(minion.isOnline()) {
				queryProcessListById(minion.getMinionId());
			}
		});
	}
	
	public void queryProcessListById(int minionId) {
		String result = cm.getProcessList(minionId);
		InfoSpawn is = new InfoSpawn();
		System.out.println(minionId);
		System.out.println(result);
		is.spawnInfoView(minionId, "Process List", result);
	}
	
	public void logout() {
		System.exit(0);
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/LoginView.fxml"));
			Scene scene = new Scene(root);
			Main.stage.setScene(scene);
			Main.stage.setTitle("Login");
		} catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}
	}

	/**
	 * @return the admin
	 */
	public static boolean isAdmin() {
		return admin;
	}

	/**
	 * @param admin the admin to set
	 */
	public static void setAdmin(boolean admin) {
		CustomerController.admin = admin;
	}
}
