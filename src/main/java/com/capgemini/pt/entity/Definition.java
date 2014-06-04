package com.capgemini.pt.entity;

import java.io.Serializable;

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
public class Definition implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long oid;

	private String name;
	private String app;
	private String inc;
	private String build;
	private String env;
	private String dbschema;

	public Definition() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return name + " " + app + " " + inc + " " + build + " " + env + " "
				+ dbschema;
	}

	/**
	 * @param name
	 * @param app
	 * @param inc
	 * @param build
	 * @param env
	 * @param schema
	 */
	public Definition(String name, String app, String inc, String build,
			String env, String dbschema) {
		super();
		this.name = name;
		this.app = app;
		this.inc = inc;
		this.build = build;
		this.env = env;
		this.dbschema = dbschema;
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
	 * @return the app
	 */
	public String getApp() {
		return app;
	}

	/**
	 * @param app
	 *            the app to set
	 */
	public void setApp(String app) {
		this.app = app;
	}

	/**
	 * @return the inc
	 */
	public String getInc() {
		return inc;
	}

	/**
	 * @param inc
	 *            the inc to set
	 */
	public void setInc(String inc) {
		this.inc = inc;
	}

	/**
	 * @return the build
	 */
	public String getBuild() {
		return build;
	}

	/**
	 * @param build
	 *            the build to set
	 */
	public void setBuild(String build) {
		this.build = build;
	}

	/**
	 * @return the env
	 */
	public String getEnv() {
		return env;
	}

	/**
	 * @param env
	 *            the env to set
	 */
	public void setEnv(String env) {
		this.env = env;
	}

	/**
	 * @return the schema
	 */
	public String getSchema() {
		return dbschema;
	}

	/**
	 * @param schema
	 *            the schema to set
	 */
	public void setSchema(String schema) {
		this.dbschema = schema;
	}

	public Long getOid() {
		// TODO Auto-generated method stub
		return oid;
	}

}
