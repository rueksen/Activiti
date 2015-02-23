package com.camunda.fox.demo.twitter;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;


public class TweetDelegate implements JavaDelegate {

  public void execute(DelegateExecution execution) throws Exception {      
    Tweet tweet = (Tweet) execution.getVariable("tweet");
      
    AccessToken accessToken = new AccessToken("220324559-8hWDVUXMSOaAnmtNNwBuNuhGJ6hOGwNdWHqhdOsU", "sGBZafB7saWYMwnPQGjjwU9Ggr0IJYkmPdyAFU5PI");
    Twitter twitter = new TwitterFactory().getOAuthAuthorizedInstance("HW62M0Rxtt39CbgdgP1og", "TJHNqolmPD6aHPtX8ec5Xp5zgIJcsMBTkwMpGCqdGuk", accessToken);
    twitter.updateStatus(tweet.getContent());
  }

}
