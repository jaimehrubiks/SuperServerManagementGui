package models;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import configuration.Settings;
import messages.CmdQuery;
import messages.CmdType;
import messages.Message;
import messages.MessageType;
import messages.UserQueryMinionBasicInfo;
import messages.UserQueryMinionList;
import network.TCPUserClient;

public class CustomerModel  {

	private int minionId;
	private String CPU;
	private String IP;
	private String RAM;
	private String publicIP;
	private String hostName;
	private String tag;
	private boolean online;
	private boolean select;

	public CustomerModel() {

	}

	public CustomerModel queryMinionBasicInfo(int minionId) {
		CustomerModel minion = null;

		TCPUserClient socket = null;
		try {
			socket = new TCPUserClient(Settings.hostname, Settings.port);

			UserQueryMinionBasicInfo query = new UserQueryMinionBasicInfo(minionId);
			System.out.println("Testing command: UserQueryMinionBasicInfo");
			socket.sendMessage(query);
			System.out.println("Message send. Waiting for response.");

			Message ans = socket.receiveMessage();
			if (ans.getMsgType() == MessageType.USER_QUERY_BASICINFO) {
				query = (UserQueryMinionBasicInfo) ans;
				System.out.println(query.toString());
				minion = new CustomerModel();
				minion.setMinionId(query.getMinionId());
				minion.setCPU(query.getCPU());
				minion.setHostName(query.getHostname());
				minion.setIP(query.getIP());
				minion.setPublicIP(query.getPublicIP());
				minion.setRAM(query.getRAM());
				minion.setOnline(query.isOnline());
				// minion.setSelect(false);
			} else {
				System.out.println("Error receiving message.");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (socket != null)
				socket.closeConnection();
		}

		return minion;
	}

	public Map<Integer, CustomerModel> queryMinionList() {
		Map<Integer, CustomerModel> minions = new ConcurrentHashMap<>();

		TCPUserClient socket = null;
		try {
			socket = new TCPUserClient(Settings.hostname, Settings.port);

			UserQueryMinionList query = new UserQueryMinionList();
			System.out.println("Testing command: UserQueryMinionList");
			socket.sendMessage(query);
			System.out.println("Message send. Waiting for response.");

			Message ans = socket.receiveMessage();
			if (ans.getMsgType() == MessageType.USER_QUERY_MINIONLIST) {
				query = (UserQueryMinionList) ans;
				System.out.println("Got response.");
				for (int i : query.getMinionList()) {
					CustomerModel minion = new CustomerModel();
					minion.setMinionId(i);
					minions.put(minion.getMinionId(), minion);
				}
				System.out.println("Prepared list");
			} else {
				System.out.println("Error receiving message.");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (socket != null)
				socket.closeConnection();
		}

		return minions; // return arraylist
	}

	public String getProcessList(int id) {

		TCPUserClient socket = null;
		try {
			socket = new TCPUserClient(Settings.hostname, Settings.port);

			System.out.println("Testing command: ProcessList");
			CmdQuery query = new CmdQuery(CmdType.MINION_PROCESS_LIST, MessageType.MINION_PROCESS_LIST, id);
			socket.sendMessage(query);
			System.out.println("Message send. Wainting for response");
			Message message2 = socket.receiveMessage();
			if (message2.getMsgType() == MessageType.MINION_PROCESS_LIST) {
				System.out.println("Correct Answer received: ");
				query = (CmdQuery) message2;
				return query.toString();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (socket != null)
				socket.closeConnection();
		}

		return " ";


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
	 * @return the cPU
	 */
	public String getCPU() {
		return CPU;
	}

	/**
	 * @param cPU the cPU to set
	 */
	public void setCPU(String cPU) {
		CPU = cPU;
	}

	/**
	 * @return the iP
	 */
	public String getIP() {
		return IP;
	}

	/**
	 * @param iP the iP to set
	 */
	public void setIP(String iP) {
		IP = iP;
	}

	/**
	 * @return the rAM
	 */
	public String getRAM() {
		return RAM;
	}

	/**
	 * @param rAM the rAM to set
	 */
	public void setRAM(String rAM) {
		RAM = rAM;
	}

	/**
	 * @return the publicIP
	 */
	public String getPublicIP() {
		return publicIP;
	}

	/**
	 * @param publicIP the publicIP to set
	 */
	public void setPublicIP(String publicIP) {
		this.publicIP = publicIP;
	}

	/**
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * @param hostName the hostName to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * @return the online
	 */
	public boolean isOnline() {
		return online;
	}

	/**
	 * @param online the online to set
	 */
	public void setOnline(boolean online) {
		this.online = online;
	}

	/**
	 * @return the select
	 */
	public boolean isSelect() {
		return select;
	}

	/**
	 * @param select the select to set
	 */
	public void setSelect(boolean select) {
		this.select = select;
	}

}