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
import com.capgemini.pt.core.data.yaml.base.BaseConfiguration;
import com.capgemini.pt.core.data.yaml.puppet.PuppetConfiguration;
import com.capgemini.pt.entity.Application;
import com.capgemini.pt.entity.Increment;

public class ConfigurationManager implements IConfigurationManager {

	public ConfigurationManager() {
	}

	@Override
	public List<Application> getApplications() {
		Map<String, Map<String, String>> values;
		try {
			values = getApplicationsConfigurationYamlValues();
			List<Application> applications = new ArrayList<Application>();

			for (String key : values.keySet()) {
				if (key.equals(SelfServiceConfigurationManager
						.getYamlApplicationSectionIdentifier())) {
					Map<String, String> subValues = values.get(key);
					for (String subValueKey : subValues.keySet()) {
						applications.add(new Application(subValueKey));
					}
				}
			}
			return applications;
		} catch (FileNotFoundException e) {
			return new ArrayList<Application>();
		}

	}

	@Override
	public List<Increment> getIncrementsForApplication(Application application) {
		Map<String, Map<String, String>> values;
		try {
			values = getApplicationsConfigurationYamlValues();
			List<Increment> incs = new ArrayList<Increment>();

			for (String key : values.keySet()) {
				if (key.equals(SelfServiceConfigurationManager
						.getYamlApplicationSectionIdentifier())) {
					Map<String, String> subValues = values.get(key);
					for (String subValueKey : subValues.keySet()) {
						if (subValueKey.equals(application.getName())) {
							String incrementsArrayString = String.format("%s",
									subValues.get(subValueKey));

							String[] incrementsArray = incrementsArrayString
									.replace("[", "").replace("]", "")
									.split(", ");
							for (int i = 0; i < incrementsArray.length; i++) {
								String incString = incrementsArray[i];
								incs.add(new Increment(incString));
							}
						}
					}
				}
			}
			return incs;
		} catch (FileNotFoundException e) {
			return new ArrayList<Increment>();
		}

	}

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

	private Map<String, Map<String, String>> getApplicationsConfigurationYamlValues()
			throws FileNotFoundException {
		Yaml yaml = new Yaml();

		FileInputStream stream = new FileInputStream(new File(
				SelfServiceConfigurationManager
						.getDefaultApplicationsFilePath()
						+ SelfServiceConfigurationManager
								.getApplicationsConfigurationFilename()));

		@SuppressWarnings("unchecked")
		Map<String, Map<String, String>> values = (Map<String, Map<String, String>>) yaml
				.load(stream);
		try {
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return values;
	}

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
