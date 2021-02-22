package com.ztesoft.inf.framework.commons;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.transaction.UserTransaction;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.log4j.Logger;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.SysSet;

public class TransactionProxy implements MethodInterceptor {

	private boolean useXa = false;
	private static Logger logger = Logger.getLogger(TransactionProxy.class);

	public TransactionProxy(boolean useXa) {
		this.useXa = useXa;
	}
	public TransactionProxy() {
		this(false);
	}
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
		throws Throwable {
		if (method.getModifiers() != Modifier.PUBLIC) {
			return proxy.invokeSuper(obj, args);
		}
		boolean commit = true;
		UserTransaction usTran = null;
		try {
			if (useXa) {
				usTran = SysSet.getUserTransaction();
				SysSet.tpBegin(usTran);
			}
			Object result = proxy.invokeSuper(obj, args);
			return result;
		} catch (Exception e) {
			commit = false;
			throw e;
		} finally {
			try {
				if (commit) {
					if (useXa)
						SysSet.tpCommit(usTran);
					else
						ConnectionContext.getContext().allCommit();
				} else {
					if (useXa)
						SysSet.tpRollback(usTran);
					else
						ConnectionContext.getContext().allRollback();
				}
				ConnectionContext.getContext().allCloseConnection();
				if (usTran != null) {
					logger.debug("关闭UserTransaction");
					usTran = null;
				}
			} catch (Exception e) {
				logger.info("关闭数据库连接时，出现异常" + e);
			}
		}
	}
}
