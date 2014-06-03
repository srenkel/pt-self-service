/**
 * Copyright to srenkel 2014
 */
package com.capgemini.pt.core.data.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.capgemini.pt.SelfServiceConfigurationManager;
import com.capgemini.pt.core.data.IPuppetDataManager;
import com.capgemini.pt.entity.DatabaseSchema;
import com.capgemini.pt.entity.Environment;
import com.capgemini.pt.entity.Server;
import com.capgemini.pt.persistence.DatabaseManager;
import com.capgemini.pt.puppet.entity.Node;
import com.capgemini.pt.puppet.entity.NodeClass;
import com.capgemini.pt.puppet.entity.NodeGroup;

public class PuppetDataManager extends DatabaseManager implements
		IPuppetDataManager {

	public PuppetDataManager() {
		super(PERSISTENCE_UNIT_PUPPET_DASHBOARD);
	}

	@Override
	public List<Environment> getEnvironments() {
		final Query query = getEntityManager().createNamedQuery(
				"NodeGroup.findAll");
		@SuppressWarnings("unchecked")
		List<NodeGroup> allGroups = query.getResultList();
		List<Environment> environments = new ArrayList<Environment>();
		for (int j = 0; j < allGroups.size(); j++) {
			NodeGroup group = allGroups.get(j);
			List<NodeClass> groupClasses = group.getNodeClasses();
			boolean isEnv = false;
			for (int i = 0; i < groupClasses.size(); i++) {
				NodeClass nodeClass = groupClasses.get(i);
				if (nodeClass.getName().equals(
						SelfServiceConfigurationManager
								.getEnvironmentGroupIdentifier())) {
					isEnv = true;
					break;
				}
			}
			if (isEnv) {
				environments.add(new Environment(group.getName()));
			}
		}
		return environments;
	}

	@Override
	public List<Server> getServerForEnvironment(Environment environment) {
		final Query query = getEntityManager().createNamedQuery("Node.findAll");
		@SuppressWarnings("unchecked")
		List<Node> nodes = query.getResultList();
		List<Server> servers = new ArrayList<Server>();
		for (int i = 0; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			List<NodeGroup> groups = node.getNodeGroups();
			boolean isEnv = false;
			boolean isApp = false;
			boolean isDb = false;
			for (int j = 0; j < groups.size(); j++) {
				NodeGroup group = groups.get(j);
				if (group.getName().equals(environment.getName())) {
					isEnv = true;
					break;
				}
			}
			if (isEnv) {
				List<NodeClass> classes = node.getNodeClasses();
				for (int j = 0; j < classes.size(); j++) {
					NodeClass nodeClass = classes.get(j);
					if (nodeClass.getName().equals(
							SelfServiceConfigurationManager
									.getApplicationServerClassIdentifier())) {
						isApp = true;
					}
					if (nodeClass.getName().equals(
							SelfServiceConfigurationManager
									.getDatabaseServerClassIdentifier())) {
						isDb = true;
					}
				}
				servers.add(new Server(node.getName(), isApp, isDb));
			}
		}
		return servers;
	}

	@Override
	public List<DatabaseSchema> getSchemasForEnvironment(Environment environment) {
		List<Server> servers = getServerForEnvironment(environment);
		List<DatabaseSchema> schemas = new ArrayList<DatabaseSchema>();
		for (int i = 0; i < servers.size(); i++) {
			Server server = servers.get(i);
			if (server.isDb()) {
				schemas.addAll(getSchemasForServer(server));
			}
		}
		return schemas;
	}

	private List<DatabaseSchema> getSchemasForServer(Server server) {
		final Query query = getEntityManager().createNamedQuery(
				"Node.findByName");
		query.setParameter("name", server.getName());
		@SuppressWarnings("unchecked")
		List<Node> nodes = query.getResultList();
		List<DatabaseSchema> schemas = new ArrayList<DatabaseSchema>();
		for (int i = 0; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			List<NodeClass> classes = node.getNodeClasses();
			for (int j = 0; j < classes.size(); j++) {
				NodeClass nodeClass = classes.get(j);
				if (nodeClass.getName().startsWith(
						SelfServiceConfigurationManager
								.getDatabaseSchemaClassIdentifier())) {
					schemas.add(new DatabaseSchema(nodeClass.getName()));
				}
			}

		}
		return schemas;
	}
}
