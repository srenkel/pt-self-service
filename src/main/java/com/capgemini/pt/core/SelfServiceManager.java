package com.capgemini.pt.core;

import org.apache.wicket.spring.injection.annot.SpringBean;

import com.capgemini.pt.core.data.IPuppetDataManager;

public class SelfServiceManager {
	
	@SpringBean
	private IPuppetDataManager puppetDataManager;

}
