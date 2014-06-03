/**
 * Copyright to srenkel 2014
 */
package com.capgemini.pt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.rest.utils.mounting.PackageScanner;

import com.capgemini.pt.dialog.DashboardView;
import com.capgemini.pt.dialog.DeployView;
import com.capgemini.pt.service.IServiceManager;
import com.capgemini.pt.service.ServiceManager;

/**
 *
 */
public class SelfServiceApplication extends WebApplication {

	private static final Properties envProperties = new Properties();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends Page> getHomePage() {
		return DeployView.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init() {
		super.init();

		// getComponentInstantiationListeners().add(
		// new SpringComponentInjector(this));

		// Init configuration file
		final InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("configuration.properties");
		try {
			envProperties.load(in);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}

		// Set extended browser info gathering
		// getRequestCycleSettings().setGatherExtendedBrowserInfo(true);

		// Remove wicket tags from output
		getMarkupSettings().setStripWicketTags(true);

		// Set the default locale to english
		Locale.setDefault(Locale.ENGLISH);

		// // Set the default pages
		// getApplicationSettings().setPageExpiredErrorPage(PageExpiredPage.class);
		// getApplicationSettings().setAccessDeniedPage(AccessDeniedPage.class);
		// getApplicationSettings().setInternalErrorPage(ErrorPage.class);

		// Uncomment to activate request logger
		// Application.get().getRequestLoggerSettings().setRequestLoggerEnabled(true);
		// Application.get().getRequestLoggerSettings().setRequestsWindowSize(3000);

		// Uncomment to remove developer error page
		// getExceptionSettings().setUnexpectedExceptionDisplay(IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE);

		// REST
		PackageScanner.scanPackage("com.capgemini.pt");
		// mountResource("/rest",
		// new ResourceReference("restReference") {
		//
		// private static final long serialVersionUID = 1L;
		//
		// private ServiceManager resource = new ServiceManager();
		//
		// @Override
		// public IResource getResource() {
		// return resource;
		// }
		// });

		// add your configuration here
		initPageMounts();
	}

	private void initPageMounts() {
		mountPage("dashboard", DashboardView.class);
		mountPage("deploy", DeployView.class);
	}

	public static String getPortalVersion() {
		return envProperties.getProperty("portal.version");
	}

}
