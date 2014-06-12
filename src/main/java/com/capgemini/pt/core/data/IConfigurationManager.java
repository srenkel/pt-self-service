package com.capgemini.pt.core.data;

import java.io.File;
import java.util.List;

import com.capgemini.pt.core.data.yaml.base.BaseConfiguration;
import com.capgemini.pt.core.data.yaml.puppet.PuppetConfiguration;
import com.capgemini.pt.entity.Application;
import com.capgemini.pt.entity.Definition;
import com.capgemini.pt.entity.Increment;
import com.capgemini.pt.entity.Server;

public interface IConfigurationManager {

	public List<Application> getApplications();

	public List<Increment> getIncrementsForApplication(Application application);

	public BaseConfiguration getSelfServiceConfiguration();

//	public PuppetConfiguration getPupperHieraConfiguration(Server server);

//	public ApplicationConfiguration getApplicationsConfiguration();
//
//	public boolean storeApplicationsConfiguration(
//			ApplicationConfiguration appsConfig);

	public boolean storeSelfServiceConfiguration(BaseConfiguration base);

	public boolean storePupperHieraConfigurationForDefinition(Definition definition);

}
