package de.sven_torben.cdi_mock.jmockit;

import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;

class InjectionTargetWrapper<T> implements InjectionTarget<T> {
	
	private final InjectionTarget<T> injectionTarget;
	
	public InjectionTargetWrapper(final InjectionTarget<T> injectionTarget) {
		this.injectionTarget = injectionTarget;
	}

	public T produce(final CreationalContext<T> ctx) {
		return this.injectionTarget.produce(ctx);
	}

	public void dispose(final T instance) {
		this.injectionTarget.dispose(instance);
	}

	public Set<InjectionPoint> getInjectionPoints() {
		return this.injectionTarget.getInjectionPoints();
	}

	public void inject(final T instance, final CreationalContext<T> ctx) {
		this.injectionTarget.inject(instance, ctx);
	}

	public void postConstruct(final T instance) {
		this.injectionTarget.postConstruct(instance);
	}

	public void preDestroy(final T instance) {
		this.injectionTarget.preDestroy(instance);
	}

}
