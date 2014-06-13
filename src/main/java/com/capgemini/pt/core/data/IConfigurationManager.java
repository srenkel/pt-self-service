package com.capgemini.pt.core.data;

import java.util.List;

import com.capgemini.pt.core.data.yaml.base.BaseConfiguration;
import com.capgemini.pt.entity.Application;
import com.capgemini.pt.entity.Definition;
import com.capgemini.pt.entity.Increment;

public interface IConfigurationManager {

	public List<Application> getApplications();

	public List<Increment> getIncrementsForApplication(Application application);

	public BaseConfiguration getSelfServiceConfiguration();

	// public PuppetConfiguration getPuppetHieraConfiguration(Server server);

	// public ApplicationConfiguration getApplicationsConfiguration();
	//
	// public boolean storeApplicationsConfiguration(
	// ApplicationConfiguration appsConfig);

	public boolean storeSelfServiceConfiguration(BaseConfiguration base);

	public boolean storePuppetHieraConfigurationForDefinition(
			Definition definition);

}
