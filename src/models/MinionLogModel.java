package models;

import java.util.ArrayList;
import java.util.List;

import configuration.Settings;
import messages.AdminQueryMinionLogs;
import messages.Message;
import messages.MessageType;
import network.TCPUserClient;

public class MinionLogModel {

	private int id;
	private int minionId;
	private String messageType;
	private String date;
	private String message;

	public MinionLogModel() {

	}

	public List<MinionLogModel> queryMinionLogs() {
		List<MinionLogModel> minionLogs = new ArrayList<>();
		TCPUserClient socket = null;
		try {
			socket = new TCPUserClient(Settings.hostname, Settings.port);
			AdminQueryMinionLogs query = new AdminQueryMinionLogs();
			System.out.println("Testing command: AdminQueryMinionLogs()");
			socket.sendMessage(query);
			System.out.println("Message send. Waiting for response.");
			Message ans = socket.receiveMessage();
			if (ans.getMsgType() == MessageType.ADMIN_QUERY_MINION_LOGS) {
				query = (AdminQueryMinionLogs) ans;
				System.out.println(query.toString());
				System.out.println(query.getArray().get(0));
				for (AdminQueryMinionLogs minionlogmsg : query.getArray()) {
					MinionLogModel minionlog = new MinionLogModel();
					minionlog.setId(minionlogmsg.getLogId());
					minionlog.setMinionId(minionlogmsg.getMinionId());
					minionlog.setMessageType(minionlogmsg.getMessageType());
					minionlog.setDate(minionlogmsg.getTimestamp());
					minionlog.setMessage(minionlogmsg.getMessage());
					minionLogs.add(minionlog);
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

		return minionLogs;

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
	 * @return the minionId
	 */
	public int getMinionId() {
		return minionId;
	}

	/**
	 * @param minionId the minionId to set
	 */
	public void setMinionId(int minionId) {
		this.minionId = minionId;
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