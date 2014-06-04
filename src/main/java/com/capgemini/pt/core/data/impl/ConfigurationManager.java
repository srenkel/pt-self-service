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
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.capgemini.pt.SelfServiceConfigurationManager;
import com.capgemini.pt.core.data.IConfigurationManager;
import com.capgemini.pt.core.data.yaml.applications.App;
import com.capgemini.pt.core.data.yaml.applications.ApplicationConfiguration;
import com.capgemini.pt.core.data.yaml.applications.Inc;
import com.capgemini.pt.core.data.yaml.base.BaseConfiguration;
import com.capgemini.pt.core.data.yaml.puppet.PuppetConfiguration;
import com.capgemini.pt.entity.Application;
import com.capgemini.pt.entity.Increment;

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

//	private Map<String, Map<String, String>> getApplicationsConfigurationYamlValues()
//			throws FileNotFoundException {
//		Yaml yaml = new Yaml();
//
//		FileInputStream stream = new FileInputStream(new File(
//				SelfServiceConfigurationManager
//						.getDefaultApplicationsFilePath()
//						+ SelfServiceConfigurationManager
//								.getApplicationsConfigurationFilename()));
//
//		@SuppressWarnings("unchecked")
//		Map<String, Map<String, String>> values = (Map<String, Map<String, String>>) yaml
//				.load(stream);
//		try {
//			stream.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return values;
//	}

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

	@Override
	public PuppetConfiguration getPupperHieraConfiguration() {
		Constructor constructor = new Constructor(PuppetConfiguration.class);
		TypeDescription description = new TypeDescription(
				PuppetConfiguration.class);
		constructor.addTypeDescription(description);

		Yaml yaml = new Yaml(constructor);

		try {
			FileInputStream stream = new FileInputStream(new File(
					SelfServiceConfigurationManager
							.getDefaultConfigurationFilePath()
							+ SelfServiceConfigurationManager
									.getSelfServiceConfigurationFilename()));

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
	public boolean storePupperHieraConfiguration(PuppetConfiguration puppetConf) {
		Constructor constructor = new Constructor(PuppetConfiguration.class);
		TypeDescription description = new TypeDescription(
				PuppetConfiguration.class);
		constructor.addTypeDescription(description);

		Yaml yaml = new Yaml(constructor);

		try {
			FileWriter writer = new FileWriter(
					SelfServiceConfigurationManager
							.getDefaultPuppetHieraFilePath()
							+ SelfServiceConfigurationManager
									.getPuppetHieraFilename());
			yaml.dump(puppetConf, writer);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
