package com.capgemini.pt;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import com.capgemini.pt.dialog.DeployView;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage {
	private WicketTester tester;

	@Before
	public void setUp() {
		tester = new WicketTester(new SelfServiceApplication());
	}

	@Test
	public void homepageRendersSuccessfully() {
		// start and render the test page
		tester.startPage(DeployView.class);

		// assert rendered page class
		tester.assertRenderedPage(DeployView.class);
	}
}
