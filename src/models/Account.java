package models;

public class Account {

	private CustomerModel customer;
	private int id;
	private int cid;
	private int tid;
	private double balance;
	
	//private ArrayList<Transaction> transactions;
	
	public Account() {
		 
	}
    public Account(int cid, double balance) {
 
		this.cid = cid;
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
  
    /*
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
	}
	*/
}