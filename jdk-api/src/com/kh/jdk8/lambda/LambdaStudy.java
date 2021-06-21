package com.kh.jdk8.lambda;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Lambda 표현식
 * JAVA에서 함수형 프로그래밍을 지원하기 위한 api(jdk8부터 사용 가능)
 * 
 *  - 객체지향프로그래밍 (상태(field), 행동(method)을 객체로 관리)과 달리
 * 함수를 값으로써 취급한다.
 *  - 상태관리를 하지 않고, 모든 것을 불변으로 처리한다.
 *
 */
public class LambdaStudy {

	public static void main(String[] args) {
		LambdaStudy study = new LambdaStudy();
//		study.test0();
//		study.test1();
//		study.test2();
//		study.test3();
		study.test4();

	}
	
	/**
	 * @ 실습문제
	 */
	private void test4() {
		// 1. 현재 시각 출력 람다식
		Consumer<Date> currentTime = date -> {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(sdf.format(date));
		};
		currentTime.accept(new Date());
		
		// 2. 로또(1~45 사이의 중복 없는 난수 Set) 생성 람다식
		Supplier<Set> lottoNumSet = () -> {
			
			Set<Integer> set = new HashSet<>();
			Supplier<Integer> random1to45 = () -> (int)(Math.random() * 45) + 1;
			
			while(set.size() < 6) {
				set.add(random1to45.get());
			}
			return 	set;
		};
		System.out.println(lottoNumSet.get());
		
		// 3. 환율 계산기 : 원화 입력 시 달러값을 리턴 (1달러는 1100원이다.)
		Function<Integer, Double> exchangeWonToDollar = (won) -> Math.floor(((double)won / 1100) * 100) / 100;
		System.out.println("$" + exchangeWonToDollar.apply(3000));
		
	}
	/**
	 * JDK가 제공하는 함수형 인터페이스
	 *  - 제네릭을 사용해서 람다식 작성 타임에 매개변수나 리턴타입이 결정되도록 함
	 *  
	 *  1. java.lang.Runnable 				: 매개변수 없음 | 리턴값 없음    | run():void
	 *  2. java.util.function.Supplier<R>   : 매개변수 없음 | 리턴 R       | get():R
	 *  3. java.util.function.Consumer<T>	: 매개변수 T   | 리턴값 없음 	  | accept(T):void 
	 *  4. java.util.function.Function<T,R> : 매개변수 T   | 리턴 R   	  | apply(T):R
	 *  5. java.util.function.Predicate<T>  : 매개변수 T   | 리턴 boolean | test(T):boolean
	 */
	private void test3() {
		
		// 1. Runnable : 매개변수 X, 리턴값 X
		Runnable r = () -> {
			for(int i = 0; i < 10; i++) {
				System.out.println(new Date());
			}
		};
		r.run();
		/**
		 * 실행 결과 : 
		 * Mon Jun 21 16:53:59 KST 2021
			Mon Jun 21 16:53:59 KST 2021
			Mon Jun 21 16:53:59 KST 2021
			Mon Jun 21 16:53:59 KST 2021
			Mon Jun 21 16:53:59 KST 2021
			Mon Jun 21 16:53:59 KST 2021
			Mon Jun 21 16:53:59 KST 2021
			Mon Jun 21 16:53:59 KST 2021
			Mon Jun 21 16:53:59 KST 2021
			Mon Jun 21 16:53:59 KST 2021
		 */
		
		// 2. Supplier : 매개변수 X, 리턴값 O.
		// 제네릭에 리턴값의 타입을 작성
		Supplier<Long> supplier = () -> new Date().getTime();
		System.out.println(supplier.get()); //1624262239346
		
		// 2-1. 난수 생성 람다식 작성하기
		Supplier<Integer> random1to100 = () -> (int)(Math.random() * 100) + 1;
		System.out.println(random1to100.get());
		Supplier<Integer> random1to45 = () -> (int)(Math.random() * 45) + 1;
		System.out.println(random1to45.get());
		
		// 3. Consumer : 매개변수 O. 리턴값 X
		Consumer<String> consumer = name -> System.out.println("이름 : " + name);
		consumer.accept("홍길동"); // 이름 : 홍길동
		
		// 3-1. 현재날짜 출력하기
		Consumer<Date> printTime = date -> {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(sdf.format(date));
		};
		printTime.accept(new Date()); // 2021-06-21
		
		// 4. Function : 매개변수 O, 리턴값 O
		Function<String, Integer> function = str -> str.length();
		System.out.println(function.apply("안녕하세요")); // 5
		System.out.println(function.apply("hello")); // 5
		System.out.println(function.apply("bye bye")); // 7
		
		// 5. Predicate : 매개변수 O, 리턴타입 boolean
		Predicate<Integer> predicate = n -> n % 3 == 0 && n % 5 == 0;
		int num = random1to100.get(); //방금 만든 난수 생성기 대입
		System.out.println("행운의 숫자 : " + num);
		if(predicate.test(num)) {
			System.out.println("3의 배수 && 5의 배수 : " + num);
		} else {
			System.out.println("꽝!");
		}
		
	}

