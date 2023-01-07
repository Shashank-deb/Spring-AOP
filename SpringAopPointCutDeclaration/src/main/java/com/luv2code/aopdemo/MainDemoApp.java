package com.luv2code.aopdemo;

import java.nio.channels.GatheringByteChannel;

import org.apache.catalina.tribes.membership.Membership;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import com.luv2code.aopdemo.dao.AccountDAO;
import com.luv2code.aopdemo.dao.MembershipDAO;

public class MainDemoApp {
	public static void main(String[] args) {
		//read spring config java class
		AnnotationConfigApplicationContext context=new
				AnnotationConfigApplicationContext(DemoConfig.class);
		
		//get the bean from spring controller
		AccountDAO theAccountDAO=context.getBean("accountDAO",AccountDAO.class);
		
		//get the membership bean from spring container
		MembershipDAO theMembershipDAO=context.getBean("membershipDAO",MembershipDAO.class);
		
		//call the membership business method
		Account myAccount=new Account();
		theAccountDAO.addAccount(myAccount,true);
		theAccountDAO.doWork();
		
		
		//call the accountdao getter/setter methods
		theAccountDAO.setName("foobar");
		theAccountDAO.setServiceCode("silver");
		
		
		String name=theAccountDAO.getName();
		String code=theAccountDAO.getServiceCode();
		
		
		//do it again!
		System.out.println("\nlet's call it again!\n");
		
		
		
		//call the business method again 
		theMembershipDAO.addSillyMember();
		theMembershipDAO.goToSleep();
		//close the context
		context.close();
	}

}
