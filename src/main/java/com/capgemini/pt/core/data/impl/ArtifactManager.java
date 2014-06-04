/**
 * Copyright to srenkel 2014
 */
package com.capgemini.pt.core.data.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.resolution.VersionRangeRequest;
import org.eclipse.aether.resolution.VersionRangeResolutionException;
import org.eclipse.aether.resolution.VersionRangeResult;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transport.file.FileTransporterFactory;
import org.eclipse.aether.transport.http.HttpTransporterFactory;
import org.eclipse.aether.version.Version;

import com.capgemini.pt.SelfServiceConfigurationManager;
import com.capgemini.pt.core.data.IArtifactManager;
//import com.capgemini.pt.entity.Artifact;
//import com.capgemini.pt.entity.Version;

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

		// uncomment for console logging
		// session.setTransferListener(new ConsoleTransferListener());
		// session.setRepositoryListener(new ConsoleRepositoryListener());

		return session;
	}

	private VersionRangeResult getVersionRangeResult(
			com.capgemini.pt.entity.Artifact artifact)
			throws VersionRangeResolutionException {
		RepositorySystem system = getRepositorySystem();

		RepositorySystemSession session = getRepositorySession();

		Artifact art = new DefaultArtifact(artifact.getGroupId() + ":"
				+ artifact.getArtifactId() + ":[0,)");

		VersionRangeRequest rangeRequest = new VersionRangeRequest();
		rangeRequest.setArtifact(art);
		rangeRequest.setRepositories(SelfServiceConfigurationManager
				.getRemoteRepositories());

		return system.resolveVersionRange(session, rangeRequest);
	}

	@Override
	public List<com.capgemini.pt.entity.Version> findAvailableArtifactVersions(
			com.capgemini.pt.entity.Artifact artifact)
			throws VersionRangeResolutionException {
		List<com.capgemini.pt.entity.Version> outputVersions = new ArrayList<com.capgemini.pt.entity.Version>();
		VersionRangeResult rangeResult = getVersionRangeResult(artifact);
		List<Version> versions = rangeResult.getVersions();

		for (int i = 0; i < versions.size(); i++) {
			Version vers = versions.get(i);
			outputVersions.add(new com.capgemini.pt.entity.Version(vers
					.toString()));
		}
		return outputVersions;
	}

	@Override
	public com.capgemini.pt.entity.Version findNewestArtifactVersion(
			com.capgemini.pt.entity.Artifact artifact)
			throws VersionRangeResolutionException {

		com.capgemini.pt.entity.Version outputVersion = null;
		VersionRangeResult rangeResult = getVersionRangeResult(artifact);

		outputVersion = new com.capgemini.pt.entity.Version(rangeResult
				.getHighestVersion().toString());
		return outputVersion;
	}

	@Override
	public File resolveArtifact(com.capgemini.pt.entity.Artifact artifact) {
		RepositorySystem system = getRepositorySystem();

		RepositorySystemSession session = getRepositorySession();

		Artifact art = new DefaultArtifact(artifact.getGroupId() + ":"
				+ artifact.getArtifactId() + ":" + (artifact.isWar() == true ? "war:" : "") + artifact.getVersion());

		art.setFile(new File("target/artifact"));
		ArtifactRequest artifactRequest = new ArtifactRequest();
		artifactRequest.setArtifact(art);
		artifactRequest.setRepositories(SelfServiceConfigurationManager
				.getRemoteRepositories());

		ArtifactResult artifactResult;
		try {
			artifactResult = system.resolveArtifact(session, artifactRequest);
			art = artifactResult.getArtifact();

			System.out.println(artifact + " resolved to  " + art.getFile());

			return art.getFile();
		} catch (ArtifactResolutionException e) {
			e.printStackTrace();
			return null;
		}

	}

}
