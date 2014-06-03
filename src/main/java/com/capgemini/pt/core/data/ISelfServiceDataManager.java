package com.capgemini.pt.core.data;

import java.util.List;

import com.capgemini.pt.entity.ApplicationStatus;
import com.capgemini.pt.entity.Definition;

public interface ISelfServiceDataManager {

	public List<Definition> getDefinitions();

	public Definition findDefinition(String name);

	public Definition storeDefinition(Definition definition);

	public List<ApplicationStatus> getApplicationStatus();

	public ApplicationStatus storeApplicationStatus(
			ApplicationStatus applicationStatus);

}
