/**
 * Copyright to srenkel 2014
 */
package com.capgemini.pt.dialog;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
*
*/
public class DashboardView extends BaseView {
	private static final long serialVersionUID = 1L;

//	@SpringBean
//	private IDashboardDataManager dashboardService;

//	@SpringBean
//	private ICoreDataManager coreService;

	public DashboardView() {
		this(new PageParameters());
	}

	public DashboardView(final PageParameters parameters) {
		super(parameters);
		add(new Label("viewTitle", "Dashboard View!"
//				+ dashboardService.getTestMessage()
				));

//		List<Project> list = coreService.getProjectList();
//		ListView<Project> listview = new ListView<Project>("listview", list) {
//			private static final long serialVersionUID = 1L;
//
//			protected void populateItem(ListItem<Project> item) {
//				Project project = (Project) item.getModelObject();
//				item.add(new Label("uid", project.getOid().toString()));
//				item.add(new Label("status", project.getProjectStatus()
//						.toString()));
//				item.add(new Label("name", project.getProjectName()));
//			}
//		};
//		add(listview);

//		coreService.saveProject(new Project("Full Test Project",
//				ProjectStatus.SUCCESFULL, true, true,
//				DeploymentPattern.MANAGER_DEFAULT, new DashboardGroup(
//						"appsrvGrp"), new DashboardGroup("dbsrvGrp")));

	}
}
