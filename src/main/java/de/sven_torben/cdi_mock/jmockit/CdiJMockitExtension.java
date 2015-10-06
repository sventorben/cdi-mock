package de.sven_torben.cdi_mock.jmockit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessBean;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import javax.enterprise.inject.spi.ProcessManagedBean;

import org.jboss.weld.util.annotated.AnnotatedTypeWrapper;

import mockit.integration.internal.TestRunnerDecorator;
import mockit.integration.junit4.JMockit;
import mockit.internal.state.SavePoint;
import mockit.internal.state.TestRun;

public class CdiJMockitExtension extends TestRunnerDecorator implements
		Extension {

	private final ThreadLocal<SavePoint> savePoint = new ThreadLocal<SavePoint>();

	/*
	public <T> void processInjectionTarget(
			@Observes final ProcessInjectionTarget<T> pit) {

		final InjectionTarget<T> it = pit.getInjectionTarget();
		final Class<T> baseType = pit.getAnnotatedType().getJavaClass();
		final InjectionTarget<T> wrapped = new InjectionTargetWrapper<T>(it) {

			@Override
			public T produce(final CreationalContext<T> ctx) {

				final T instance = it.produce(ctx);

				TestRun.clearNoMockingZone();

				TestRun.enterNoMockingZone();

				try {
					updateTestClassState(instance, baseType);
					TestRun.setRunningIndividualTest(instance);
					final SavePoint testMethodSavePoint = new SavePoint();
					savePoint.set(testMethodSavePoint);
					if (shouldPrepareForNextTest) {
						TestRun.prepareForNextTest();
						shouldPrepareForNextTest = false;
					}
					createInstancesForTestedFields(instance, false);
				} finally {
					TestRun.exitNoMockingZone();
				}

				return instance;
			}
		};
		

		pit.setInjectionTarget(wrapped);
	}
	*/
	
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
