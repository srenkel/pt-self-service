package com.capgemini.pt.core;

import java.util.List;

import com.capgemini.pt.core.data.IArtifactManager;
import com.capgemini.pt.core.data.IConfigurationManager;
import com.capgemini.pt.core.data.IFileManager;
import com.capgemini.pt.core.data.IPuppetDataManager;
import com.capgemini.pt.core.data.ISelfServiceDataManager;
import com.capgemini.pt.core.data.impl.ArtifactManager;
import com.capgemini.pt.core.data.impl.ConfigurationManager;
import com.capgemini.pt.core.data.impl.FileManager;
import com.capgemini.pt.core.data.impl.PuppetAgentManager;
import com.capgemini.pt.core.data.impl.PuppetDataManager;
import com.capgemini.pt.core.data.impl.SelfServiceDataManager;
import com.capgemini.pt.entity.ApplicationStatus;
import com.capgemini.pt.entity.Definition;
import com.capgemini.pt.entity.Server;
import com.capgemini.pt.entity.Status;
import com.capgemini.pt.puppet.IPuppetAgentManager;

public class SelfServiceManager {

	private ISelfServiceDataManager selfServiceDataManager = new SelfServiceDataManager();

	private IArtifactManager artifactManager = new ArtifactManager();

	private IConfigurationManager configurationManager = new ConfigurationManager();

	private IFileManager fileManager = new FileManager();

	private IPuppetAgentManager puppetAgentManager = new PuppetAgentManager();

	/**
	 * Returns a new instance of PuppetDataManager every time. This is because
	 * puppet data is changing in the background.
	 * 
	 * @return A PuppetDataManager instance.
	 */
	public IPuppetDataManager getPuppetDataManager() {
		return new PuppetDataManager();
	}

	public IArtifactManager getArtifactManager() {
		return artifactManager;
	}

	public IConfigurationManager getConfigurationManager() {
		return configurationManager;
	}

	public ISelfServiceDataManager getSelfServiceDataManager() {
		return selfServiceDataManager;
	}

	public IFileManager getFileManager() {
		return fileManager;
	}

	public IPuppetAgentManager getPuppetAgentManager() {
		return puppetAgentManager;
	}

	public boolean deploy(final Definition definitionToDeploy) {
		final ApplicationStatus appStatus = getSelfServiceDataManager()
				.storeApplicationStatus(
						new ApplicationStatus(definitionToDeploy.getEnv()
								.getName(), definitionToDeploy.getBuild()
								.getName(), definitionToDeploy.getApp()
								.getName(), Status.INSTALLING));
		final boolean successConfig = new ConfigurationManager()
				.storePuppetHieraConfigurationForDefinition(definitionToDeploy);
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				List<Server> servers = getPuppetDataManager()
						.getServerForEnvironment(definitionToDeploy.getEnv());
				boolean successPuppetRuns = true;
				if (successConfig) {
					for (int i = 0; i < servers.size(); i++) {
						final Server server = servers.get(i);
						boolean partlySuccess = getPuppetAgentManager()
								.invokePuppetRun(server);
						if (!partlySuccess) {
							successPuppetRuns = false;
						}
					}
					if (successPuppetRuns) {
						appStatus.setStatus(Status.SUCCESFULL);
					} else {
						appStatus.setStatus(Status.FAILURE);
					}
					getSelfServiceDataManager().storeApplicationStatus(
							appStatus);
				}
			};

		});
		t.start();
		return successConfig;
	}
}
