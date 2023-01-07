package com.luv2code.aopdemo.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(3)
public class MyDemoLoggingAspects {

	// this is where we add all of our related advices for logging

	// let's start with @Before advice

	// Pointcut:A predicate expression for where advice should be applied
	// execution(moifiers-pattern?return-type-pattern
	// declaring-type-pattern?method-name-pattern(param-pattern)throws-pattern?)

	// For the patterns,can use wildcard:* (matches on everything)

	/*
	 * Match on method names
	 * 
	 * 
	 * Match only addAccount() method in AccountDAO class
	 * 
	 * 
	 * Match methods starting with add in any class
	 * 
	 * @Before("execution(public void add*())")
	 * 
	 * 
	 * 
	 * 
	 * Match methods starting with processCreditCard in any class
	 * 
	 * @Before("execution(public VerficationResult processCredictCard*())")
	 * 
	 * 
	 * 
	 * 
	 * Use wildcards on modifiers and return type(modifier is wildcard in the below
	 * code)
	 * 
	 * @Before("execution(* * processCreditCard*())")
	 * 
	 * 
	 * 
	 * Modifier is optional ... so you don't have to list it
	 * 
	 * @Before("execution(* processCreditCard*())")
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @Before("execution(* process)")
	 * 
	 */

	/*
	@Pointcut("execution(* com.luv2code.aopdemo.dao.*.*(..))")
	private void forDaoPackage() {
	}

	@Before("forDaoPackage()")
	public void beforeAddAccountAdvice() {
		System.out.println("\n====>>> Executing @Before advice on method");
	}

	@Before("forDaoPackage()")
	public void performApiAnalytics() {
		System.out.println("\n====>>> Performing API analytics");
	}
	
	*/
	
	
	
	
	@Before("com.luv2code.aopdemo.aspects.LuvAopExpressions.forDaoPackageNoGetterSetter()")
    public void beforeAddAccountAdvice() {
		System.out.println("\n====>> Executing @Before advice on method");
	}
	
	
	

	
	
	

//	()-matches a method with no arguments

//	(*)-matches a method with one argument of any type

//	(..)-matches a method with 0 or more arguments of any type

	/*
	 * Match addAccount methods with no arguments
	 * 
	 * @Before("execution(*addAccount())")
	 * 
	 * 
	 * Match addAccount methods with have Account param
	 * 
	 * @Before("execution(*addAccount(com.luv2code.aopdemo.Account))")
	 * 
	 * 
	 * Match addAccount methods with any number of arguments
	 * 
	 * @Before("execution(*addAccount(..))")
	 * 
	 * 
	 * Match any method in our DAO package:com.luv2code.aopdemo.dao
	 * 
	 * @Before("execution(*com.luv2code.aopdemo.dao.*.*(..))")
	 * 
	 * 
	 * 
	 * 
	 */

