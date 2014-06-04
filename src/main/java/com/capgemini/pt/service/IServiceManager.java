package com.capgemini.pt.service;

import java.util.List;

import com.capgemini.pt.entity.Application;
import com.capgemini.pt.entity.ApplicationStatus;
import com.capgemini.pt.entity.Artifact;
import com.capgemini.pt.entity.DatabaseSchema;
import com.capgemini.pt.entity.Definition;
import com.capgemini.pt.entity.Environment;
import com.capgemini.pt.entity.Increment;
import com.capgemini.pt.entity.Version;

public interface IServiceManager {

	public List<Application> getApplications();

	public List<Environment> getEnvironments();

	public List<Definition> getDefinitions();

	public Definition getDefinition(String defName);

	public List<ApplicationStatus> getApplicationStatus();

	public List<Increment> getIncrementsForApplication(Application app);

	public List<DatabaseSchema> getSchemasForEnvironment(Environment env);

	public List<Version> getBuildsForIncrement(Artifact artifact);

	public Definition saveDefinition(Definition definition);

	public boolean deploy(Definition definition);

}
