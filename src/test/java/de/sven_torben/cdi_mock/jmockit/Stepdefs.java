package de.sven_torben.cdi_mock.jmockit;

import javax.inject.Inject;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class Stepdefs {

	@Inject
	private TestBean bean;
	
	@Given("^I have (\\d+) cukes in my belly$")
    public void I_have_cukes_in_my_belly(int cukes) throws Throwable {
        bean.testRealCalculation();
    }
	
	@When("^I wait (\\d+) hour$")
    public void I_wait_one_hour(int cukes) throws Throwable {
        bean.testMockedCalculation();
    }
}
