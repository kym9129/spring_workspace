package com.kh.spring.common.aop.test;

public class FooImpl implements Foo {

	@Override
	public void sayHello() {
		System.out.println("say foo o o o ooooooooooooooooooooo!");
		
	}

	@Override
	public String getName() {
		return "My Name is FOO!";
	}

}