	/*
	 * Problem
	 * 
	 * How can we reuse a pointcut expression? Want to apply to multiple advices
	 * 
	 * @Before("execution(*com.luv2code3.aopdemo.dao.*.*(..))")
	 * 
	 * 
	 * Reuse the pointcut expression 1.copy /paste the same expression
	 * 
	 * 
	 * Ideal Solution
	 * 
	 * 1.Create a pointcut declaration once .
	 * 
	 * @Pointcut("execution(* com.luv2code.aopdemo.dao.*.*(..))") private void
	 * forDaoPackage(){}
	 * 
	 * Step1-Create Pointcut Declaration
	 * 
	 * @Pointcut("execution(* com.luv2code.aopde4mo.dao.*.*(..))") private void
	 * forDaoPackage(){
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
	 * @Before("forDaoPackage()") public void beforeAddAccountAdvice(){
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * @Before("forDaoPackage()") public void performApiAnalytics(){
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
	 * Development Process 1.Create a pointcut declarations
	 * 
	 * 
	 * 2.Combine pointcut declarations.
	 * 
	 * 
	 * 3.Apply pointcut declaration to advice(s)
	 * 
	 * 
	 */

	
	
	
	/*How to control the order of advices being applied
	 * 
	 * MyLoggingDemoAspect
	 * 
	 * The order is undefined
	 * 
	 * beforeAddAccountAdvice
	 *  
	 * 
	 * performApiAnalyticsAdvice
	 *
	 * 
	 * logToCloudAdvice
	 * 
	 * 
	 * 
	 * 
	 * To Control Order
	 * 
	 * 
	 * Refactor:Place advices in separate Aspects
	 * 
	 * 
	 * 
	 * Control order on Aspects using the @Order annotation
	 * 
	 * 
	 * 
	 * Guarantees order of when Aspects are applied
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Development Process
	 * 
	 * Refactor:Place advices in separate Aspects
	 * 
	 * 
	 * Add @Order annotation to Aspects
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Step1-Refactor:Place advices in separate Aspects
	 * 
	 * 
	 * 
	 * MyLoggingDemoAspect
	 * 
	 * beforeAddAccountAdvice
	 * 
	 * performApiAnalyticsAdvice
	 * 
	 * logToCloudAdvice
	 * 
	 * 
	 * 
	 * 
	 * After Refactor
	 * 
	 * 
	 * 
	 * 
	 * AS->MyLoggingDemoAspect
	 * beforeAddAccountAdvice
	 * 
	 * 
	 * AS->MyApiAnalyticsAspect
	 * performApiAnalyticsAdvice
	 * 
	 * 
	 * 
	 * AS->MyCloudLogAspect
	 * logToCloudAdvice
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Step2-Add @Order annotation
	 * 
	 * Control order on Aspects using the @Order annotation
	 * 
	 * 
	 * @Order(1)
	 * public class MyCloudLogAspect{
	 * 
	 * 
	 * }
	 * 
	 * ->Guarantess order of when aspects are applied
	 * 
	 * 
	 * ->Lower numbers have higher precedence
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * We ant the following order:
	 * 1.MyCloudLogAspect
	 * 
	 * 
	 * 2.MyLoggingDemoAspect
	 * 
	 * 
	 * 
	 * 3.MyApiAnalyticsAspect
	 * 
	 * 
	 * 
	 * 
	 * Main app->AOP proxy->@Order(1)->@Order(2)->@Order(3)->Target Object
	 * 
	 * @Aspect
	 * @Component
	 * @Order(1)
	 * public class MyCloudLogAspect{
	 * 
	 * 
	 * }
	 * 
	 * 
	 * @Aspect
	 * @Component
	 * @Order(2)
	 * public class MyLoggingDemoAspect{
	 * 
	 *
	 * }
	 * 
	 * 
	 * @Aspect
	 * @Component
	 * @Order(3)
	 * public class MyApiAnalyticsAspect{
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * @Order annotation facts
	 * 
	 * 
	 * Lower number have higher precedence
	 * 
	 * 
	 * ->Range:Integer.MIN_VALUE to Integer.MAX_VALUE
	 * 
	 * 
	 * ->Negative numbers are allowed
	 * 
	 * 
	 * ->Does not have to be consecutive
	 * 
	 * 
	 * 
	 * 
	 * 
	 * */
	
	
	
	
	
	
	/*AOP Reading Method Arguments with JoinPoints
	 * 
	 * 
	 * 
	 * Problem 
	 * 
	 * When we are in an aspect (ie for logging)
	 * 
	 * How can we access method parameters
	 * 
	 * 
	 * MainDemoApp.java
	 * 
	 * call the business method
	 * Account myAccount=new Account();
	 * theAccountDAO.addAccount(myAccount,true);
	 * 
	 * 
	 * 
	 * 
	 * Development Process
	 * 
	 * 1.Access and display Method Signature
	 * 
	 * @Before("...")
	 * 
	 * public void beforeAddAccountAdvice(JoinPoint theJoinPoint){
	 * 
	 * MethodSignature methodSig=(MethodSignature)theJoinPoint.getSignature();
	 * 
	 * System.out.println("Method: "+methodSig);
	 * 
	 * }
	 * 
	 * 2.Access and display Method Arguments
	 * 
	 * 
	 * 
	 * @Before
	 * 
	 * public void beforeAddAccountAdvice(JoinPoint theJoinPoint){
	 * 
	 *display method arguments
	 *
	 *
	 *get args
	 *Object[] args=theJoinPoint.getArgs();
	 *
	 *loop thru args
	 *
	 *for(Object tempArg: args){
	 *
	 * System.out.println(tempArg);
	 *}
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * */
	
}
