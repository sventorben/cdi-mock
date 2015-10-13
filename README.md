# cdi-mock
<aside>Note: This is more a little experiment than an actual project!</aside>

This project provides a CDI extension and Interceptor to support integration of [JMockit]{https://github.com/jmockit/jmockit1} with [Cucumber-JVM]{https://github.com/cucumber/cucumber-jv} when [Cucumber-Weld]{https://github.com/cucumber/cucumber-jvm/tree/master/weld} is used as dependency injection (DI) container.

## CDI extension
To use the CDI extension register the extension as a service provider by creating a file named _META-INF/services/javax.enterprise.inject.spi.Extension_, which contains the name of the extension class:

```
de.sven_torben.cdi_mock.jmockit.CdiJMockitExtension
```

The extension will register an interceptor _(JMockitInterceptor)_ to all step definitions (glue code) which provides the integration between JMockit, Cucumber and Weld.

## Interceptor
Alternatively, e.g. in case you do not want to use the CDI extension, you may want to use the interceptor directly. Therfore, simply annotate your step definitions (glue code) with _@WithJMockit_ annotation like so:

```Java
import cucumber.api.java.en.Given;
import de.sven_torben.cdi_mock.jmockit;

@WithJMockit
public class Stepdefs {
	
	@Given("^I have (\\d+) cukes in my belly$")
    public void I_have_cukes_in_my_belly(int cukes) throws Throwable {
		// glue code
    }

}
```
