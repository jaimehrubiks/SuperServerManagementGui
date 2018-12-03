package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dao.DBConnect;

public class CustomerModel extends DBConnect implements User<Bank>{

	private String firstName;
	private String lastName;
	private int id;
	private int cid;
	private int tid;
	private double balance;
	 
	Bank custBank;
	
	public CustomerModel() {
		
		custBank = new Bank();
		custBank.setBankId(100);
		custBank.setBankName("Bank of IIT");
		custBank.setBankAddress("10 W 35th St, Chicago, IL 60616");

	}
 
	public CustomerModel(int tid, double balance) {
		 
		this.tid = tid;
		this.balance = balance;
 
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
 	public void setCid(int cid) {
		this.cid = cid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}
	
	public int getTid() {
		return tid;
	}
	 
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
  
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public List<CustomerModel> getAccounts(int cid) {
		List<CustomerModel> accounts  = new ArrayList<>();
		String query = "SELECT tid,balance FROM accounts WHERE cid = ?;";
		try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, cid);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
            	CustomerModel account = new CustomerModel();
                //grab record data by table field name into CustomerModel account object
            	account.setTid(resultSet.getInt("tid"));
            	account.setBalance(resultSet.getDouble("balance"));
            	accounts.add(account); //add account data to arraylist
            }
        } catch(SQLException e){
            System.out.println("Error fetching Accounts: " + e);
        }
		return accounts; //return arraylist
	}

	@Override
	public Bank getCustomerInfo() {
		// TODO Auto-generated method stub
		return custBank;
	}
}