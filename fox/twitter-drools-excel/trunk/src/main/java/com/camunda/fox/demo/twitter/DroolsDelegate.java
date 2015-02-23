package com.camunda.fox.demo.twitter;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;


public class DroolsDelegate implements JavaDelegate {

	public static String decisionTableResource = "/TweetRejectionRules.xls";

	public void execute(DelegateExecution execution) throws Exception {
		
		Tweet tweet = (Tweet) execution.getVariable("tweet");

		DroolsUtil d = new DroolsUtil();
		d.loadDecisionTable(decisionTableResource);
		d.executeDrools(tweet);
		
		execution.setVariable("tweet", tweet);
	}
}
