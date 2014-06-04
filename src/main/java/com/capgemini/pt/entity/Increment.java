package com.capgemini.pt.entity;

import java.io.Serializable;

public class Increment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String artifactId;

	/**
	 * @param name
	 */
	public Increment(String name,String artifactId) {
		super();
		this.name = name;
		this.artifactId = artifactId;
	}

	public String getName() {
		return name;
	}
	
	public String getArtifactId() {
		return artifactId;
	}
}
