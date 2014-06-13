/**
 * Copyright to srenkel 2014
 */
package com.capgemini.pt.core.data.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.wicket.ajax.json.JSONArray;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;
import org.eclipse.jetty.util.ajax.JSON;

import com.capgemini.pt.entity.Server;
import com.capgemini.pt.puppet.IPuppetAgentManager;

public class PuppetAgentManager implements IPuppetAgentManager {

	public PuppetAgentManager() {
	}

	@Override
	public boolean invokePuppetRun(Server server) {
		StringBuffer output = new StringBuffer();
		boolean success = false;
		try {
			ProcessBuilder p = new ProcessBuilder("curl", "-k", "--show-error",
					"-X", "PUT", "-H", "Content-Type: text/pson", "-d", "{}",
					"https://" + server.getName()
							+ ":8139/prodcution/run/no_key");

			final Process shell = p.start();
			shell.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					shell.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			
			try {
				JSONObject json = new JSONObject(output.toString());
				success = json.get("status").toString().equals("success") ? true : false;
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return success;

	}

}
