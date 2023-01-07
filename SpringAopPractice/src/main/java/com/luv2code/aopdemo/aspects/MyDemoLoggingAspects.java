package com.luv2code.aopdemo.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class MyDemoLoggingAspects {
	
	//this is where we add all of our related advices for logging
	
	
	
	//let's start with @Before advice
	
	
	//Pointcut:A predicate expression for where advice should be applied
	//execution(moifiers-pattern?return-type-pattern declaring-type-pattern?method-name-pattern(param-pattern)throws-pattern?)
	
	
	//For the patterns,can use wildcard:* (matches on everything)
	
	
	/*Match on method names
	 * 
	 * 
	 * Match only addAccount() method in AccountDAO class
	 * 
	 * 
	 * Match methods starting with add in any class
	 * @Before("execution(public void add*())")
	 * 
	 * 
	 * 
	 * 
	 * Match methods starting with processCreditCard in any class
	 * @Before("execution(public VerficationResult processCredictCard*())")
	 * 
	 * 
	 * 
	 * 
	 * Use wildcards on modifiers and return type(modifier is wildcard in the below code)
	 * @Before("execution(* * processCreditCard*())")
	 * 
	 * 
	 * 
	 * Modifier is optional ... so you don't have to list it 
	 * @Before("execution(* processCreditCard*())")
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @Before("execution(* process)")
	 * 
	 * */
	
	

//	@Before("execution(* com.luv2code.aopdemo.dao.*.*(..))")
//	public void beforeAddAccountAdvice() {
//		System.out.println("\n======>>> Executing @Before advice on method");
//	}
//	
//	
//	
	
//	()-matches a method with no arguments
	
//	(*)-matches a method with one argument of any type
	
//	(..)-matches a method with 0 or more arguments of any type
	
	
/*
 * Match addAccount methods with no arguments
 * @Before("execution(*addAccount())")
 * 
 * 
 * Match addAccount methods with have Account param
 * @Before("execution(*addAccount(com.luv2code.aopdemo.Account))")
 * 
 * 
 * Match addAccount methods with any number of arguments
 * @Before("execution(*addAccount(..))")
 * 
 * 
 * Match any method in our DAO package:com.luv2code.aopdemo.dao
 * @Before("execution(*com.luv2code.aopdemo.dao.*.*(..))")
 * 
 * 
 * 
 * 
 * */
	
	
	
	/*Problem
	 * 
	 * How can we reuse a pointcut expression?
	 * Want to apply to multiple advices
	 * 
	 * @Before("execution(*com.luv2code3.aopdemo.dao.*.*(..))")
	 * 
	 * 
	 * Reuse the pointcut expression 
	 * 1.copy /paste the same expression
	 * 
	 * 
	 * Ideal Solution
	 * 
	 * 1.Create a pointcut declaration once .
	 * 
	 * @Pointcut("execution(* com.luv2code.aopdemo.dao.*.*(..))")
	 * private void forDaoPackage(){}
	 * 
	 * Step1-Create Pointcut Declaration
	 * 
	 * @Pointcut("execution(* com.luv2code.aopde4mo.dao.*.*(..))")
	 * private void forDaoPackage(){
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 2.Apply it to multiple advices.
	 * 
	 * 
	 * @Before("forDaoPackage()")
	 * public void beforeAddAccountAdvice(){
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * @Before("forDaoPackage()")
	 * public void performApiAnalytics(){
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * Benefits to Pointcut Declarations
	 * 
	 * 
	 * 1.Easily reuse pointcut expression.
	 * 
	 * 
	 * 2.Update pointcut in one location.
	 * 
	 * 
	 * 3.Can also share and combine pointcut expressions
	 * 
	 * 
	 * 
	 * 
	 * 
	 * */
	

}
