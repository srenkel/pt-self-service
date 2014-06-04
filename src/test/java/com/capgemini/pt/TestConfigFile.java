package com.capgemini.pt;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.capgemini.pt.core.data.yaml.applications.App;
import com.capgemini.pt.core.data.yaml.applications.ApplicationConfiguration;
import com.capgemini.pt.core.data.yaml.applications.Inc;

public class TestConfigFile {

	public static void main(String[] args) {
		ApplicationConfiguration appsConfig = new ApplicationConfiguration();
		
		appsConfig.applications = new HashMap<String, App>();
		App app = new App();
		app.groupId = "com.capgemini.tango";
		app.name = "TANGO";
		app.increments = new ArrayList<Inc>();
		Inc inc1 = new Inc();
		inc1.artifactId = "inc1";
		inc1.name = "Increment 1";
		Inc inc2 = new Inc();
		inc2.artifactId = "inc1";
		inc2.name = "Increment 1";
		app.increments.add(inc1);
		app.increments.add(inc2);
		appsConfig.applications.put("App1",app);
		
		Constructor constructor = new Constructor(
				ApplicationConfiguration.class);
		TypeDescription description = new TypeDescription(
				ApplicationConfiguration.class);
		constructor.addTypeDescription(description);

		Yaml yaml = new Yaml(constructor);

		try {
			FileWriter writer = new FileWriter(
					SelfServiceConfigurationManager
							.getDefaultApplicationsFilePath()
							+ SelfServiceConfigurationManager
									.getApplicationsConfigurationFilename());
			yaml.dump(appsConfig, writer);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
