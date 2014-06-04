/**
 * Copyright to srenkel 2014
 */
package com.capgemini.pt.dialog;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.capgemini.pt.entity.Application;
import com.capgemini.pt.entity.Artifact;
import com.capgemini.pt.entity.DatabaseSchema;
import com.capgemini.pt.entity.Definition;
import com.capgemini.pt.entity.Environment;
import com.capgemini.pt.entity.Increment;
import com.capgemini.pt.entity.Version;

/**
*
*/
public class DeployView extends BaseView {

	private static final long serialVersionUID = 1L;

	// make Google selected by default
	private Application selectedApplication = null;
	private Increment selectedIncrement = null;
	private Definition selectedDefinition = null;
	private Version selectedVersion = null;
	private Environment selectedEnvironment = null;
	private DatabaseSchema selectedDatabaseSchema = null;

	public DeployView() {
		this(new PageParameters());
	}

	public DeployView(final PageParameters parameters) {
		super(parameters);
		Form<?> form = new Form<Void>("deployForm") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				service.deploy(new Definition("Deployed Definition",
						selectedApplication.getName(), selectedIncrement
								.getName(), selectedVersion.getName(),
						selectedEnvironment.getName(), selectedDatabaseSchema
								.getName()));
				setResponsePage(DashboardView.class);

			}
		};

		add(form);
		form.add(getApplicationDropDown());
		form.add(getIncrementDropDown());
		form.add(getDefinitionDropDown());
		form.add(getBuildDropDown());
		form.add(getEnvironmentDropDown());
		form.add(getDatabaseDropDown());
	}

	private DropDownChoice<Application> getApplicationDropDown() {
		return new DropDownChoice<Application>("applicationDropDown",
				new PropertyModel<Application>(this, "selectedApplication"),
				service.getApplications(), new ChoiceRenderer<Application>(
						"name"));
	}

	private DropDownChoice<Increment> getIncrementDropDown() {
		return new DropDownChoice<Increment>(
				"incrementDropDown",
				new PropertyModel<Increment>(this, "selectedIncrement"),
				service.getIncrementsForApplication(new Application("Testapp")),
				new ChoiceRenderer<Increment>("name"));
	}

	private DropDownChoice<Definition> getDefinitionDropDown() {
		return new DropDownChoice<Definition>("definitionDropDown",
				new PropertyModel<Definition>(this, "selectedDefinition"),
				service.getDefinitions(),
				new ChoiceRenderer<Definition>("name"));
	}

	private DropDownChoice<Version> getBuildDropDown() {
		return new DropDownChoice<Version>("versionDropDown",
				new PropertyModel<Version>(this, "selectedVersion"),
				service.getBuildsForIncrement(new Artifact("hibernate",
						"hibernate-annotations", null)),
				new ChoiceRenderer<Version>("name"));
	}

	private DropDownChoice<Environment> getEnvironmentDropDown() {
		return new DropDownChoice<Environment>("environmentDropDown",
				new PropertyModel<Environment>(this, "selectedEnvironment"),
				service.getEnvironments(), new ChoiceRenderer<Environment>(
						"name"));
	}

	private DropDownChoice<DatabaseSchema> getDatabaseDropDown() {
		return new DropDownChoice<DatabaseSchema>(
				"databaseSchemaDropDown",
				new PropertyModel<DatabaseSchema>(this,
						"selectedDatabaseSchema"),
				service.getSchemasForEnvironment(new Environment("TANGO INT 1")),
				new ChoiceRenderer<DatabaseSchema>("name"));
	}
}
