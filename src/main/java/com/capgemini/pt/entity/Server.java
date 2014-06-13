package com.capgemini.pt.entity;

public class Server {

	private String name;
	private boolean isApp;
	private boolean isDb;
	private boolean isSe;

	/**
	 * @param name
	 * @param isApp
	 * @param isDb
	 */
	public Server(String name, boolean isApp, boolean isDb, boolean isSe) {
		super();
		this.name = name;
		this.isApp = isApp;
		this.isDb = isDb;
		this.isSe = isSe;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the isApp
	 */
	public boolean isApp() {
		return isApp;
	}

	/**
	 * @param isApp
	 *            the isApp to set
	 */
	public void setApp(boolean isApp) {
		this.isApp = isApp;
	}

	/**
	 * @return the isDb
	 */
	public boolean isDb() {
		return isDb;
	}

	/**
	 * @param isDb
	 *            the isDb to set
	 */
	public void setDb(boolean isDb) {
		this.isDb = isDb;
	}

	/**
	 * @return the isSe
	 */
	public boolean isSe() {
		return isSe;
	}

	/**
	 * @param isSe
	 *            the isSe to set
	 */
	public void setSe(boolean isSe) {
		this.isSe = isSe;
	}

}
