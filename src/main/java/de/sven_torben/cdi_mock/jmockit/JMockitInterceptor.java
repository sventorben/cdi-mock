package de.sven_torben.cdi_mock.jmockit;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import mockit.integration.internal.TestRunnerDecorator;
import mockit.internal.state.SavePoint;
import mockit.internal.state.TestRun;

@Interceptor
@JMockitIntercepted
public class JMockitInterceptor extends TestRunnerDecorator {

	private final ThreadLocal<SavePoint> savePoint = new ThreadLocal<SavePoint>();
	
	@AroundInvoke
	public Object intercept(final InvocationContext ctx) throws Exception {
		this.beforeMethod(ctx);
		final Object result = ctx.proceed();
		this.afterMethod(ctx);
		return result;
	}

	private void afterMethod(final InvocationContext ctx) {
		// TODO Auto-generated method stub
	}

	private void beforeMethod(final InvocationContext ctx) {
		
		TestRun.clearNoMockingZone();

		TestRun.enterNoMockingZone();

		try {
			updateTestClassState(ctx.getTarget(), ctx.getTarget().getClass());
			TestRun.setRunningIndividualTest(ctx.getTarget());
			final SavePoint testMethodSavePoint = new SavePoint();
			savePoint.set(testMethodSavePoint);
			if (shouldPrepareForNextTest) {
				TestRun.prepareForNextTest();
				shouldPrepareForNextTest = false;
			}
			createInstancesForTestedFields(ctx.getTarget(), false);
		} finally {
			TestRun.exitNoMockingZone();
		}

	}

}
