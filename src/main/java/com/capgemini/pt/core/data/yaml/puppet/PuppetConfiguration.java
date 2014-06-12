package com.capgemini.pt.core.data.yaml.puppet;

import java.util.List;
import java.util.Map;

public class PuppetConfiguration {

	public List<String> role;
//	public Map<String, Instance> instances;
	public Map<String, Webapp> maven_webapps;
	public Map<String, JNDIDatabase> jndi_databases;
}
