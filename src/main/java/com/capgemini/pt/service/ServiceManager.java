/**
 * Capgemini PT-Support
 */
package com.capgemini.pt.service;

import java.util.Arrays;
import java.util.List;

import org.eclipse.aether.resolution.VersionRangeResolutionException;
import org.wicketstuff.rest.annotations.MethodMapping;
import org.wicketstuff.rest.annotations.ResourcePath;
import org.wicketstuff.rest.annotations.parameters.PathParam;
import org.wicketstuff.rest.annotations.parameters.RequestBody;
import org.wicketstuff.rest.resource.gson.GsonRestResource;
import org.wicketstuff.rest.utils.http.HttpMethod;

import com.capgemini.pt.core.SelfServiceManager;
import com.capgemini.pt.entity.Application;
import com.capgemini.pt.entity.ApplicationStatus;
import com.capgemini.pt.entity.Artifact;
import com.capgemini.pt.entity.DatabaseSchema;
import com.capgemini.pt.entity.Definition;
import com.capgemini.pt.entity.Environment;
import com.capgemini.pt.entity.Increment;
import com.capgemini.pt.entity.Version;

/**
 *
 */
@ResourcePath("/rest")
public class ServiceManager extends GsonRestResource implements IServiceManager {

	private static final long serialVersionUID = 1L;

	private SelfServiceManager manager = new SelfServiceManager();

	@Override
	@MethodMapping(value = "/applications", httpMethod = HttpMethod.GET)
	public List<Application> getApplications() {
		return manager.getConfigurationManager().getApplications();
	}

	@Override
	@MethodMapping(value = "/environments", httpMethod = HttpMethod.GET)
	public List<Environment> getEnvironments() {
		return manager.getPuppetDataManager().getEnvironments();
	}

	@Override
	@MethodMapping(value = "/definitions", httpMethod = HttpMethod.GET)
	public List<Definition> getDefinitions() {
		return manager.getSelfServiceDataManager().getDefinitions();
	}

	@Override
	@MethodMapping(value = "/definitions/{def}", httpMethod = HttpMethod.GET)
	public Definition getDefinition(@PathParam("def") String defName) {
		try {
			return manager.getSelfServiceDataManager().findDefinition(defName);
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	@MethodMapping(value = "/status", httpMethod = HttpMethod.GET)
	public List<ApplicationStatus> getApplicationStatus() {
		return manager.getSelfServiceDataManager().getApplicationStatus();
	}

	@Override
	@MethodMapping(value = "/increments", httpMethod = HttpMethod.POST)
	public List<Increment> getIncrementsForApplication(
			@RequestBody Application app) {
		return manager.getConfigurationManager().getIncrementsForApplication(
				app);
	}

	@Override
	@MethodMapping(value = "/schemas", httpMethod = HttpMethod.POST)
	public List<DatabaseSchema> getSchemasForEnvironment(
			@RequestBody() Environment env) {
		return manager.getPuppetDataManager().getSchemasForEnvironment(env);
	}

	@Override
	@MethodMapping(value = "/versions", httpMethod = HttpMethod.POST)
	public List<Version> getBuildsForIncrement(@RequestBody Artifact artifact) {
		try {
			return manager.getArtifactManager().findAvailableArtifactVersions(
					artifact);
		} catch (VersionRangeResolutionException e) {
			return Arrays.asList(new Version("ERROR RETRIEVING VERSIONS"));
		}
	}

	@Override
	@MethodMapping(value = "/definitions", httpMethod = HttpMethod.POST)
	public Definition saveDefinition(@RequestBody Definition definition) {
		return manager.getSelfServiceDataManager().storeDefinition(definition);
	}

	@Override
	@MethodMapping(value = "/deploy", httpMethod = HttpMethod.POST)
	public boolean deploy(@RequestBody Definition definitionToDeploy) {
		return manager.deploy(definitionToDeploy);
	}

}
