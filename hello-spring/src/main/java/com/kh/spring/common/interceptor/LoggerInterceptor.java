package com.kh.spring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerInterceptor extends HandlerInterceptorAdapter {

	/**
	 * Handler 호출 전 실행
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.debug("================== start ==================");
		log.debug(request.getRequestURI());
		log.debug("-------------------------------------------");
		
		//return절 이거 있어야 handler가 호출된다. true를 리턴
		return super.preHandle(request, response, handler);
	}
	/**
	 * Handler 리턴 이후 실행
	 * (ModelAndView객체 확인 가능)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
		log.debug("-------------------------------------------");
		log.debug("modelAndView = {}", modelAndView);
	}
	
	/**
	 * view단(jsp) 작업 이후 실행
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.debug("----------------------- view --------------------");
		super.afterCompletion(request, response, handler, ex);
		log.debug("______________________ end _______________________\n");
		
	}
	
	

}
