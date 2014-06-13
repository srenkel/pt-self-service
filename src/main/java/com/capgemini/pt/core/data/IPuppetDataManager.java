package com.capgemini.pt.core.data;

import java.util.List;

import com.capgemini.pt.entity.DatabaseSchema;
import com.capgemini.pt.entity.Environment;
import com.capgemini.pt.entity.Server;

public interface IPuppetDataManager {

	public List<Environment> getEnvironments();

	public List<Server> getServerForEnvironment(Environment environment);

	public List<DatabaseSchema> getSchemasForEnvironment(Environment environment);

	public String getLastReportStatusForServer(Server server);
}
