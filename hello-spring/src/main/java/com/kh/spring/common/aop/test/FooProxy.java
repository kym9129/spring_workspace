package com.kh.spring.common.aop.test;

public class FooProxy implements Foo {

	private Foo foo;
	
	public FooProxy(Foo foo) {
		this.foo = foo;
	}
	
	@Override
	public void sayHello() {
		//보조업무로직 before
		System.out.println("---------------before------------");
		
		this.foo.sayHello();
		
		//보조업무로직 after
		System.out.println("-------------after--------------");
	}

	@Override
	public String getName() {
		return this.foo.getName();
	}

}
