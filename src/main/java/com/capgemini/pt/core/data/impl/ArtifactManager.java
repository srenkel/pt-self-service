/**
 * Copyright to srenkel 2014
 */
package com.capgemini.pt.core.data.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.VersionRangeRequest;
import org.eclipse.aether.resolution.VersionRangeResolutionException;
import org.eclipse.aether.resolution.VersionRangeResult;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transport.file.FileTransporterFactory;
import org.eclipse.aether.transport.http.HttpTransporterFactory;
import org.eclipse.aether.version.Version;

import com.capgemini.pt.SelfServiceApplication;
import com.capgemini.pt.SelfServiceConfigurationManager;
import com.capgemini.pt.core.data.IArtifactManager;
import com.capgemini.pt.core.data.aether.ConsoleRepositoryListener;
//import com.capgemini.pt.entity.Artifact;
//import com.capgemini.pt.entity.Version;
import com.capgemini.pt.core.data.aether.ConsoleTransferListener;

public class ArtifactManager implements IArtifactManager {

	public ArtifactManager() {
	}

	private RepositorySystem getRepositorySystem() {
		/*
		 * Aether's components implement org.eclipse.aether.spi.locator.Service
		 * to ease manual wiring and using the prepopulated
		 * DefaultServiceLocator, we only need to register the repository
		 * connector and transporter factories.
		 */
		DefaultServiceLocator locator = MavenRepositorySystemUtils
				.newServiceLocator();
		locator.addService(RepositoryConnectorFactory.class,
				BasicRepositoryConnectorFactory.class);
		locator.addService(TransporterFactory.class,
				FileTransporterFactory.class);
		locator.addService(TransporterFactory.class,
				HttpTransporterFactory.class);

		locator.setErrorHandler(new DefaultServiceLocator.ErrorHandler() {
			@Override
			public void serviceCreationFailed(Class<?> type, Class<?> impl,
					Throwable exception) {
				exception.printStackTrace();
			}
		});

		RepositorySystem system = locator.getService(RepositorySystem.class);
		return system;
	}

	private DefaultRepositorySystemSession getRepositorySession() {

		DefaultRepositorySystemSession session = MavenRepositorySystemUtils
				.newSession();

		LocalRepository localRepo = new LocalRepository("target/local-repo");
		session.setLocalRepositoryManager(getRepositorySystem()
				.newLocalRepositoryManager(session, localRepo));

		session.setTransferListener(new ConsoleTransferListener());
		session.setRepositoryListener(new ConsoleRepositoryListener());

		return session;
	}

	@Override
	public List<com.capgemini.pt.entity.Version> findAvailableArtifactVersions(
			com.capgemini.pt.entity.Artifact artifact) {

		System.out
				.println("------------------------------------------------------------");

		// uncomment to generate dirty trees
		// session.setDependencyGraphTransformer( null );

		// Artifact art = new DefaultArtifact(
		// "org.hibernate:hibernate-annotations:[0,)");

		Artifact art = new DefaultArtifact(artifact.getGroupId() + ":"
				+ artifact.getArtifactId() + ":[0,)");

		VersionRangeRequest rangeRequest = new VersionRangeRequest();
		rangeRequest.setArtifact(art);
		rangeRequest.setRepositories(SelfServiceConfigurationManager.getRemoteRepositories());

		VersionRangeResult rangeResult;
		// ClassLoader loader = SelfServiceApplication.class.getClassLoader();
		// System.out.println(loader
		// .getResource("org/slf4j/spi/LocationAwareLogger.class"));
		List<com.capgemini.pt.entity.Version> outputVersions = new ArrayList<com.capgemini.pt.entity.Version>();
		try {
			rangeResult = getRepositorySystem().resolveVersionRange(
					getRepositorySession(), rangeRequest);
			List<Version> versions = rangeResult.getVersions();

			for (int i = 0; i < versions.size(); i++) {
				Version vers = versions.get(i);
				outputVersions.add(new com.capgemini.pt.entity.Version(vers
						.toString()));
			}
			System.out.println("Available versions " + versions);
		} catch (VersionRangeResolutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outputVersions;
	}

	@Override
	public com.capgemini.pt.entity.Version findNewestArtifactVersion(
			com.capgemini.pt.entity.Artifact artifact) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File resolveArtifact(com.capgemini.pt.entity.Artifact artifact) {
		// TODO Auto-generated method stub
		return null;
	}

}
