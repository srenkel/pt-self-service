package com.capgemini.pt.puppet.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the node_classes database table.
 * 
 */
@Entity
@Table(name = "node_classes", schema = "dashboard_production")
@NamedQuery(name = "NodeClass.findAll", query = "SELECT n FROM NodeClass n")
public class NodeClass implements Serializable {
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
	@ManyToMany(mappedBy = "nodeClasses")
	private List<Node> nodes;

	// bi-directional many-to-many association to NodeGroup
	@ManyToMany(mappedBy = "nodeClasses")
	private List<NodeGroup> nodeGroups;

	public NodeClass() {
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

	public List<NodeGroup> getNodeGroups() {
		return this.nodeGroups;
	}

	public void setNodeGroups(List<NodeGroup> nodeGroups) {
		this.nodeGroups = nodeGroups;
	}

}