package com.capgemini.pt.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "definitions", schema = "deploymentmanager")
@NamedQueries({
		@NamedQuery(name = "Definition.findAll", query = "SELECT d FROM Definition d"),
		@NamedQuery(name = "Definition.findByName", query = "SELECT d FROM Definition d WHERE d.name = :name") })
public class Definition {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long oid;

	private String name;
	private String app;
	private String inc;
	private String env;

	public Definition() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param app
	 * @param inc
	 * @param env
	 * @param string
	 */
	public Definition(String name, String app, String inc, String env) {
		super();
		this.name = name;
		this.app = app;
		this.inc = inc;
		this.env = env;
	}

	public Long getOid() {
		// TODO Auto-generated method stub
		return oid;
	}

}
