package com.capgemini.pt.service;

import java.util.List;

import com.capgemini.pt.entity.Application;
import com.capgemini.pt.entity.ApplicationStatus;
import com.capgemini.pt.entity.DatabaseSchema;
import com.capgemini.pt.entity.Definition;
import com.capgemini.pt.entity.Environment;
import com.capgemini.pt.entity.Increment;
import com.capgemini.pt.entity.Version;

public interface IServiceManager {

	public List<Application> getApplications();

	public List<Environment> getEnvironments();

	public List<Increment> getIncrementsForApplication(Application app);

	public List<DatabaseSchema> getSchemasForEnvironment(Environment env);

	public List<Version> getBuildsForApplicationIncrement(Application app,
			Increment inc);

	public List<Definition> getDefinitions();

	public List<ApplicationStatus> getApplicationStatus();

	public Definition saveDefinition(Definition definition);

	public Definition deploy(Application app, Increment inc, Version version,
			Environment env, DatabaseSchema schemas);

}
