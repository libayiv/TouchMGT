package com.security.common.dataSource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 数据源适配器
 */
@Aspect
@Component
public class MultipleDataSourceAspectAdvice {

	private Logger log = LoggerFactory.getLogger(MultipleDataSourceAspectAdvice.class);
	
	@Around("execution(* com.security.modules.*.dao.*.*(..))")
	public Object doAround(ProceedingJoinPoint jp) throws Throwable {
		log.debug("doAround    jp:{}", jp);

		if (jp.getTarget() instanceof AFRICASqlMapper) {
			MultipleDataSource.putDataSource("AFRICADataSource");
		} else {
			MultipleDataSource.putDataSource("dataSource");
		}
		return jp.proceed();
	}
}
