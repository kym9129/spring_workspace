package com.kh.spring.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * ## Slf4j 테스트
`PSA의 대표적인 예시`

PSA Portable Service Abstraction ~~이동 가능한 서비스 추상체~~
**Slf4j - Spring이 제공하는 logging 추상체**

구현체

1. log4j
2. java.util.Logging
3. apache logging
4. logback

→구현체를 직접 다루지 않고 추상체를 이용하는 이점 : 구현체가 뭐가됐건 일관되게 코드를 제어할 수 있다
 *
 */
@Slf4j
public class Slf4jTest {
	
	//private static final Logger log = LoggerFactory.getLogger(Slf4jTest.class);

	public static void main(String[] args) {
//		log.fatal("fatal"); //slf4j에는 fatal레벨이 없다.
		log.error("error - {}", "메세지메세지");
		log.warn("warn");
		log.info("info");
		log.debug("debug");
		log.trace("trace");

	}

}
