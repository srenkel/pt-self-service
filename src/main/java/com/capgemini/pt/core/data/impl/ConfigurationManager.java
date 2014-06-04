/**
 * Copyright to srenkel 2014
 */
package com.capgemini.pt.core.data.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.capgemini.pt.SelfServiceConfigurationManager;
import com.capgemini.pt.core.data.IConfigurationManager;
import com.capgemini.pt.entity.Application;
import com.capgemini.pt.entity.Increment;

public class ConfigurationManager implements IConfigurationManager {

	public ConfigurationManager() {
	}

	@Override
	public List<Application> getApplications() {
		Map<String, Map<String, String>> values;
		try {
			values = getSelfServiceConfigurationYamlValues();
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
			values = getSelfServiceConfigurationYamlValues();
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

	@Override
	public boolean addConfigurationToNode(Object todo, Object todo2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean clearConfigurationFromNode(Object todo, Object todo2) {
		// TODO Auto-generated method stub
		return false;
	}

	public Map<String, Map<String, String>> getSelfServiceConfigurationYamlValues()
			throws FileNotFoundException {
		Yaml yaml = new Yaml();
		// System.out.println(yaml.dump(yaml.load(new FileInputStream(new File(
		// SelfServiceConfigurationManager
		// .getDefaultConfigurationFilePath()
		// + SelfServiceConfigurationManager
		// .getApplicationsConfigurationFilename())))));

		@SuppressWarnings("unchecked")
		Map<String, Map<String, String>> values = (Map<String, Map<String, String>>) yaml
				.load(new FileInputStream(
						new File(SelfServiceConfigurationManager
								.getDefaultConfigurationFilePath()
								+ SelfServiceConfigurationManager
										.getApplicationsConfigurationFilename())));

		return values;
	}
}
