package de.sven_torben.cdi_mock.jmockit;

import javax.inject.Inject;
import javax.inject.Named;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Named
public class TestBean {
	
	@Mocked
	private Dependency mockedDependency;
	
	@Inject
	private Dependency realDependency;

	public void testRealCalculation() {
		int result = this.realDependency.multiply(2, 3);
		assertThat(result, is(6));
	}

	public void testMockedCalculation() {
		new Expectations() {{
			mockedDependency.multiply(2, 3);
			times = 1;
			result = 9999;
		}};
		
		int result = this.mockedDependency.multiply(2, 3);
		assertThat(result, is(9999));
	}
}
