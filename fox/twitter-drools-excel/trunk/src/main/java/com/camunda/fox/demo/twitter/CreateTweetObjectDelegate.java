package com.camunda.fox.demo.twitter;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;


public class CreateTweetObjectDelegate implements JavaDelegate {

  public void execute(DelegateExecution execution) throws Exception {
    Tweet tweet = new Tweet();
    tweet.setEmail((String) execution.getVariable("email"));
    tweet.setContent((String) execution.getVariable("content"));
    tweet.setTwitterAccount((String) execution.getVariable("twitterAccount"));
    
    execution.setVariable("tweet", tweet);
    
    execution.removeVariable("email");
    execution.removeVariable("content");
    execution.removeVariable("twitterAccount");
  }

}
