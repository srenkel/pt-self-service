/**
 * Copyright to srenkel 2014
 */
package com.capgemini.pt.dialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.capgemini.pt.SelfServiceApplication;

/**
*
*/
public abstract class BaseView extends WebPage {
	private static final long serialVersionUID = 1L;

	public BaseView() {
		this(new PageParameters());
	}
	
	public BaseView(final PageParameters parameters) {
		super(parameters);
		
		add(new Label("pageTitle", new ResourceModel("label.static.page-title",
				"Deployment Manager")));
		
		FeedbackPanel f = new FeedbackPanel("feedback");
		add(f);
		
		addBookmarkables();
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm");
		add(new Label("footer", SelfServiceApplication.getPortalVersion() + " - "
				+ format.format(Calendar.getInstance().getTime()) + " - "
				+ TimeZone.getDefault().getDisplayName() + " - "
				+ getSession().getLocale().getDisplayLanguage()));
    }
	
	private void addBookmarkables(){
		add(new BookmarkablePageLink<WebPage>("dashboardLink", DashboardView.class));
		add(new BookmarkablePageLink<WebPage>("newProjectLink", DeployView.class));
	}
	
	protected void setPageTitle(IModel<?> model) {
		get("pageTitle").setDefaultModel(model);
	}
}
