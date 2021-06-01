package com.kh.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @SpringBootApplication에 포함된 내용들
 * 
 *  - @SpringBootConfiguration
 * 		-> springboot관련 설정
 *  - @EnableAutoConfiguration
 * 		-> application-context를 관리(root, servlet 나뉘지 않고 하나로 관리)
 *  - @ComponentScan : 현재 실행 클래스가 속한 base-package를 기준으로 annotation 활성화 (Component, Autowired 등)
 * 
 *
 */
@SpringBootApplication
public class HelloSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloSpringbootApplication.class, args);
	}

}
