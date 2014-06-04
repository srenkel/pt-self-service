package com.capgemini.pt.entity;

import java.io.Serializable;

public class DatabaseSchema implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;

	public DatabaseSchema(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
