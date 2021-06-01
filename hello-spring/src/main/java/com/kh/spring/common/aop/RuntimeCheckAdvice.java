package com.kh.spring.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class RuntimeCheckAdvice {
	
	@Pointcut("execution(* com.kh.spring.memo.controller.MemoController.insertMemo(..))")
	public void pointcut() {}
	
	@Around("pointcut()")
	public Object runtimeCheck(ProceedingJoinPoint joinPoint) throws Throwable {
		Signature signature = joinPoint.getSignature(); //패키지를 포함한 메소드명
		String methodName = signature.getName(); //심플하게 메소드명만 가져올 수 있음
		//before
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		//본 업무
		Object returnObj = joinPoint.proceed();
		
		//after
		stopWatch.stop();
		long duration = stopWatch.getTotalTimeMillis();
		log.debug("{} 소요시간 : {} ms", methodName, duration);
		
		return returnObj;
	}

}
