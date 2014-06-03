package com.capgemini.pt.core.data;

import java.io.File;
import java.util.List;

import org.eclipse.aether.resolution.VersionRangeResolutionException;

import com.capgemini.pt.entity.Artifact;
import com.capgemini.pt.entity.Version;

public interface IArtifactManager {

	public List<Version> findAvailableArtifactVersions(Artifact artifact)
			throws VersionRangeResolutionException;

	public Version findNewestArtifactVersion(Artifact artifact)
			throws VersionRangeResolutionException;

	public File resolveArtifact(Artifact artifact);

}
