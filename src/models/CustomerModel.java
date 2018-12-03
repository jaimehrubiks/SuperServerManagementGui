package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dao.DBConnect;

public class CustomerModel extends DBConnect implements User<Bank>{

	private int minionId;
	private String CPU;
	private String IP;
	private String RAM;
	private String publicIP;
	private String hostName;
	private String tag;
	private boolean online;	
	private boolean select;
	
	Bank custBank;
	
	public CustomerModel() {
		
		custBank = new Bank();
		custBank.setBankId(100);
		custBank.setBankName("Bank of IIT");
		custBank.setBankAddress("10 W 35th St, Chicago, IL 60616");

	}
 
	public CustomerModel(int tid, double balance) {
		 
//		this.tid = tid;
//		this.balance = balance;
 
	}

	public List<CustomerModel> getAccounts(int cid) {
		List<CustomerModel> accounts  = new ArrayList<>();
//		String query = "SELECT tid,balance FROM accounts WHERE cid = ?;";
//		try(PreparedStatement statement = connection.prepareStatement(query)){
//            statement.setInt(1, cid);
//            ResultSet resultSet = statement.executeQuery();
//            while(resultSet.next()) {
//            	CustomerModel account = new CustomerModel();
//                //grab record data by table field name into CustomerModel account object
//            	account.setTid(resultSet.getInt("tid"));
//            	account.setBalance(resultSet.getDouble("balance"));
//            	accounts.add(account); //add account data to arraylist
//            }
//        } catch(SQLException e){
//            System.out.println("Error fetching Accounts: " + e);
//        }
		return accounts; //return arraylist
	}

	@Override
	public Bank getCustomerInfo() {
		// TODO Auto-generated method stub
		return custBank;
	}
}