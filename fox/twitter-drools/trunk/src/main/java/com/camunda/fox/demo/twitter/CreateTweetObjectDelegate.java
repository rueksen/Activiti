package com.camunda.fox.demo.twitter;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class CreateTweetObjectDelegate implements ExecutionListener {
	
	public static Tweet lastTweetCreated = null;

	@Override
	public void notify(DelegateExecution ctx) throws Exception {
		Tweet tweet = new Tweet();
		tweet.setContent( (String)ctx.getVariable("content") );
		tweet.setEmail( (String)ctx.getVariable("email") );
		
		// tweet will be set as object and serialized, this is NOT best practice, but easy and straightforward in this case :-)
		ctx.setVariable("tweet", tweet);
		
		// little helper to make unit testing easier (not best practice as well!)
		lastTweetCreated = tweet;

	}

}