	/**
	 * 함수형 인터페이스
	 *  - 추상메소드가 딱 하나인 인터페이스를 베이스로 람다식을 작성할 수 있다.
	 *  - @FunctionalInterface 어노테이션을 사용하면, 문법 오류를 컴파일타임에 확인 가능
	 */
	private void test2() {
		
		//a, b를 비교하여 더 큰 수 리턴
		Foo max = (a, b) -> a > b ? a : b;
		System.out.println(max.process(80, 77)); //80
		
		// 작은 수 리턴
		Foo min = (a, b) -> a < b ? a : b;
		System.out.println(min.process(80, 77)); //77
		
		// 합 구하기
		Foo sum = (a, b) -> a + b;
		System.out.println(sum.process(80, 77)); // 157
		
		
		// 인터페이스를 통해 문자열 이름과 나이를 받아 출력 가능한 람다식 작성하기
		// 1. 인터페이스 선언
		// 2. 람다식 작성
		// 3. 람다식 호출
		Person p = (name, age) -> "이름 : " + name + ", 나이 : " + age;
		System.out.println(p.info("홍길동", 30));
		
		//void로 선언한 경우
		Bar printPerson = (name, age) -> System.out.printf("이름 : %s, 나이 : %d세%n", name, age);
		printPerson.print("홍길동", 80);
		
	}
	
	@FunctionalInterface
	interface Bar {
		void print(String s, int n);
	}
	
	@FunctionalInterface
	interface Person{
		String info(String name, int age);
	}
	
	
	@FunctionalInterface
	interface Foo {
		int process(int a, int b);
//		int process(int a);
	}

	/**
	 * 메소드만 전달 또는 변수에 저장이 불가능하므로, 인터페이스를 통해 처리해야 한다.
	 */
	private void test1() {
//		Pita pita = (int a, int b) -> {
//			return Math.sqrt(a * a + b * b);
//		};
		
		// return절이 한줄일 경우 {return } 생략 가능
//		Pita pita = (int a, int b) -> Math.sqrt(a * a + b * b);
		
		// 매개변수 선언부 자료형 생략 가능
		// -> Interface의 추상메소드와 비교하기 때문에 가능. (그래서 추상메소드가 1개여야 함)
		Pita pita = (a, b) -> Math.sqrt(a * a + b * b);
		
		double c = pita.calc(100, 30);
		System.out.println("빗변 : " + c);
		
	}

	/**
	 * 피타고라스의 정리 : 빗변제곱 = 밑변제곱 + 높이제곱
	 * 
	 * 자바에서 메소드는 독립적으로 존재할 수 없다. 인자로 전달되거나, 리턴되거나 모두 불가능.
	 * 객체를 통해서만 전달 가능.
	 */
	private void test0() {
		//익명클래스 : 객체 선언과 생성을 동시에 처리
		Pita pita = new Pita() {
			@Override
			public double calc(int a, int b) {
				return Math.sqrt(a * a + b * b);
			}
		};
		
		double c = pita.calc(100, 30);
		System.out.println("빗변 = " + c);
		
	}
	
	/**
	 * 빗변을 구하는 추상메소드 선언
	 *
	 */
	interface Pita {
		double calc(int a, int b);
	}

}
