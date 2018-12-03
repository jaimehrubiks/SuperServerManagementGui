package models;

import Dao.DBConnect;
import network.TCPUserClient;

public class LoginModel extends DBConnect {
 
	private Boolean admin;
	private TCPUserClient socket;
	private int userId;
	
	public LoginModel() {
	}
 
	public Boolean isAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	
	public Boolean authenticate(String username, String password) {
		try {
			/*
			TCPUserClient socket = new TCPUserClient(Settings.hostname, Settings.port);
			UserLogin login = new UserLogin(username, password);
			socket.sendMessage(login);
			Messsage m = socket.receiveMessage();
			if(m.msgType == MessageType.USER_LOGIN){
				login = (UserLogin) m;
				if(m.isCorrect(){
					setAdmin(m.isAdmin());
					setUserId(m.getUserId());
					return true;
				}
			}
			*/
			setAdmin(false);
			return true;
		} catch (Exception e) {
			return false;
		}
		// return false;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

}//end class