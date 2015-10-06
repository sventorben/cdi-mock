package de.sven_torben.cdi_mock.jmockit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.jboss.weld.util.annotated.AnnotatedTypeWrapper;

import mockit.integration.internal.TestRunnerDecorator;

public class CdiJMockitExtension extends TestRunnerDecorator implements
		Extension {

	public <T> void processAnnotatedType(
			@Observes ProcessAnnotatedType<T> processAnnotatedType) {

		final AnnotatedType<T> annotatedType = processAnnotatedType
				.getAnnotatedType();

		if (!Modifier.isFinal(annotatedType.getJavaClass().getModifiers())
				&& !annotatedType.getJavaClass().equals(JMockitInterceptor.class)) {
			Set<Annotation> annotations = new HashSet<>(
					annotatedType.getAnnotations());
			annotations.add(new Annotation() {
				@Override
				public Class<? extends Annotation> annotationType() {
					return JMockitIntercepted.class;
				}
			});

			final AnnotatedTypeWrapper<T> wrapper = new AnnotatedTypeWrapper<T>(
					annotatedType,
					annotations.toArray(new Annotation[annotations.size()]));

			processAnnotatedType.setAnnotatedType(wrapper);
		}
	}
}
