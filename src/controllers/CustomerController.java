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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import models.CustomerModel;
 
public class CustomerController extends DBConnect implements Initializable {

	static int user_id;
	private CustomerModel cm;
	private Map<Integer, CustomerModel> minions;
	
    @FXML private TableView<CustomerModel> tableMinions;
    @FXML private TableColumn<CustomerModel, String> minionId;
    @FXML private TableColumn<CustomerModel, String> hostName;
    @FXML private TableColumn<CustomerModel, String> tag;
    @FXML private TableColumn<CustomerModel, String> publicIP;
    @FXML private TableColumn<CustomerModel, String> IP;
    @FXML private TableColumn<CustomerModel, String> CPU;
    @FXML private TableColumn<CustomerModel, String> RAM;
    @FXML private TableColumn<CustomerModel, String> online;
    @FXML private TableColumn<CustomerModel, String> select;
 
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        minionId.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("minionId"));
        hostName.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("hostName")); 
        tag.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("tag")); 
        publicIP.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("publicIP")); 
        IP.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("IP")); 
        CPU.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("CPU")); 
        RAM.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("RAM")); 
        online.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("online")); 
        select.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("select")); 
        
        //auto adjust width of columns depending on their content
        tableMinions.setColumnResizePolicy((param) -> true );
        Platform.runLater(() -> customResize(tableMinions));
        
    	tableMinions.setVisible(true); //set invisible initially
    }
    
    public void customResize(TableView<?> view) {

        AtomicLong width = new AtomicLong();
        view.getColumns().forEach(col -> {
            width.addAndGet((long) col.getWidth());
        });
        double tableWidth = view.getWidth();

        if (tableWidth > width.get()) {
            view.getColumns().forEach(col -> {
                col.setPrefWidth(col.getWidth()+((tableWidth-width.get())/view.getColumns().size()));
            });
        }
    }
 
	public CustomerController() {
		cm = new CustomerModel();
     
		/*Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("From Customer controller");
		alert.setHeaderText("Bank Of IIT- Chicago Main Branch");
		alert.setContentText("Welcome back!");
		alert.showAndWait(); */
	 
	} 
	 
	public static void setUser(int user_id){
	    CustomerController.user_id = user_id;
	}
	
	public void viewAccounts( ) throws IOException {
		
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
		int minionId = tableMinions.getSelectionModel().getSelectedItem().getMinionId();
		if (minions.containsKey(minionId)) {
			CustomerModel minion = cm.queryMinionBasicInfo(minionId);
			if(minion!=null) {
				minions.put(minionId, minion);
				tableMinions.getItems().setAll(minions.values());
			}
		}
	}
	
	public void logout() {
		System.exit(0);
		try {
		    AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/LoginView.fxml"));
			Scene scene = new Scene(root);
			Main.stage.setScene(scene);
			Main.stage.setTitle("Login");
		} catch(Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}
	}
}
