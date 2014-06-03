package com.capgemini.pt.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "applicationstatus", schema = "deploymentmanager")
@NamedQuery(name = "ApplicationStatus.findAll", query = "SELECT a FROM ApplicationStatus a")
public class ApplicationStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long oid;

	public Long getOid() {
		return oid;
	}
}
