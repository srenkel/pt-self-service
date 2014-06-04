package com.capgemini.pt.entity;

import java.io.Serializable;

public class Application implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String groupId;

	public Application(String name, String groupId) {
		super();
		this.name = name;
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public String getGroupId() {
		return groupId;
	}
}
