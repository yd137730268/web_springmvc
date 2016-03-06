package org.dy.springmvc.interceptor;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;



@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})
public class DiclectStatementHandlerInterceptor implements Interceptor{

	@Override
	public Object intercept(Invocation arg0) throws Throwable {
		RoutingStatementHandler statement = (RoutingStatementHandler) arg0.getTarget();
		//TODO
		return arg0.proceed();
	}

	@Override
	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	@Override
	public void setProperties(Properties arg0) {
	}

}
