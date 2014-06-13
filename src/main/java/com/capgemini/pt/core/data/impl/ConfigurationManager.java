/**
 * Copyright to srenkel 2014
 */
package com.capgemini.pt.core.data.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.capgemini.pt.SelfServiceConfigurationManager;
import com.capgemini.pt.core.data.IConfigurationManager;
import com.capgemini.pt.core.data.yaml.applications.App;
import com.capgemini.pt.core.data.yaml.applications.ApplicationConfiguration;
import com.capgemini.pt.core.data.yaml.applications.Inc;
import com.capgemini.pt.core.data.yaml.base.BaseConfiguration;
import com.capgemini.pt.core.data.yaml.base.DatabaseConfig;
import com.capgemini.pt.core.data.yaml.puppet.JNDIDatabase;
import com.capgemini.pt.core.data.yaml.puppet.PuppetConfiguration;
import com.capgemini.pt.core.data.yaml.puppet.Webapp;
import com.capgemini.pt.entity.Application;
import com.capgemini.pt.entity.Definition;
import com.capgemini.pt.entity.Increment;
import com.capgemini.pt.entity.Server;

public class ConfigurationManager implements IConfigurationManager {

	public ConfigurationManager() {
	}

	@Override
	public List<Application> getApplications() {
		List<Application> applications = new ArrayList<Application>();

		Map<String, App> appMap = getApplicationsConfiguration().applications;

		for (String key : appMap.keySet()) {
			applications.add(new Application(appMap.get(key).name, appMap
					.get(key).groupId));
		}
		return applications;
	}

	@Override
	public List<Increment> getIncrementsForApplication(Application application) {
		List<Increment> incs = new ArrayList<Increment>();

		Map<String, App> appMap = getApplicationsConfiguration().applications;

		for (String key : appMap.keySet()) {
			if (appMap.get(key).name.equals(application.getName())) {
				List<Inc> increments = appMap.get(key).increments;
				for (int i = 0; i < increments.size(); i++) {
					Inc inc = increments.get(i);
					incs.add(new Increment(inc.name, inc.artifactId));
				}
			}
		}
		return incs;
	}

