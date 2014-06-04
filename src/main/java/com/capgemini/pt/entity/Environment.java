package com.capgemini.pt.entity;

import java.io.Serializable;

public class Environment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;

	public Environment(String name) {
		this.name = name;
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
