/**
 * Copyright to srenkel 2014
 */
package com.capgemini.pt.core.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.capgemini.pt.entity.DatabaseSchema;
import com.capgemini.pt.entity.Environment;
import com.capgemini.pt.entity.Server;
import com.capgemini.pt.persistence.DatabaseManager;
import com.capgemini.pt.puppet.entity.Node;
import com.capgemini.pt.puppet.entity.NodeGroup;

@Service
public class PuppetDataManager extends DatabaseManager implements
		IPuppetDataManager {

	public PuppetDataManager() {
		super(PERSISTENCE_UNIT_PUPPET_DASHBOARD);
	}

	@Override
	public List<Environment> getEnvironments() {
		// TODO Auto-generated method stub
		final Query query = getEntityManager().createNamedQuery("Node.findAll");
		List<Node> nodes = query.getResultList();
		List<Environment> environments = new ArrayList<Environment>();
		for (int i = 0; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			List<NodeGroup> groups = node.getNodeGroups();
			for (int j = 0; j < groups.size(); j++) {
				NodeGroup group = groups.get(i);
				environments.add(new Environment(group.getName()));
			}
		}
		return environments;
	}

	@Override
	public List<Server> getServerForEnvironment(Environment environment) {
		final Query query = getEntityManager().createNamedQuery("Node.findAll");
		List<Node> nodes = query.getResultList();
		List<Server> servers = new ArrayList<Server>();
		for (int i = 0; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			List<NodeGroup> groups = node.getNodeGroups();
			for (int j = 0; j < groups.size(); j++) {
				NodeGroup group = groups.get(i);
				if (group.getName().equals("Capgemini Test Project")) {
					servers.add(new Server("Capgemini Test Project"));
				}
			}
		}
		return servers;
	}

	@Override
	public List<DatabaseSchema> getSchemasForServer(Server server) {
		// TODO Auto-generated method stub
		return null;
	}

}
