/**
 * Copyright to srenkel 2014
 */
package com.capgemini.pt.dialog;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.hibernate.event.internal.OnUpdateVisitor;

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

	private Application selectedApplication = null;
	private Increment selectedIncrement = null;
	private Definition selectedDefinition = null;
	private Version selectedVersion = null;
	private Environment selectedEnvironment = null;
	private DatabaseSchema selectedDatabaseSchema = null;

	public DeployView() {
		this(new PageParameters());
	}

	public DeployView(Definition def) {
		this.selectedDefinition = def;
		this.selectedApplication = def.getApp();
		this.selectedDatabaseSchema = def.getDbschema();
		this.selectedEnvironment = def.getEnv();
		this.selectedIncrement = def.getInc();
		this.selectedVersion = def.getBuild();
		initContent(true);
	}

	public DeployView(final PageParameters parameters) {
		super(parameters);
		initContent(false);
	}

	private void initContent(boolean isDefinitionLoaded) {
		final DropDownChoice<Application> applicationDropDown = getApplicationDropDown();
		final DropDownChoice<Increment> incrementDropDown = getIncrementDropDown();
		final DropDownChoice<Version> buildDropDown = getBuildDropDown();
		final DropDownChoice<Environment> environmentDropDown = getEnvironmentDropDown();
		final DropDownChoice<DatabaseSchema> databaseDropDown = getDatabaseDropDown();

		final Form<?> form = new Form<Void>("deployForm") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				boolean error = false;
				if (selectedApplication == null) {
					applicationDropDown.error("Choose application first!");
					applicationDropDown.add(new AttributeAppender("class",
							new Model<String>("error"), " "));
					error = true;
				}
				if (selectedIncrement == null) {
					incrementDropDown.error("Choose increment first!");
					incrementDropDown.add(new AttributeAppender("class",
							new Model<String>("error"), " "));
					error = true;
				}
				if (selectedVersion == null
						|| buildDropDown.getChoices().size() == 1) {
					if (buildDropDown.getChoices().size() == 1) {
						buildDropDown
								.error("There are no version available. Deploy not possible!");
					} else {
						buildDropDown.error("Choose version first!");
					}
					buildDropDown.add(new AttributeAppender("class",
							new Model<String>("error"), " "));
					error = true;
				}
				if (selectedEnvironment == null) {
					environmentDropDown.error("Choose environment first!");
					environmentDropDown.add(new AttributeAppender("class",
							new Model<String>("error"), " "));
					error = true;
				}
				if (selectedDatabaseSchema == null) {
					databaseDropDown.error("Choose database schema first!");
					databaseDropDown.add(new AttributeAppender("class",
							new Model<String>("error"), " "));
					error = true;
				}
				if (error) {
					return;
				}
				service.deploy(new Definition("Deployed Definition",
						selectedApplication, selectedIncrement,
						selectedVersion, selectedEnvironment,
						selectedDatabaseSchema));
				setResponsePage(DashboardView.class);

			}
		};

		form.setOutputMarkupId(true);
		add(form);

		final Label loadingLabel = new Label("loadingLabel", new Model<String>(
				""));
		loadingLabel.setOutputMarkupId(true);
		form.add(loadingLabel);

		form.add(applicationDropDown.setOutputMarkupId(true));
		form.add(incrementDropDown.setEnabled(false || isDefinitionLoaded)
				.setOutputMarkupId(true));
		form.add(buildDropDown.setEnabled(false || isDefinitionLoaded)
				.setOutputMarkupId(true));
		form.add(environmentDropDown.setEnabled(false || isDefinitionLoaded)
				.setOutputMarkupId(true));
		form.add(databaseDropDown.setEnabled(false || isDefinitionLoaded)
				.setOutputMarkupId(true));
		form.add(getDefinitionDropDown().setOutputMarkupId(true));

		form.add(new AjaxSubmitLink("loadButton", form) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (selectedDefinition == null) {
					error("No definition selected!");
					target.add(get("feedback"));
				} else {
					info("Definition loaded!");
					setResponsePage(new DeployView(selectedDefinition));
				}
			}
		});

		form.add(new SubmitLink("saveButton", form) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				info("save");
				boolean error = false;
				if (selectedApplication == null || selectedIncrement == null
						|| selectedVersion == null
						|| selectedEnvironment == null
						|| selectedDatabaseSchema == null) {
					error = true;
				}
				if (error) {
					form.error("Not all parameters selected!");
				} else {
					form.info("saved def");
					service.saveDefinition(new Definition("A new definition",
							selectedApplication, selectedIncrement,
							selectedVersion, selectedEnvironment,
							selectedDatabaseSchema));
				}

			}
		});

		applicationDropDown.add(new OnChangeAjaxBehavior() {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				selectedApplication = (Application) getComponent()
						.getDefaultModelObject();
				if (getComponent().getDefaultModelObject() != null) {
					getComponent().add(new AttributeModifier("class", "ok"));
				}
				selectedIncrement = null;
				selectedVersion = null;
				selectedEnvironment = null;
				selectedDatabaseSchema = null;
				incrementDropDown.setEnabled(true);
				incrementDropDown.setChoices(service
						.getIncrementsForApplication(selectedApplication));
				target.add(incrementDropDown);
				target.add(buildDropDown.setEnabled(false).add(
						new AttributeModifier("class", "")));
				target.add(environmentDropDown.setEnabled(false).add(
						new AttributeModifier("class", "")));
				target.add(databaseDropDown.setEnabled(false).add(
						new AttributeModifier("class", "")));
				target.add(getComponent());
			}
		});

		incrementDropDown.add(new OnChangeAjaxBehavior() {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(buildDropDown.setEnabled(false).add(
						new AttributeModifier("class", "")));
				target.add(environmentDropDown.setEnabled(false).add(
						new AttributeModifier("class", "")));
				target.add(databaseDropDown.setEnabled(false).add(
						new AttributeModifier("class", "")));
				loadingLabel.setDefaultModel(new Model<String>(
						"Loading versions from repo.."));
				target.add(loadingLabel);
			}
		});

		incrementDropDown.add(new OnChangeAjaxBehavior() {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				selectedIncrement = (Increment) getComponent()
						.getDefaultModelObject();
				if (getComponent().getDefaultModelObject() != null) {
					getComponent().add(new AttributeModifier("class", "ok"));
				}
				selectedVersion = null;
				selectedEnvironment = null;
				selectedDatabaseSchema = null;
				target.add(getComponent());
				List<Version> versions = service
						.getBuildsForIncrement(new Artifact(selectedApplication
								.getGroupId(), selectedIncrement
								.getArtifactId(), ""));
				buildDropDown.setChoices(versions);
				buildDropDown.setEnabled(true);
				environmentDropDown.setEnabled(true);
				target.add(databaseDropDown.setEnabled(false).add(
						new AttributeModifier("class", "")));
				target.add(buildDropDown);
				target.add(environmentDropDown);
				loadingLabel.setDefaultModel(new Model<String>(""));
				target.add(loadingLabel);
			}
		});

		environmentDropDown.add(new OnChangeAjaxBehavior() {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				selectedEnvironment = (Environment) getComponent()
						.getDefaultModelObject();
				selectedDatabaseSchema = null;
				if (getComponent().getDefaultModelObject() != null) {
					getComponent().add(new AttributeModifier("class", "ok"));
				}
				databaseDropDown.setEnabled(true).add(
						new AttributeModifier("class", ""));
				databaseDropDown.setChoices(service
						.getSchemasForEnvironment(selectedEnvironment));
				target.add(databaseDropDown);
				target.add(getComponent());
			}
		});

		buildDropDown.add(new OnChangeAjaxBehavior() {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				selectedVersion = (Version) getComponent()
						.getDefaultModelObject();
				if (getComponent().getDefaultModelObject() != null) {
					getComponent().add(new AttributeModifier("class", "ok"));
				}
				target.add(getComponent());
			}
		});

		databaseDropDown.add(new OnChangeAjaxBehavior() {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				selectedDatabaseSchema = (DatabaseSchema) getComponent()
						.getDefaultModelObject();
				if (getComponent().getDefaultModelObject() != null) {
					getComponent().add(new AttributeModifier("class", "ok"));
				}
				target.add(getComponent());
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
		List<Version> versions;
		if (selectedApplication != null && selectedIncrement != null) {
			versions = service.getBuildsForIncrement(new Artifact(
					selectedApplication.getGroupId(), selectedIncrement
							.getArtifactId(), ""));
		} else {
			versions = new ArrayList<Version>();
		}

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
		List<DatabaseSchema> databaseSchemas;
		if (selectedEnvironment != null) {
			databaseSchemas = service
					.getSchemasForEnvironment(selectedEnvironment);
		} else {
			databaseSchemas = new ArrayList<DatabaseSchema>();
		}
		return new DropDownChoice<DatabaseSchema>("databaseSchemaDropDown",
				new PropertyModel<DatabaseSchema>(this,
						"selectedDatabaseSchema"), databaseSchemas,
				new ChoiceRenderer<DatabaseSchema>("fullName"));
	}
}
