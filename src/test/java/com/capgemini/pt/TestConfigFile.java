package com.capgemini.pt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;

import com.capgemini.pt.core.data.yaml.base.BaseConfiguration;

public class TestConfigFile {

	public static void main(String[] args) {
		Constructor constructor = new Constructor(BaseConfiguration.class);
		TypeDescription description = new TypeDescription(
				BaseConfiguration.class);
		constructor.addTypeDescription(description);

		constructor.addTypeDescription(description);
		Yaml yaml = new Yaml(constructor);
		// Yaml y = new Yaml(new CustomClassLoaderConstructor(
		// TestConfigFile.class.getClassLoader()));

		try {
			FileInputStream stream = new FileInputStream(new File(
					SelfServiceConfigurationManager
							.getDefaultConfigurationFilePath()
							+ SelfServiceConfigurationManager
									.getSelfServiceConfigurationFilename()) + ".yml");

			BaseConfiguration base = (BaseConfiguration) yaml.load(stream);

			System.out.println("test:" + base.identifier.applicationserver);
//			try {
//				FileWriter writer = new FileWriter(
//						SelfServiceConfigurationManager
//								.getDefaultConfigurationFilePath()
//								+ SelfServiceConfigurationManager
//										.getSelfServiceConfigurationFilename()
//								+ ".yml");
//				yaml.dump(base, writer);
//				writer.close();
//
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
