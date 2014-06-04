package com.capgemini.pt.core.data;

import java.util.List;

import com.capgemini.pt.entity.Application;
import com.capgemini.pt.entity.Increment;

public interface IConfigurationManager {
	
	public List<Application> getApplications();
	
	public List<Increment> getIncrementsForApplication(Application application);
	
	public boolean addConfigurationToNode(Object todo, Object todo2);
	
	public boolean clearConfigurationFromNode(Object todo, Object todo2);

}
