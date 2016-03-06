package org.dy.springmvc.interceptor;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.MethodProxy;

public class LogInterceptor implements MethodInterceptor {
	private final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

	@Override
	public Object invoke(MethodInvocation arg0) throws Throwable {
		logger.info(" Before LogIntercerptor: ");
		logger.info("Class : " + arg0.getMethod().getDeclaringClass().getClass());
		logger.info("Method : " + arg0.getMethod().getName());

		Object o = null;
		try {
			o = arg0.proceed();
		} catch (Exception e) {
			logger.error("Error method invoke : " + arg0.getMethod().getName(), e);
			throw e;
		}
		logger.info(" End LogIntercerptor.");
		return o;
	}
}
