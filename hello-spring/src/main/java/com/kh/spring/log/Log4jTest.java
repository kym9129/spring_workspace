package com.kh.spring.log;

import org.apache.log4j.Logger;

/**
 * # Logging Framework

- 콘솔로그 : System.out.printxx 보다 효율적인 로그관리가 가능
- 파일로그

### Level(Priority) 메소드 우선순위

- fatal : 아주 심각한 에러 발생
- error : 요청 처리 중 오류 발생
- warn : 경고성 메세지. 현재 실행에는 문제 없지만, 향후 잠재적 오류가 될 가능성이 있음 `deprecated`
- info : 요청 처리 중 상태변경 등의 정보성 메세지
- debug : 개발 중에 필요한 로그. 운영 상에는 필요없음
- trace : 개발용. debug의 범위를 한정해서 로깅할 때.

Slf4j(스프링이 제공하는 로깅 추상) 없이 순수하게 Log4j프레임워크만 테스트
 *
 */

public class Log4jTest {
	// org.apache.log4j.Logger;
	private static final Logger log = Logger.getLogger(Log4jTest.class);

	public static void main(String[] args) {
		log.fatal("fatal");
		log.error("error");
		log.warn("warn");
		log.info("info");
		log.debug("debug");
		log.trace("trace");
		
	}

}
