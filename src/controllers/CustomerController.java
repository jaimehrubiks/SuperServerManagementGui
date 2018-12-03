package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
	
    @FXML private TableView<CustomerModel> tblAccounts;
    @FXML private TableColumn<CustomerModel, String> tid;
    @FXML private TableColumn<CustomerModel, String> balance;
 
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tid.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("tid"));
        balance.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("balance")); 
        
        //auto adjust width of columns depending on their content
        tblAccounts.setColumnResizePolicy((param) -> true );
        Platform.runLater(() -> customResize(tblAccounts));
        
    	tblAccounts.setVisible(true); //set invisible initially
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
		
		CustomerModel cm = new CustomerModel();
    	tblAccounts.getItems().setAll(cm.getAccounts(CustomerController.user_id )); //load table data from CustomerModel list
		tblAccounts.setVisible(true); //set tableview to visible
		System.out.println(cm.getCustomerInfo());
		 
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
