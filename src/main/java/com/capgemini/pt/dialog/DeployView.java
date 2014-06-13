/**
 * Copyright to srenkel 2014
 */
package com.capgemini.pt.dialog;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
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

		final Form<?> form = new Form<Void>("deployForm") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				service.deploy(new Definition("Deployed Definition",
						selectedApplication, selectedIncrement,
						selectedVersion, selectedEnvironment,
						selectedDatabaseSchema));
				setResponsePage(DashboardView.class);

			}
		};

		add(form);

		final DropDownChoice<Application> applicationDropDown = getApplicationDropDown();
		form.add(applicationDropDown.setOutputMarkupId(true));

		final DropDownChoice<Increment> incrementDropDown = getIncrementDropDown();
		form.add(incrementDropDown.setEnabled(false).setOutputMarkupId(true));

		final DropDownChoice<Version> buildDropDown = getBuildDropDown();
		form.add(buildDropDown.setEnabled(false).setOutputMarkupId(true));

		final DropDownChoice<Environment> environmentDropDown = getEnvironmentDropDown();
		form.add(environmentDropDown.setEnabled(false).setOutputMarkupId(true));

		final DropDownChoice<DatabaseSchema> databaseDropDown = getDatabaseDropDown();
		form.add(databaseDropDown.setEnabled(false).setOutputMarkupId(true));

		form.add(getDefinitionDropDown().setOutputMarkupId(true));

		applicationDropDown.add(new OnChangeAjaxBehavior() {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				selectedApplication = (Application) getComponent()
						.getDefaultModelObject();
				selectedIncrement = null;
				selectedVersion = null;
				selectedEnvironment = null;
				selectedDatabaseSchema = null;
				incrementDropDown.setEnabled(true);
				incrementDropDown.setChoices(service
						.getIncrementsForApplication(selectedApplication));
				target.add(incrementDropDown);
				target.add(buildDropDown.setEnabled(false));
				target.add(environmentDropDown.setEnabled(false));
				target.add(databaseDropDown.setEnabled(false));
			}
		});

		incrementDropDown.add(new OnChangeAjaxBehavior() {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				selectedIncrement = (Increment) getComponent()
						.getDefaultModelObject();
				buildDropDown.setEnabled(true);
				environmentDropDown.setEnabled(true);
				List<Version> versions = service
						.getBuildsForIncrement(new Artifact(selectedApplication
								.getGroupId(), selectedIncrement
								.getArtifactId(), ""));
				buildDropDown.setChoices(versions);
				selectedVersion = null;
				selectedEnvironment = null;
				selectedDatabaseSchema = null;
				target.add(databaseDropDown.setEnabled(false));
				target.add(buildDropDown);
				target.add(environmentDropDown);
			}
		});

		environmentDropDown.add(new OnChangeAjaxBehavior() {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				selectedEnvironment = (Environment) getComponent()
						.getDefaultModelObject();
				databaseDropDown.setEnabled(true);
				databaseDropDown.setChoices(service
						.getSchemasForEnvironment(selectedEnvironment));
				target.add(databaseDropDown);

			}
		});

	}

	private DropDownChoice<Application> getApplicationDropDown() {
		return new DropDownChoice<Application>("applicationDropDown",
				new PropertyModel<Application>(this, "selectedApplication"),
				service.getApplications(), new ChoiceRenderer<Application>(
						"name"));
	}

	private DropDownChoice<Increment> getIncrementDropDown() {
		if (selectedApplication == null) {
			return new DropDownChoice<Increment>("incrementDropDown",
					new PropertyModel<Increment>(this, "selectedIncrement"),
					new ArrayList<Increment>(), new ChoiceRenderer<Increment>(
							"name"));
		} else {
			return new DropDownChoice<Increment>("incrementDropDown",
					new PropertyModel<Increment>(this, "selectedIncrement"),
					service.getIncrementsForApplication(selectedApplication),
					new ChoiceRenderer<Increment>("name"));
		}
	}

	private DropDownChoice<Definition> getDefinitionDropDown() {
		return new DropDownChoice<Definition>("definitionDropDown",
				new PropertyModel<Definition>(this, "selectedDefinition"),
				service.getDefinitions(),
				new ChoiceRenderer<Definition>("name"));
	}

	private DropDownChoice<Version> getBuildDropDown() {
		List<Version> versions = new ArrayList<Version>();
		return new DropDownChoice<Version>("versionDropDown",
				new PropertyModel<Version>(this, "selectedVersion"), versions,
				new ChoiceRenderer<Version>("name"));
	}

	private DropDownChoice<Environment> getEnvironmentDropDown() {
		return new DropDownChoice<Environment>("environmentDropDown",
				new PropertyModel<Environment>(this, "selectedEnvironment"),
				service.getEnvironments(), new ChoiceRenderer<Environment>(
						"name"));
	}

	private DropDownChoice<DatabaseSchema> getDatabaseDropDown() {
		return new DropDownChoice<DatabaseSchema>("databaseSchemaDropDown",
				new PropertyModel<DatabaseSchema>(this,
						"selectedDatabaseSchema"),
				new ArrayList<DatabaseSchema>(),
				new ChoiceRenderer<DatabaseSchema>("fullName"));
	}
}
