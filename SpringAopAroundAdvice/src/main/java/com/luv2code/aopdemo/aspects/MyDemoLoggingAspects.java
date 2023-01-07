package com.luv2code.aopdemo.aspects;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.weaver.StaticJoinPointFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.luv2code.aopdemo.Account;

@Aspect
@Component
@Order(2)
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
	 * @Pointcut("execution(* com.luv2code.aopdemo.dao.*.*(..))") private void
	 * forDaoPackage() { }
	 * 
	 * @Before("forDaoPackage()") public void beforeAddAccountAdvice() {
	 * System.out.println("\n====>>> Executing @Before advice on method"); }
	 * 
	 * @Before("forDaoPackage()") public void performApiAnalytics() {
	 * System.out.println("\n====>>> Performing API analytics"); }
	 * 
	 */
	
	
	@Around("execution(* com.luv2code.aopdemo.service.*.getFortune(..))")
	public Object aroundGetFortune(ProceedingJoinPoint theProceedingJoinPoint)throws Throwable {
		//print out method we are advising on
		String method = theProceedingJoinPoint.getSignature().toShortString();
		System.out.println("\n====>>> Executing @Around  on method: " + method);
		
		//get begin timestamp
		long begin=System.currentTimeMillis();
		
		//now let's execute the method
		Object result=theProceedingJoinPoint.proceed();
		
		
		//get end timestamp
		
		long end=System.currentTimeMillis();
		
		//compute duration and display it 
		
		long duration=end-begin;
		
		System.out.println("\n====> Duration: "+duration/1000.0+" seconds");
		
		
		return result;
	}
	
	
	
	
	@After("execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))")
	public void afterFinallyFindAccountsAdvice(JoinPoint theJoinPoint) {
		// print out which method we are advising on
		String method = theJoinPoint.getSignature().toShortString();
		System.out.println("\n====>>> Executing @After (finally) on method: " + method);

	}

	@AfterThrowing(pointcut = "(execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..)))", throwing = "theExc"

	)
	public void afterThrowingFindAccountsAdvice(JoinPoint theJoinPoint, Throwable theExc) {

		// print out which method we are advising on
		String method = theJoinPoint.getSignature().toShortString();
		System.out.println("\n====>>> Executing @AfterThrowing on method: " + method);

		// log the exception
		System.out.println("\n====>>> The exception is: " + theExc);

	}

	// add a new advice for @AfterReturning on the findAccounts method

	@AfterReturning(pointcut = "(execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..)))", returning = "result")
	public void afterReturningFindAccountsAdvice(JoinPoint theJoinPoint, List<Account> result) {
		// print out which method we are advising on

		String method = theJoinPoint.getSignature().toShortString();
		System.out.println("\n====>>> Executing @AfterReturing on method: " + method);

		// print out the results of the method call
		System.out.println("\n====>>> Result is: " + result);

		// let's post-process the data.. let's modify it:)

		// convert the account names to uppercase

		convertAccountNamesToUpperCase(result);

		System.out.println("\n====>>> Result is: " + result);
	}

	public void convertAccountNamesToUpperCase(List<Account> result) {
		for (Account tempAccount : result) {
			// get uppercase version of name
			String theUpperName = tempAccount.getName().toUpperCase();

			// update the name on the account
			tempAccount.setName(theUpperName);

		}

	}

	@Before("com.luv2code.aopdemo.aspects.LuvAopExpressions.forDaoPackageNoGetterSetter()")
	public void beforeAddAccountAdvice(JoinPoint theJoinPoint) {
		System.out.println("\n====>> Executing @Before advice on method");

		// display the method signature
		MethodSignature methodSignature = (MethodSignature) theJoinPoint.getSignature();

		System.out.println("Method: " + methodSignature);

		// display method arguments

		// get args
		Object[] args = theJoinPoint.getArgs();

		// loop thru args

		for (Object tempArgs : args) {
			System.out.println(tempArgs);

			if (tempArgs instanceof Account) {
				// downcast and print Account specific stuff

				Account myAccount = new Account();
				System.out.println("Account name: " + myAccount.getName());
				System.out.println("Account Level: " + myAccount.getLevel());

			}

		}

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

	/*
	 * How to control the order of advices being applied
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
	 * AS->MyLoggingDemoAspect beforeAddAccountAdvice
	 * 
	 * 
	 * AS->MyApiAnalyticsAspect performApiAnalyticsAdvice
	 * 
	 * 
	 * 
	 * AS->MyCloudLogAspect logToCloudAdvice
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
	 * @Order(1) public class MyCloudLogAspect{
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
	 * We ant the following order: 1.MyCloudLogAspect
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
	 * 
	 * @Component
	 * 
	 * @Order(1) public class MyCloudLogAspect{
	 * 
	 * 
	 * }
	 * 
	 * 
	 * @Aspect
	 * 
	 * @Component
	 * 
	 * @Order(2) public class MyLoggingDemoAspect{
	 * 
	 *
	 * }
	 * 
	 * 
	 * @Aspect
	 * 
	 * @Component
	 * 
	 * @Order(3) public class MyApiAnalyticsAspect{
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
	 */

	/*
	 * AOP Reading Method Arguments with JoinPoints
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
	 * call the business method Account myAccount=new Account();
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
	 * display method arguments
	 *
	 *
	 * get args Object[] args=theJoinPoint.getArgs();
	 *
	 * loop thru args
	 *
	 * for(Object tempArg: args){
	 *
	 * System.out.println(tempArg); }
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
	 */

	/*
	 * Work completed
	 * 
	 * 1.@Before advice type
	 * 
	 * 2.Pointcut Expressions
	 * 
	 * 3.JoinPoints
	 * 
	 * 
	 * Work To Do- More Advice Types
	 * 
	 * 1.@AfterReturing
	 * 
	 * 2.@AfterThrowing
	 * 
	 * 3.@After
	 * 
	 * 4.Around
	 * 
	 * 
	 * 
	 * Advice Types
	 * 
	 * 1.Before advice:-run before the method
	 * 
	 * 
	 * 2.After returing advice:-run after the method(success execution)
	 * 
	 * 
	 * 3.After throwing advice:-run after method(if exception throw)
	 * 
	 * 
	 * 4.After finally advice:-run after the method(finally)
	 * 
	 * 
	 * 5.Around advice:-run before and after method
	 * 
	 * 
	 * Advice - Interaction
	 * 
	 * @Before
	 * 
	 * @AfterReturning(succesfully execution no exceptions) public void
	 * doSomeStuff(){
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * @AfterReturning Advice - Use Cases
	 * 
	 * Most common ->logging,security,transactions
	 * 
	 * 
	 * Audit logging ->who,what,when,where
	 * 
	 * 
	 * Post-processing Data ->Post process the data before returning to caller
	 * ->Format the data or enrich the data(really cool but be careful)
	 * 
	 * 
	 * 
	 * Example ->Create an advice that will run after a method call(success
	 * execution)
	 * 
	 * 
	 * @AfterReturingAdvice ->This advice will run after the method call(success
	 * execution)
	 * 
	 * public void afterReturingFindAccountsAdvice(){
	 * 
	 * System.out.println("Executing @AfterReturning advice"); }
	 * 
	 * 
	 * Access the Return Value ->Need to access the return value of method called
	 * 
	 * 
	 * Target Object AccountDAO List<Account> findAccounts()
	 * 
	 * 
	 * Logging Aspect
	 * 
	 * @AfterReturning
	 * 
	 * 
	 * 
	 * Access the Return Value
	 * 
	 * 
	 * @AfterReturing(
	 * pontcut="execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))",
	 * returning="result" )
	 * 
	 * public void afterReturningFindAccountsAdvice( JoinPoint
	 * theJoinPoint,List<Account> result ) {
	 * System.out.println("\n====>> result is: "+result);
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	/*
	 * @AfterThrowing Advice
	 * 
	 * 
	 * @Before public void doSomeStuff(){
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * @AfterThrowing Advice - Use Cases
	 * 
	 * 
	 * ->Log the exception
	 * 
	 * ->Perform auditing on the exception
	 * 
	 * ->Notify DevOps team via email or SMS
	 * 
	 * ->Encapsulate this functionality in AOP aspect for easy reuse
	 * 
	 * 
	 * 
	 * 
	 * Example
	 * 
	 * ->Create an advice that will run after an exception in thrown
	 * 
	 * 
	 * @AfterThrowing(
	 * pointcut="execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))",
	 * throwing="theExc" ) public void afterThrowingFindAccountAdvice(JoinPoint
	 * theJoinPoint,Throwable theExc){
	 * 
	 * log the exception
	 * 
	 * System.out.println("\n====>>> The exception is: "+theExc)
	 * 
	 * }
	 * 
	 * 
	 * Exception Propogation ->At this point,we are only intercepting the
	 * exception(reading it)
	 * 
	 * ->However,the exception is still propagated to calling program
	 * 
	 * 
	 * 
	 * 
	 * Development Process-> @AfterThrowing
	 * 
	 * 
	 * ->In Main App,add a try/catch block for exception handling
	 * 
	 * ->Modify AccountDAO to simulate throwing an exception
	 * 
	 * ->Add @AfterThrowing advice
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	/*
	 * @After advice is always run for success or failure
	 * 
	 * one the code is run return back to AccountDAO then return back to AOP Proxy
	 * then return back to Main Application (Main app)
	 * 
	 * 
	 * 
	 * MainApp->findAccounts()->AOP Proxy->findAccounts()->AccountDAO->success or
	 * exception->@After advice->exception->@AfterThrowing advice
	 * 
	 * then return back on the same path
	 * 
	 *
	 * @After Advice The @After advice does not have access to the exception
	 * 
	 * 
	 * If you need exception , then use @AfterThrowing advice
	 * 
	 * 
	 * The @After advice should be able to run in the case of success or error
	 * 
	 * Your code should not depend on happy path or an exception
	 * 
	 * Logging / auditing is the easiest case here.
	 * 
	 * 
	 * 
	 * 
	 * Development Process - @After Prep work
	 * 
	 * 
	 * Add @After advice
	 * 
	 * 
	 * Test for failure / exception case
	 * 
	 * 
	 * Test for success case
	 * 
	 * 
	 * 
	 * 
	 */

	
	/*Resolving Print Order Issue
	 * 
	 * Root Cause
	 * ->The data is printing to two different output streams
	 * 
	 * ->Spring is printing to the logger output stream
	 * 
	 * ->System.out.println is printing to the standard out output stream
	 * 
	 * 
	 * Solution
	 * ->To have everything in order , you should send to same output stream
	 * 
	 * ->We'll change our code to use the logger output stream... same as Spring
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * */
}
