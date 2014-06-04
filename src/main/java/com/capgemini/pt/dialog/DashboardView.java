/**
 * Copyright to srenkel 2014
 */
package com.capgemini.pt.dialog;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.capgemini.pt.entity.ApplicationStatus;

/**
*
*/
public class DashboardView extends BaseView {
	private static final long serialVersionUID = 1L;

	public DashboardView() {
		this(new PageParameters());
	}

	public DashboardView(final PageParameters parameters) {
		super(parameters);
		
		List<ApplicationStatus> list = service.getApplicationStatus();
		ListView<ApplicationStatus> listview = new ListView<ApplicationStatus>(
				"applicationStatusList", list) {
			private static final long serialVersionUID = 1L;

			protected void populateItem(ListItem<ApplicationStatus> item) {
				ApplicationStatus applicationStatus = (ApplicationStatus) item
						.getModelObject();
				item.add(new Label("oid", applicationStatus.getOid().toString()));
				item.add(new Label("createdAt", applicationStatus
						.getCreatedAt().toString()));
				item.add(new Label("environment", applicationStatus
						.getEnvironment().toString()));
				item.add(new Label("build", applicationStatus.getBuild()
						.toString()));
				item.add(new Label("application", applicationStatus
						.getApplication()));
				item.add(new Label("status", applicationStatus.getStatus()
						.toString()));
			}
		};
		add(listview);

	}
}
