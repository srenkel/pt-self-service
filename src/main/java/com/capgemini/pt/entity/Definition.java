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
@Table(name = "definitions", schema = "ptselfservice")
@NamedQueries({
		@NamedQuery(name = "Definition.findAll", query = "SELECT d FROM Definition d"),
		@NamedQuery(name = "Definition.findByName", query = "SELECT d FROM Definition d WHERE d.name = :name") })
public class Definition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long oid;

	private String name;
	private Application app;
	private Increment inc;
	private Version build;
	private Environment env;
	private DatabaseSchema dbschema;

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
	 * @param dbschema
	 */
	public Definition(String name, Application app, Increment inc,
			Version build, Environment env, DatabaseSchema dbschema) {
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
	public Application getApp() {
		return app;
	}

	/**
	 * @param app
	 *            the app to set
	 */
	public void setApp(Application app) {
		this.app = app;
	}

	/**
	 * @return the inc
	 */
	public Increment getInc() {
		return inc;
	}

	/**
	 * @param inc
	 *            the inc to set
	 */
	public void setInc(Increment inc) {
		this.inc = inc;
	}

	/**
	 * @return the build
	 */
	public Version getBuild() {
		return build;
	}

	/**
	 * @param build
	 *            the build to set
	 */
	public void setBuild(Version build) {
		this.build = build;
	}

	/**
	 * @return the env
	 */
	public Environment getEnv() {
		return env;
	}

	/**
	 * @param env
	 *            the env to set
	 */
	public void setEnv(Environment env) {
		this.env = env;
	}

	/**
	 * @return the dbschema
	 */
	public DatabaseSchema getDbschema() {
		return dbschema;
	}

	/**
	 * @param dbschema
	 *            the dbschema to set
	 */
	public void setDbschema(DatabaseSchema dbschema) {
		this.dbschema = dbschema;
	}

	public Long getOid() {
		// TODO Auto-generated method stub
		return oid;
	}

}
