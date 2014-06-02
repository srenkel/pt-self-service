/**
 * Capgemini PT-Support
 */
package com.capgemini.pt.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.rest.annotations.MethodMapping;
import org.wicketstuff.rest.annotations.parameters.RequestBody;
import org.wicketstuff.rest.contenthandling.mimetypes.RestMimeTypes;
import org.wicketstuff.rest.resource.gson.GsonRestResource;
import org.wicketstuff.rest.utils.http.HttpMethod;

import com.capgemini.pt.core.data.IPuppetDataManager;
import com.capgemini.pt.core.data.PuppetDataManager;
import com.capgemini.pt.entity.Application;
import com.capgemini.pt.entity.ApplicationStatus;
import com.capgemini.pt.entity.DatabaseSchema;
import com.capgemini.pt.entity.Definition;
import com.capgemini.pt.entity.Environment;
import com.capgemini.pt.entity.Increment;
import com.capgemini.pt.entity.Version;

/**
 *
 */
public class ServiceManager extends GsonRestResource implements IServiceManager {

	private static final long serialVersionUID = 1L;
	
	private PuppetDataManager puppetDataManager = new PuppetDataManager();
	
	@Override
	@MethodMapping(value = "/applications", httpMethod = HttpMethod.GET)
	public List<Application> getApplications() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@MethodMapping(value = "/environments", httpMethod = HttpMethod.GET)
	public List<Environment> getEnvironments() {
		// TODO Auto-generated method stub
		return puppetDataManager.getEnvironments();
	}

	@Override
	@MethodMapping(value = "/increments", httpMethod = HttpMethod.GET)
	public List<Increment> getIncrementsForApplication(Application app) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@MethodMapping(value = "/schemas", httpMethod = HttpMethod.GET)
	public List<DatabaseSchema> getSchemasForEnvironment(Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@MethodMapping(value = "/versions", httpMethod = HttpMethod.GET)
	public List<Version> getBuildsForApplicationIncrement(Application app,
			Increment inc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@MethodMapping(value = "/definitions", httpMethod = HttpMethod.GET)
	public List<Definition> getDefinitions() {
		// TODO Auto-generated method stub
		ArrayList<Definition> list = new ArrayList<Definition>();
		list.add(new Definition("testapp1", "testinc1", "testenv1"));
		list.add(new Definition("testapp2", "testinc2", "testenv2"));
		list.add(new Definition("testapp3", "testinc3", "testenv3"));
		return list;
	}

	@Override
	@MethodMapping(value = "/status", httpMethod = HttpMethod.GET)
	public List<ApplicationStatus> getApplicationStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@MethodMapping(value = "/definitions", httpMethod = HttpMethod.POST)
	public Definition saveDefinition(@RequestBody Definition definition) {
		// TODO Auto-generated method stub
		return new Definition("testapp", "testinc", "testenv");
	}

	@Override
	@MethodMapping(value = "/deploy", httpMethod = HttpMethod.POST)
	public Definition deploy(Application app, Increment inc, Version version,
			Environment env, DatabaseSchema schemas) {
		// TODO Auto-generated method stub
		return null;
	}

}
