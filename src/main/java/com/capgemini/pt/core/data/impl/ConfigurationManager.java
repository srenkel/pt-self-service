/**
 * Copyright to srenkel 2014
 */
package com.capgemini.pt.core.data.impl;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.pt.core.data.IConfigurationManager;
import com.capgemini.pt.entity.Application;
import com.capgemini.pt.entity.Increment;

public class ConfigurationManager implements IConfigurationManager {

	public ConfigurationManager() {
	}

	@Override
	public List<Application> getApplications() {
		// TODO Auto-generated method stub
		List<Application> apps = new ArrayList<Application>();
		apps.add(new Application("TANGO"));
		apps.add(new Application("COMPASS"));
		return apps;
	}

	@Override
	public List<Increment> getIncrementsForApplication(Application application) {
		// TODO Auto-generated method stub
		List<Increment> incs = new ArrayList<Increment>();
		incs.add(new Increment("Increment 1"));
		incs.add(new Increment("Increment 2"));
		incs.add(new Increment("Increment 3"));
		return incs;
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

}
