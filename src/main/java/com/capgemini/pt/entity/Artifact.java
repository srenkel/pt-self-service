package com.capgemini.pt.entity;

public class Artifact {

	private String groupId;
	private String artifactId;
	private String version;
	private boolean isWar = true;

	/**
	 * @param groupId
	 * @param artifactId
	 * @param version
	 */
	public Artifact(String groupId, String artifactId, String version) {
		super();
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
		this.isWar = true;
	}

	public boolean isWar() {
		return isWar;
	}

	public void setWar(boolean isWar) {
		this.isWar = isWar;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the artifactId
	 */
	public String getArtifactId() {
		return artifactId;
	}

	/**
	 * @param artifactId
	 *            the artifactId to set
	 */
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

}
