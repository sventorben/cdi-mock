package de.sven_torben.cdi_mock.jmockit;


import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@Test
@CucumberOptions(plugin = {"pretty"})
public final class TestNGRunner extends AbstractTestNGCucumberTests {
}
