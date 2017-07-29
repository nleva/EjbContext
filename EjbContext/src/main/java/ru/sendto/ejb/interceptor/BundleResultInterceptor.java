package ru.sendto.ejb.interceptor;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import lombok.extern.java.Log;
import ru.sendto.dto.Dto;
import ru.sendto.ejb.EventResultsBean;

@Log
@BundleResult
@Interceptor
@Priority(2017)
public class BundleResultInterceptor {

	@Inject
	EventResultsBean bean;

	@AroundInvoke
	public Object bundle(InvocationContext ic) throws Exception {
		final Object request = ic.getParameters()[0];
		Object result = ic.proceed();
		if (!(result instanceof Dto)) {
			log.fine("result is not assignable from Dto.class. It can`t be returned via rest. "
					+ ic.getTarget().getClass().getName()
					+ "::"
					+ ic.getMethod().getName());
			return result;
		}
		if (!(request instanceof Dto)) {
			log.fine("param[0] is not assignable from Dto.class. Result can`t be returned via rest. "
					+ ic.getTarget().getClass().getName()
					+ "::"
					+ ic.getMethod().getName());
			return result;
		}
		bean.put(((Dto) request), (Dto) result);
		return result;
	}

}
