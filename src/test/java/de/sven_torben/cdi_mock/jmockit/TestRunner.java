package de.sven_torben.cdi_mock.jmockit;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public final class TestRunner {
	
	private WeldContainer weldContainer; 
	
	@Before
	public void init() {
		this.weldContainer = new Weld().initialize();
	}
	
	@After
	public void shutdown() {
		this.weldContainer.shutdown();
	}
	
	public <T> T getBean(final Class<T> clazz) {
		return this.weldContainer.instance().select(clazz).get();
	}
	
	@Test
	public void run() {
		final TestBean testBean = this.getBean(TestBean.class);
		testBean.testRealCalculation();
		testBean.testMockedCalculation();
	}

}
