package com.capgemini.pt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.aether.repository.Authentication;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.util.repository.AuthenticationBuilder;

public class SelfServiceConfigurationManager {

	private static final String DEFAULT_REPOSITORY_URL = "http://central.maven.org/maven2/";
	private static final String DEFAULT_DATABASESERVER_CLASS_IDENTIFIER = "databaseserver";
	private static final String DEFAULT_APPLICATIONSERVER_CLASS_IDENTIFIER = "applicationserver";
	private static final String DEFAULT_ENVIRONMENT_GROUP_IDENTIFIER = "environment";
	private static final String DEFAULT_DATABASE_SCHEMA_IDENTIFIER = "database::";

	private static final String DEFAULT_APPLICATIONS_CONFIGFILE_NAME = "applications.yaml";
	private static final String DEFAULT_PATH_TO_CONFIGFILES = "C:/Users/sebi/workspace/pt-self-service/";
	private static final String DEFAULT_YAML_APPLICATION_SECTION_IDENTIFIER = "APPLICATIONS";

	public static String getApplicationsConfigurationFilename() {
		return DEFAULT_APPLICATIONS_CONFIGFILE_NAME;
	}

	public static String getDefaultConfigurationFilePath() {
		return DEFAULT_PATH_TO_CONFIGFILES;
	}

	public static String getYamlApplicationSectionIdentifier() {
		return DEFAULT_YAML_APPLICATION_SECTION_IDENTIFIER;
	}

	public static String getEnvironmentGroupIdentifier() {
		return DEFAULT_ENVIRONMENT_GROUP_IDENTIFIER;
	}

	public static String getApplicationServerClassIdentifier() {
		return DEFAULT_APPLICATIONSERVER_CLASS_IDENTIFIER;
	}

	public static String getDatabaseServerClassIdentifier() {
		return DEFAULT_DATABASESERVER_CLASS_IDENTIFIER;
	}

	public static String getDatabaseSchemaClassIdentifier() {
		return DEFAULT_DATABASE_SCHEMA_IDENTIFIER;
	}

	public static List<RemoteRepository> getRemoteRepositories() {
		// RemoteRepository nexus = new RemoteRepository.Builder("central",
		// "default", DEFAULT_REPOSITORY_URL).setAuthentication(
		// new AuthenticationBuilder().addUsername("admin")
		// .addPassword("admin123").build()).build();
		return new ArrayList<RemoteRepository>(
				Arrays.asList(new RemoteRepository.Builder("central",
						"default", DEFAULT_REPOSITORY_URL).build()));
	}
}
