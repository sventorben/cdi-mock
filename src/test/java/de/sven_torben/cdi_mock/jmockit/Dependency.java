package de.sven_torben.cdi_mock.jmockit;

import javax.inject.Named;

@Named
public class Dependency {
	
	public int multiply(final int a, final int b) {
		return a * b;
	}

}
