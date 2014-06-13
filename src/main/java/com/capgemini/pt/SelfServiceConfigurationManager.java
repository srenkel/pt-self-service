package com.capgemini.pt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.aether.repository.RemoteRepository;

import com.capgemini.pt.core.data.impl.ConfigurationManager;

public class SelfServiceConfigurationManager {

	private static ConfigurationManager configManager = new ConfigurationManager();

	private static final String DEFAULT_REPOSITORY_URL = "http://central.maven.org/maven2/";
	private static final String DEFAULT_DATABASESERVER_CLASS_IDENTIFIER = "databaseserver";
	private static final String DEFAULT_APPLICATIONSERVER_CLASS_IDENTIFIER = "applicationserver";
	private static final String DEFAULT_SOFTWAREENVIRONMENT_CLASS_IDENTIFIER = "softwareenvironment";
	private static final String DEFAULT_ENVIRONMENT_GROUP_IDENTIFIER = "environment";
	private static final String DEFAULT_DATABASE_SCHEMA_IDENTIFIER = "database";

	private static final String DEFAULT_PATH_TO_CONFIGFILES = "yaml/";
	private static final String DEFAULT_PATH_TO_APPLICATIONSFILE = "yaml/";
	private static final String DEFAULT_PATH_TO_PUPPET_HIERAFILES = "yaml/";

	private static final String DEFAULT_APPLICATIONS_CONFIG_FILENAME = "applications.yaml";
	private static final String DEFAULT_PUPPET_HIERA_FILENAME = "hiera.yaml";
	private static final String DEFAULT_SELFSERVICE_CONFIG_FILENAME = "config.yaml";

	private static final String DEFAULT_YAML_APPLICATION_SECTION_IDENTIFIER = "APPLICATIONS";

	public static String getApplicationsConfigurationFilename() {

		String filename = configManager.getSelfServiceConfiguration().applications.file;

		if (filename != null && !filename.equals("")) {
			return filename;
		} else {
			return DEFAULT_APPLICATIONS_CONFIG_FILENAME;
		}
	}

	public static String getSelfServiceConfigurationFilename() {
		return DEFAULT_SELFSERVICE_CONFIG_FILENAME;
	}

	public static String getPuppetHieraFilename() {
		String filename = configManager.getSelfServiceConfiguration().puppet.file;

		if (filename != null && !filename.equals("")) {
			return filename;
		} else {
			return DEFAULT_PUPPET_HIERA_FILENAME;
		}
	}

	public static String getDefaultConfigurationFilePath() {
		return DEFAULT_PATH_TO_CONFIGFILES;
	}

	public static String getDefaultPuppetHieraFilePath() {
		String path = configManager.getSelfServiceConfiguration().puppet.path;

		if (path != null && !path.equals("")) {
			return path;
		} else {
			return DEFAULT_PATH_TO_PUPPET_HIERAFILES;
		}
	}

	public static String getDefaultApplicationsFilePath() {
		String path = configManager.getSelfServiceConfiguration().applications.path;

		if (path != null && !path.equals("")) {
			return path;
		} else {
			return DEFAULT_PATH_TO_APPLICATIONSFILE;
		}
	}

	public static String getYamlApplicationSectionIdentifier() {
		String identifier = configManager.getSelfServiceConfiguration().identifier.applicationsection;

		if (identifier != null && !identifier.equals("")) {
			return identifier;
		} else {
			return DEFAULT_YAML_APPLICATION_SECTION_IDENTIFIER;
		}
	}

	public static String getEnvironmentGroupIdentifier() {
		String identifier = configManager.getSelfServiceConfiguration().identifier.environment;

		if (identifier != null && !identifier.equals("")) {
			return identifier;
		} else {
			return DEFAULT_ENVIRONMENT_GROUP_IDENTIFIER;
		}
	}

	public static String getApplicationServerClassIdentifier() {
		String identifier = configManager.getSelfServiceConfiguration().identifier.applicationserver;

		if (identifier != null && !identifier.equals("")) {
			return identifier;
		} else {
			return DEFAULT_APPLICATIONSERVER_CLASS_IDENTIFIER;
		}
	}

	public static String getDatabaseServerClassIdentifier() {
		String identifier = configManager.getSelfServiceConfiguration().identifier.databaseserver;

		if (identifier != null && !identifier.equals("")) {
			return identifier;
		} else {
			return DEFAULT_DATABASESERVER_CLASS_IDENTIFIER;
		}
	}
	
	public static String getSoftwareEnvironmentClassIdentifier() {
		String identifier = configManager.getSelfServiceConfiguration().identifier.softwareenvironment;

		if (identifier != null && !identifier.equals("")) {
			return identifier;
		} else {
			return DEFAULT_SOFTWAREENVIRONMENT_CLASS_IDENTIFIER;
		}
	}

	public static String getDatabaseSchemaClassIdentifier() {
		String identifier = configManager.getSelfServiceConfiguration().identifier.schema;

		if (identifier != null && !identifier.equals("")) {
			return identifier + "::";
		} else {
			return DEFAULT_DATABASE_SCHEMA_IDENTIFIER + "::";
		}
	}

	public static List<RemoteRepository> getRemoteRepositories() {
		// RemoteRepository nexus = new RemoteRepository.Builder("central",
		// "default", DEFAULT_REPOSITORY_URL).setAuthentication(
		// new AuthenticationBuilder().addUsername("admin")
		// .addPassword("admin123").build()).build();
		List<RemoteRepository> remoteRepositories = new ArrayList<RemoteRepository>();
		List<String> repos = configManager.getSelfServiceConfiguration().repositories;
		for (int i = 0; i < repos.size(); i++) {
			String repoUrl = repos.get(i);
			remoteRepositories.add(new RemoteRepository.Builder("repo" + i,
					"default", repoUrl).build());
		}
		if (remoteRepositories.isEmpty()) {
			remoteRepositories.add(new RemoteRepository.Builder("central",
					"default", DEFAULT_REPOSITORY_URL).build());
		}
		return remoteRepositories;
	}
}
