package com.capgemini.pt.entity;

import java.io.Serializable;

public class Application implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;

	public Application(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
