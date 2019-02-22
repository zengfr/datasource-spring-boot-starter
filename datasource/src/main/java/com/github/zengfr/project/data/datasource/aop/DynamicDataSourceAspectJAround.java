package com.github.zengfr.project.data.datasource.aop;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import com.github.zengfr.project.data.datasource.DynamicDataSourceKey;
import com.github.zengfr.project.data.datasource.DynamicDataSourceKeyContextHolder;
import com.github.zengfr.project.util.ExpressionUtil;
/**
 * zengfr QQ:362505707/1163551688 Email:zengfr3000@qq.com
 * https://github.com/zengfr/stepchain-spring-boot-starter
 * 
 * @author zengfr QQ:362505707/1163551688 Email:zengfr3000@qq.com
 *         https://github.com/zengfr/stepchain-spring-boot-starter
 */
@Aspect
public class DynamicDataSourceAspectJAround implements Ordered {

	@Autowired
	ExpressionUtil expressionUtil;
	private int order;

	@Value("100")
	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public int getOrder() {
		return order;
	}

	@Pointcut("execution(public * *(..))")
	public void anyPublicMethodAnno() {
		;//
	}

	@Pointcut("@within(com.github.zengfr.project.data.datasource.DynamicDataSourceKey)")
	public void anyClassAnno() {
		;//
	}

	@Pointcut("anyClassAnno()||anyPublicMethodAnno()")
	public void any() {
		;//
	}

	@Around("anyClassAnno()")
	public Object invoke2(ProceedingJoinPoint pjp) throws Throwable {
		DynamicDataSourceKey dynamicDataSource = findDynamicDataSourceAnnotation(pjp);
		return process(pjp, dynamicDataSource);
	}

	@Around("@annotation(com.github.zengfr.project.data.datasource.DynamicDataSourceKey)")
	public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
		DynamicDataSourceKey dynamicDataSource = findDynamicDataSourceAnnotation(pjp);
		return process(pjp, dynamicDataSource);
	}

	private Object process(ProceedingJoinPoint pjp, DynamicDataSourceKey ds) throws Throwable {
		try {

			MethodSignature signature = (MethodSignature) pjp.getSignature();
			LinkedHashMap<String, Object> mapArgs = getArgsLinkedHashMap(signature.getParameterNames(), pjp.getArgs());
			Object keyValue = ds.shardId();
			String keyEls = ds.shardIdEls();
			if (StringUtils.isNotEmpty(keyEls)) {
				keyValue = getElsValue(keyEls, mapArgs);
			}
			DynamicDataSourceKeyContextHolder.setContextKey(ds.value(), ds.dbType(), Integer.valueOf("" + keyValue));
			Object result = pjp.proceed();
			DynamicDataSourceKeyContextHolder.removeContextKey();
			return result;
		} finally {
			DynamicDataSourceKeyContextHolder.removeContextKey();
		}
	}

	private Object getElsValue(String keyEls, LinkedHashMap<String, Object> mapArgs) {
		return expressionUtil.getValue(keyEls, mapArgs);
	}

	private LinkedHashMap<String, Object> getArgsLinkedHashMap(String[] parameterNames, Object[] args) {

		Assert.isTrue(parameterNames.length == args.length, "the length should be the same!");

		LinkedHashMap<String, Object> map = new LinkedHashMap<>(args.length);
		for (int i = 0; i < args.length; i++) {
			map.put(parameterNames[i], args[i]);
		}

		return map;
	}

	private DynamicDataSourceKey findDynamicDataSourceAnnotation(ProceedingJoinPoint point) throws Throwable {
		Method method = findExecutedMethod(point);
		DynamicDataSourceKey dataSource = AnnotationUtils.findAnnotation(method, DynamicDataSourceKey.class);
		if (dataSource == null) {
			dataSource = AnnotationUtils.findAnnotation(point.getTarget().getClass(), DynamicDataSourceKey.class);
		}

		if (dataSource == null) {
			dataSource = AnnotationUtils.findAnnotation(point.getTarget().getClass().getPackage(),
					DynamicDataSourceKey.class);
		}
		return dataSource;
	}

	private DynamicDataSourceKey findDynamicDataSourceAnnotation2(ProceedingJoinPoint point) throws Throwable {
		Method method = findExecutedMethod(point);
		DynamicDataSourceKey dataSource = findDynamicDataSourceAnnotation(point);
		DynamicDataSourceKey dataSource1 = AnnotationUtils.findAnnotation(method, DynamicDataSourceKey.class);
		DynamicDataSourceKey dataSource2 = AnnotationUtils.findAnnotation(point.getTarget().getClass(),
				DynamicDataSourceKey.class);
		DynamicDataSourceKey dataSource3 = AnnotationUtils.findAnnotation(point.getTarget().getClass().getPackage(),
				DynamicDataSourceKey.class);

		return dataSource;
	}

	/**
	 * Find executed method
	 *
	 * @param point
	 * @return
	 * @throws NoSuchMethodException
	 */
	private Method findExecutedMethod(ProceedingJoinPoint point) throws NoSuchMethodException {
		final MethodSignature methodSignature = (MethodSignature) point.getSignature();
		Method method = methodSignature.getMethod();
		if (method.getDeclaringClass().isInterface()) {
			String methodName = methodSignature.getName();
			method = point.getTarget().getClass().getDeclaredMethod(methodName, method.getParameterTypes());
		}
		return method;
	}

}