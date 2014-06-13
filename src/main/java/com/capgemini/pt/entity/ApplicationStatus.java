package com.capgemini.pt.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "applicationstatus", schema = "ptselfservice")
@NamedQuery(name = "ApplicationStatus.findAll", query = "SELECT a FROM ApplicationStatus a")
public class ApplicationStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long oid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	private String environment;
	private String build;
	private String application;

	@Enumerated(EnumType.STRING)
	private Status status;

	public ApplicationStatus() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param createdAt
	 * @param environment
	 * @param build
	 * @param application
	 * @param status
	 */
	public ApplicationStatus(String environment, String build,
			String application, Status status) {
		super();
		this.createdAt = new Date();
		this.environment = environment;
		this.build = build;
		this.application = application;
		this.status = status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @return the environment
	 */
	public String getEnvironment() {
		return environment;
	}

	/**
	 * @param environment
	 *            the environment to set
	 */
	public void setEnvironment(String environment) {
		this.environment = environment;
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
	 * @return the application
	 */
	public String getApplication() {
		return application;
	}

	/**
	 * @param application
	 *            the application to set
	 */
	public void setApplication(String application) {
		this.application = application;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getOid() {
		return oid;
	}
}
