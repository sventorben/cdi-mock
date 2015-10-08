package de.sven_torben.cdi_mock.jmockit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.WithAnnotations;

import org.jboss.weld.util.annotated.AnnotatedTypeWrapper;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.de.Aber;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Gegebensei;
import cucumber.api.java.de.Gegebenseien;
import cucumber.api.java.de.Und;
import cucumber.api.java.de.Wenn;
import cucumber.api.java.en.And;
import cucumber.api.java.en.But;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import mockit.integration.internal.TestRunnerDecorator;

public class CdiJMockitExtension extends TestRunnerDecorator implements Extension {

	public <T> void processAnnotatedTypeForHooks(
			@Observes
			@WithAnnotations( { After.class, Before.class } )
			final ProcessAnnotatedType<T> processAnnotatedType) {
		this.processAnnotatedType(processAnnotatedType);
	}
	
	public <T> void processAnnotatedTypeForEn(
			@Observes 
			@WithAnnotations( { Given.class, When.class, Then.class, And.class, But.class } ) 
			final ProcessAnnotatedType<T> processAnnotatedType) {
		this.processAnnotatedType(processAnnotatedType);
	}
	
	public <T> void processAnnotatedTypeForDe(
			@Observes 
			@WithAnnotations( { Aber.class, Angenommen.class, Dann.class, Gegebensei.class, Gegebenseien.class, Und.class, Wenn.class } ) 
			final ProcessAnnotatedType<T> processAnnotatedType) {
		this.processAnnotatedType(processAnnotatedType);
	}
	
	
	public <T> void processAnnotatedType(final ProcessAnnotatedType<T> processAnnotatedType) {

		final AnnotatedType<T> annotatedType = processAnnotatedType.getAnnotatedType();

		if (!Modifier.isFinal(annotatedType.getJavaClass().getModifiers())
				&& !annotatedType.getJavaClass().equals(JMockitInterceptor.class)) {
			
			final Set<Annotation> annotations = new HashSet<>(annotatedType.getAnnotations());
			annotations.add(new Annotation() {
				@Override
				public Class<? extends Annotation> annotationType() {
					return WithJMockit.class;
				}
			});

			final AnnotatedTypeWrapper<T> wrapper = new AnnotatedTypeWrapper<T>(
					annotatedType, annotations.toArray(new Annotation[annotations.size()]));

			processAnnotatedType.setAnnotatedType(wrapper);
		}
	}
}
