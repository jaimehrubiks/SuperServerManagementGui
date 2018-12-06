package models;

import configuration.Settings;
import messages.Message;
import messages.MessageType;
import messages.UserLogin;
import network.TCPUserClient;

public class LoginModel {
 
	private Boolean admin;
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
		System.out.println("Trying to authenticate.");
		try {
			TCPUserClient socket = new TCPUserClient(Settings.hostname, Settings.port);
			UserLogin login = new UserLogin(username, password);
			socket.sendMessage(login);
			Message m = socket.receiveMessage();
			if(m.getMsgType() == MessageType.USER_LOGIN){
				login = (UserLogin) m;
				System.out.println(login);
				if(login.isOk()){
					setAdmin(login.isAdmin());
					setUserId(login.getUserId());
					return true;
				}
			}
		} catch (Exception e) { System.out.println(e); }
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