package ru.sendto.ejb;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import lombok.extern.java.Log;
import ru.sendto.dto.Dto;

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
		if(!(result instanceof Dto)){
			log.warning("result is not assignable from Dto.class. It can`t be returned via rest. "
					+ic.getTarget().getClass().getName()
					+"::"
					+ic.getMethod().getName());
			return result;
		} else if(!(request instanceof Dto)){
			log.warning("param[0] is not assignable from Dto.class. Result can`t be returned via rest. "
					+ic.getTarget().getClass().getName()
					+"::"
					+ic.getMethod().getName());
			return result;
		}
		bean.put(((Dto)request), (Dto)result);
		return result;
	}

}
