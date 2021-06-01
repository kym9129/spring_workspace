package com.kh.spring.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component // 빈등록
@Aspect // aspect클래스 등록. pointcut, advice를 가지고 있음.
public class LoggerAspect {
	
	@Pointcut("execution(* com.kh.spring.memo.controller.MemoController.insertMemo(..))")
	public void loggerPointcut() {}
	
	/**
	 * Around Advice
	 * 	- JoinPoint 실행 전, 실행 후에 삽입되어 처리되는 advice(보조업무)
	 * 
	 * Stopwatch
	 * https://www.logicbig.com/how-to/code-snippets/jcode-spring-framework-stopwatch.html
	 */
	@Around("loggerPointcut()")
	public Object logger(ProceedingJoinPoint joinPoint) throws Throwable {
		Signature signature = joinPoint.getSignature();
		
		//before
		log.debug("----- {} start", signature);
		//StopWatch watch = new StopWatch();
		//watch.start();
		
		//주업무 joinPoint 실행
		Object returnObj = joinPoint.proceed();
		//watch.stop();
		//double time = watch.getTotalTimeSeconds();
		//long time = watch.getLastTaskTimeMillis();
		//after
		//log.debug("time = {}", time);
		log.debug("----- {} end", signature);
		
		return returnObj;
	}

}
