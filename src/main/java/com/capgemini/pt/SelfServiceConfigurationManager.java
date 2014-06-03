package com.capgemini.pt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.aether.repository.RemoteRepository;

public class SelfServiceConfigurationManager {

	private static final String DEFAULT_REPOSITORY_URL = "http://central.maven.org/maven2/";
	private static final String DEFAULT_DATABASESERVER_CLASS_IDENTIFIER = "databaseserver";
	private static final String DEFAULT_APPLICATIONSERVER_CLASS_IDENTIFIER = "applicationserver";
	private static final String DEFAULT_ENVIRONMENT_GROUP_IDENTIFIER = "environment";
	private static final String DEFAULT_DATABASE_SCHEMA_IDENTIFIER = "database::";

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
		return new ArrayList<RemoteRepository>(
				Arrays.asList(new RemoteRepository.Builder("central",
						"default", DEFAULT_REPOSITORY_URL).build()));
	}

}
