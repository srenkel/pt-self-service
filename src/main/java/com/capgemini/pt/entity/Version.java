package com.capgemini.pt.entity;

import java.io.Serializable;

public class Version implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;

	public Version(String name) {
		this.name = name;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public void setName(String string) {
		this.name = string;
	}

}
