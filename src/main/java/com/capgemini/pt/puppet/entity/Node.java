package com.capgemini.pt.puppet.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the nodes database table.
 * 
 */
@Entity
@Table(name = "nodes", schema = "dashboard_production")
@NamedQueries({
		@NamedQuery(name = "Node.findAll", query = "SELECT n FROM Node n"),
		@NamedQuery(name = "Node.findByName", query = "SELECT n FROM Node n WHERE n.name = :name") })
public class Node implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(columnDefinition = "TINYINT(1) DEFAULT 0")
	private Boolean hidden;

	@Column(name = "last_apply_report_id")
	private Integer lastApplyReportId;

	@Column(name = "last_inspect_report_id")
	private Integer lastInspectReportId;

	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "reported_at")
	private Date reportedAt;

	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	// bi-directional many-to-many association to NodeGroup
	@ManyToMany
	@JoinTable(schema = "dashboard_production", name = "node_group_memberships", joinColumns = { @JoinColumn(name = "node_id") }, inverseJoinColumns = { @JoinColumn(name = "node_group_id") })
	private List<NodeGroup> nodeGroups;

	// bi-directional many-to-many association to NodeClass
	@ManyToMany
	@JoinTable(schema = "dashboard_production", name = "node_class_memberships", joinColumns = { @JoinColumn(name = "node_id") }, inverseJoinColumns = { @JoinColumn(name = "node_class_id") })
	private List<NodeClass> nodeClasses;

	public Node() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getHidden() {
		return this.hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public int getLastApplyReportId() {
		return this.lastApplyReportId;
	}

	public void setLastApplyReportId(int lastApplyReportId) {
		this.lastApplyReportId = lastApplyReportId;
	}

	public int getLastInspectReportId() {
		return this.lastInspectReportId;
	}

	public void setLastInspectReportId(int lastInspectReportId) {
		this.lastInspectReportId = lastInspectReportId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getReportedAt() {
		return this.reportedAt;
	}

	public void setReportedAt(Date reportedAt) {
		this.reportedAt = reportedAt;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<NodeGroup> getNodeGroups() {
		return this.nodeGroups;
	}

	public void setNodeGroups(List<NodeGroup> nodeGroups) {
		this.nodeGroups = nodeGroups;
	}

	/**
	 * @return the nodeClasses
	 */
	public List<NodeClass> getNodeClasses() {
		return nodeClasses;
	}

	/**
	 * @param nodeClasses
	 *            the nodeClasses to set
	 */
	public void setNodeClasses(List<NodeClass> nodeClasses) {
		this.nodeClasses = nodeClasses;
	}

}