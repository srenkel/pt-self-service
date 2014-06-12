package com.capgemini.pt.entity;

import java.io.Serializable;

import com.capgemini.pt.core.data.impl.ConfigurationManager;

public class DatabaseSchema implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String host;

	public DatabaseSchema(String name, String host) {
		this.name = name;
		this.host = host;
	}

	public String getName() {
		return name;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getCleanedName() {
		return name
				.replaceFirst(
						new ConfigurationManager()
								.getSelfServiceConfiguration().identifier.schema
								+ "::", "");
	}

	public String getFullName() {
		return getCleanedName() + " on " + host;
	}

}
