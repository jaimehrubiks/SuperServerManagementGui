package models;

import Dao.DBConnect;
import configuration.Settings;
import messages.Message;
import messages.MessageType;
import messages.UserLogin;
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
			TCPUserClient socket = new TCPUserClient(Settings.hostname, Settings.port);
			UserLogin login = new UserLogin(username, password);
			socket.sendMessage(login);
			Message m = socket.receiveMessage();
			if(m.getMsgType() == MessageType.USER_LOGIN){
				login = (UserLogin) m;
				if(login.isOk()){
					setAdmin(login.isAdmin());
					setUserId(login.getUserId());
					return true;
				}
			}
		} catch (Exception e) { }
		 return false;
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