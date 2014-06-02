/**
 * Copyright to srenkel 2014
 */
package com.capgemini.pt.dialog;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
*
*/
public class DeployView extends BaseView {
	private static final long serialVersionUID = 1L;

	public DeployView() {
		this(new PageParameters());
	}
	
	public DeployView(final PageParameters parameters) {
		super(parameters);
		add(new Label("viewTitle", "New Project View!"));
    }
}