	public ApplicationConfiguration getApplicationsConfiguration() {
		Constructor constructor = new Constructor(
				ApplicationConfiguration.class);
		TypeDescription description = new TypeDescription(
				ApplicationConfiguration.class);
		constructor.addTypeDescription(description);

		Yaml yaml = new Yaml(constructor);
		try {
			FileInputStream stream = new FileInputStream(new File(
					SelfServiceConfigurationManager
							.getDefaultApplicationsFilePath()
							+ SelfServiceConfigurationManager
									.getApplicationsConfigurationFilename()));

			ApplicationConfiguration appsConfig = (ApplicationConfiguration) yaml
					.load(stream);
			stream.close();

			return appsConfig;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	};

	public boolean storeApplicationsConfiguration(
			ApplicationConfiguration appsConfig) {
		Constructor constructor = new Constructor(
				ApplicationConfiguration.class);
		TypeDescription description = new TypeDescription(
				ApplicationConfiguration.class);
		constructor.addTypeDescription(description);

		Yaml yaml = new Yaml(constructor);

		try {
			FileWriter writer = new FileWriter(
					SelfServiceConfigurationManager
							.getDefaultApplicationsFilePath()
							+ SelfServiceConfigurationManager
									.getApplicationsConfigurationFilename());
			yaml.dump(appsConfig, writer);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	};

	// private Map<String, Map<String, String>> getPuppetHieraYamlValues()
	// throws FileNotFoundException {
	// Yaml yaml = new Yaml();
	//
	// @SuppressWarnings("unchecked")
	// Map<String, Map<String, String>> values = (Map<String, Map<String,
	// String>>) yaml
	// .load(new FileInputStream(new File(
	// SelfServiceConfigurationManager
	// .getDefaultPuppetHieraFilePath()
	// + SelfServiceConfigurationManager
	// .getPuppetHieraFilename())));
	//
	// return values;
	// }

	// private Map<String, Map<String, String>>
	// getApplicationsConfigurationYamlValues()
	// throws FileNotFoundException {
	// Yaml yaml = new Yaml();
	//
	// FileInputStream stream = new FileInputStream(new File(
	// SelfServiceConfigurationManager
	// .getDefaultApplicationsFilePath()
	// + SelfServiceConfigurationManager
	// .getApplicationsConfigurationFilename()));
	//
	// @SuppressWarnings("unchecked")
	// Map<String, Map<String, String>> values = (Map<String, Map<String,
	// String>>) yaml
	// .load(stream);
	// try {
	// stream.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// return values;
	// }

	// private Map<String, Map<String, String>>
	// getSelfServiceConfigurationYamlValues()
	// throws FileNotFoundException {
	// Yaml yaml = new Yaml();
	// // System.out.println(yaml.dump(yaml.load(new FileInputStream(new File(
	// // SelfServiceConfigurationManager
	// // .getDefaultConfigurationFilePath()
	// // + SelfServiceConfigurationManager
	// // .getApplicationsConfigurationFilename())))));
	//
	// @SuppressWarnings("unchecked")
	// Map<String, Map<String, String>> values = (Map<String, Map<String,
	// String>>) yaml
	// .load(new FileInputStream(new File(
	// SelfServiceConfigurationManager
	// .getDefaultConfigurationFilePath()
	// + SelfServiceConfigurationManager
	// .getSelfServiceConfigurationFilename())));
	//
	// return values;
	// }

	@Override
	public BaseConfiguration getSelfServiceConfiguration() {
		Constructor constructor = new Constructor(BaseConfiguration.class);
		TypeDescription description = new TypeDescription(
				BaseConfiguration.class);
		constructor.addTypeDescription(description);

		Yaml yaml = new Yaml(constructor);

		try {
			FileInputStream stream = new FileInputStream(new File(
					SelfServiceConfigurationManager
							.getDefaultConfigurationFilePath()
							+ SelfServiceConfigurationManager
									.getSelfServiceConfigurationFilename()));

			BaseConfiguration base = (BaseConfiguration) yaml.load(stream);
			stream.close();

			return base;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean storeSelfServiceConfiguration(BaseConfiguration base) {
		Constructor constructor = new Constructor(BaseConfiguration.class);
		TypeDescription description = new TypeDescription(
				BaseConfiguration.class);
		constructor.addTypeDescription(description);

		Yaml yaml = new Yaml(constructor);

		try {
			FileWriter writer = new FileWriter(
					SelfServiceConfigurationManager
							.getDefaultConfigurationFilePath()
							+ SelfServiceConfigurationManager
									.getSelfServiceConfigurationFilename());
			yaml.dump(base, writer);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public PuppetConfiguration getPuppetHieraConfiguration(Server server) {
		Constructor constructor = new Constructor(PuppetConfiguration.class);
		TypeDescription description = new TypeDescription(
				PuppetConfiguration.class);
		constructor.addTypeDescription(description);

		Yaml yaml = new Yaml(constructor);

		try {
			FileInputStream stream = new FileInputStream(new File(
					SelfServiceConfigurationManager
							.getDefaultPuppetHieraFilePath()
							+ "node/"
							+ server.getName() + ".yaml"));

			PuppetConfiguration puppetConf = (PuppetConfiguration) yaml
					.load(stream);

			stream.close();

			return puppetConf;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean storePuppetHieraConfigurationForDefinition(
			Definition definition) {
		Map<Server, PuppetConfiguration> puppetConfigs = generatePuppetConfigurationFromDefinition(definition);

		for (Entry<Server, PuppetConfiguration> entry : puppetConfigs
				.entrySet()) {
			Server server = entry.getKey();
			PuppetConfiguration config = entry.getValue();
			storePuppetHieraConfiguration(config, server);
		}
		return true;
	}

	private void storePuppetHieraConfiguration(PuppetConfiguration puppetConf,
			Server server) {
		Constructor constructor = new Constructor(PuppetConfiguration.class);
		TypeDescription description = new TypeDescription(
				PuppetConfiguration.class);
		constructor.addTypeDescription(description);

		Yaml yaml = new Yaml(constructor);

		try {
			String filePath = SelfServiceConfigurationManager
					.getDefaultPuppetHieraFilePath()
					+ "node/"
					+ server.getName() + ".yaml";
			FileWriter writer = new FileWriter(filePath);
			yaml.dump(puppetConf, writer);
			writer.close();

			File path = new File(filePath);
			Scanner scanner = new Scanner(path);
			ArrayList<String> coll = new ArrayList<String>();
			scanner.nextLine();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				coll.add(line);
			}

			scanner.close();

			FileWriter writer2 = new FileWriter(path);
			for (String line : coll) {
				writer2.write(line);
				writer2.write("\n");
			}

			writer2.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Map<Server, PuppetConfiguration> generatePuppetConfigurationFromDefinition(
			Definition definitionToDeploy) {
		// GET SERVERS FOR CHOSEN ENVIRONMENT
		PuppetDataManager puppetData = new PuppetDataManager();
		ConfigurationManager configManager = new ConfigurationManager();
		List<Server> servers = puppetData
				.getServerForEnvironment(definitionToDeploy.getEnv());

		Map<Server, PuppetConfiguration> puppetConfigs = new HashMap<Server, PuppetConfiguration>();

		// FOR EACH SERVER GENERATE ITS CONFIG
		for (int i = 0; i < servers.size(); i++) {
			Server server = servers.get(i);

			PuppetConfiguration puppetConf = getPuppetHieraConfiguration(server);
			if (puppetConf == null) {
				puppetConf = new PuppetConfiguration();
			}
			// ROLES
			if (puppetConf.role == null) {
				puppetConf.role = new ArrayList<String>();
			}
			if (server.isApp()
					&& !puppetConf.role.contains("role::applicationserver")) {
				puppetConf.role.add("roles::applicationserver");
			}
			if (server.isDb()
					&& !puppetConf.role.contains("role::databaseserver")) {
				puppetConf.role.add("roles::databaseserver");
			}

			if (server.isApp()) {
				boolean isRoot = false;

				if (puppetConf.maven_webapps == null) {
					puppetConf.maven_webapps = new HashMap<String, Webapp>();
					isRoot = true;
				} else if (!puppetConf.maven_webapps.containsKey("ROOT")) {
					isRoot = true;
				}
				String contextRoot = "ROOT";
				if (!isRoot) {
					int ident = 1;
					if (puppetConf.maven_webapps.containsKey(definitionToDeploy
							.getApp().getName().toLowerCase())) {
						while (puppetConf.maven_webapps
								.containsKey(definitionToDeploy.getApp()
										.getName().toLowerCase()
										+ ident)) {
							ident++;
						}
						contextRoot = definitionToDeploy.getApp().getName()
								.toLowerCase()
								+ ident;
					} else {
						contextRoot = definitionToDeploy.getApp().getName()
								.toLowerCase();
					}
				}
				Webapp webapp = new Webapp();
				webapp.groupid = definitionToDeploy.getApp().getGroupId();
				webapp.artifactid = definitionToDeploy.getInc().getArtifactId();
				webapp.version = definitionToDeploy.getBuild().getName();
				webapp.instance = "tomcat_1";
				webapp.repos = configManager.getSelfServiceConfiguration().repositories;
				puppetConf.maven_webapps.put(contextRoot, webapp);

				// TODO JNDI Datasources
				if (puppetConf.jndi_databases == null) {
					puppetConf.jndi_databases = new HashMap<String, JNDIDatabase>();
				}

				if (definitionToDeploy.getDbschema() != null) {
					DatabaseConfig dbConfig = null;

					for (int j = 0; j < configManager
							.getSelfServiceConfiguration().databases.size(); j++) {
						DatabaseConfig tmpConfig = configManager
								.getSelfServiceConfiguration().databases.get(j);
						if (tmpConfig.name.equals(definitionToDeploy
								.getDbschema().getCleanedName())) {
							dbConfig = tmpConfig;
							break;
						}
					}
					if (dbConfig != null
							&& !puppetConf.jndi_databases
									.containsKey(dbConfig.resource_name)) {
						JNDIDatabase database = new JNDIDatabase();
						database.instance = "tomcat_1";
						database.database = dbConfig.database;
						database.username = dbConfig.username;
						database.password = dbConfig.password;
						database.resource_name = dbConfig.resource_name;
						database.host = definitionToDeploy.getDbschema()
								.getHost();
						database.use_unicode = dbConfig.use_unicode;
						database.character_encoding = dbConfig.character_encoding;
						
						puppetConf.jndi_databases.put(dbConfig.resource_name,
								database);
					}
				}
			} else {
				puppetConf.maven_webapps = new HashMap<String, Webapp>();
				puppetConf.jndi_databases = new HashMap<String, JNDIDatabase>();
			}

			puppetConfigs.put(server, puppetConf);
		}
		return puppetConfigs;
	}
}
