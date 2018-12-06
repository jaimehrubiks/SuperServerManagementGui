package models;

import java.util.ArrayList;
import java.util.List;

import configuration.Settings;
import messages.AdminQueryUserLogs;
import messages.Message;
import messages.MessageType;
import network.TCPUserClient;

public class UserLogModel {

	private int id;
	private int userId;
	private String messageType;
	private String date;
	private String message;

	public UserLogModel() {

	}

	public List<UserLogModel> queryUserLogs() {
		List<UserLogModel> userLogs = new ArrayList<>();
		TCPUserClient socket = null;
		try {
			socket = new TCPUserClient(Settings.hostname, Settings.port);
			AdminQueryUserLogs query = new AdminQueryUserLogs();
			System.out.println("Testing command: AdminQueryUserLogs()");
			socket.sendMessage(query);
			System.out.println("Message send. Waiting for response.");
			Message ans = socket.receiveMessage();
			if (ans.getMsgType() == MessageType.ADMIN_QUERY_USER_LOGS) {
				query = (AdminQueryUserLogs) ans;
				System.out.println(query.toString());
				System.out.println(query.getArray().get(0));
				for (AdminQueryUserLogs userlogmsg : query.getArray()) {
					UserLogModel userlog = new UserLogModel();
					userlog.setId(userlogmsg.getLogId());
					userlog.setUserId(userlogmsg.getUserId());
					userlog.setMessageType(userlogmsg.getMessageType());
					userlog.setDate(userlogmsg.getTimestamp());
					userlog.setMessage(userlogmsg.getMessage());
					userLogs.add(userlog);
				}
			} else {
				System.out.println("Error receiving message.");
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (socket != null)
				socket.closeConnection();
		}

		return userLogs;

	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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

	/**
	 * @return the messageType
	 */
	public String getMessageType() {
		return messageType;
	}

	/**
	 * @param messageType the messageType to set
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
};