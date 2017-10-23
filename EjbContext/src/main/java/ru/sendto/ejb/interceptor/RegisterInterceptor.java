package ru.sendto.ejb.interceptor;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import lombok.extern.java.Log;
import ru.sendto.dto.Dto;
import ru.sendto.ejb.SingleRequestEventResultsBean;

@Log
@BundleResult
@Interceptor
@Priority(2017)
public class RegisterInterceptor {

//	@Inject
//	EventResultsBean bean;
	@Inject
	SingleRequestEventResultsBean plain;

	@AroundInvoke
	public Object bundle(InvocationContext ic) throws Exception {
		final Object request = ic.getParameters()[0];
		Object result = ic.proceed();
		
		if(result==null) {
			return null;
		}
		if (!(request instanceof Dto)) {
			log.fine("param[0] is not assignable from Dto.class. Result can`t be returned via rest. "
					+ ic.getTarget().getClass().getName()
					+ "::"
					+ ic.getMethod().getName());
			return result;
		}
		if(Collection.class.isAssignableFrom(ic.getMethod().getReturnType())) {
			return putListToResults(request, result);
			
		}
		if(ic.getMethod().getReturnType().isArray() 
				&& Collection.class.isAssignableFrom(ic.getMethod().getReturnType().getComponentType())) {
			return putArrayToResults(request, result);
		}
		if (!(result instanceof Dto)) {
			log.fine("result is not assignable from Dto.class. It can`t be returned via rest. "
					+ ic.getTarget().getClass().getName()
					+ "::"
					+ ic.getMethod().getName());
			return result;
		}
//		bean.put(((Dto) request), (Dto) result);
		plain.add((Dto)result);
		return result;
	}

	private Object putArrayToResults(final Object request, Object result) {
		final List list = Arrays.asList(result);
		plain.addAll(list);
		return result;
	}

	private Object putListToResults(final Object request, Object result) {
		Collection c= (Collection) result;
		List<Dto> dtoList = (List<Dto>) c.stream().filter(e->e instanceof Dto).collect(Collectors.toList());
		plain.addAll(dtoList);
		return result;
	}

}
