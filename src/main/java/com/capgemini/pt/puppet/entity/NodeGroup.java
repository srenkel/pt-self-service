package com.capgemini.pt.puppet.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the node_groups database table.
 * 
 */
@Entity
@Table(name = "node_groups", schema = "dashboard_production")
@NamedQuery(name = "NodeGroup.findAll", query = "SELECT n FROM NodeGroup n")
public class NodeGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	// bi-directional many-to-many association to Node
	@ManyToMany(mappedBy = "nodeGroups")
	private List<Node> nodes;

	// bi-directional many-to-many association to NodeGroup
	@ManyToMany
	@JoinTable(name = "node_group_edges", schema = "dashboard_production", joinColumns = { @JoinColumn(name = "from_id") }, inverseJoinColumns = { @JoinColumn(name = "to_id") })
	private List<NodeGroup> nodeGroups;

	// bi-directional many-to-many association to NodeClass
	@ManyToMany
	@JoinTable(name = "node_group_class_memberships", schema = "dashboard_production", joinColumns = { @JoinColumn(name = "node_group_id") }, inverseJoinColumns = { @JoinColumn(name = "node_class_id") })
	private List<NodeClass> nodeClasses;

	public NodeGroup() {
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Node> getNodes() {
		return this.nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	/**
	 * @return the nodeGroups
	 */
	public List<NodeGroup> getNodeGroups() {
		return nodeGroups;
	}

	/**
	 * @param nodeGroups
	 *            the nodeGroups to set
	 */
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