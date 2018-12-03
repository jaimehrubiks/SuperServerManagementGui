package controllers;

import application.DynamicTable;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import models.CustomerModel;

public class AdminController {

	@FXML
	private Pane pane1;
	@FXML
	private Pane pane2;
	@FXML
	private Pane pane3;
	
	public AdminController() {
		
		 
	}
	
	public void viewAccounts() {
		
	DynamicTable d = new DynamicTable();
	//call method from DynamicTable class and pass some arbitrary query string
	d.buildData("Select tid,balance from accounts");
		
	}
	
	public void updateRec() {
		
		pane3.setVisible(false);
		pane2.setVisible(false);
		pane1.setVisible(true);
		
	}
	public void deleteRec() {
		
	    pane1.setVisible(false);
	    pane2.setVisible(true);
		pane3.setVisible(false);
	}	
	
	public void addBankRec() {
		
	    pane1.setVisible(false);
	    pane2.setVisible(false);
		pane3.setVisible(true);
	}	
	
	public void submitUpdate() {
		
		System.out.println("Update Submit button pressed");
	  
	}	
	
	public void submitDelete() {
	
		System.out.println("Delete Submit button pressed");
	  
	}	
	
}
