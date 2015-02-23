package com.camunda.fox.demo.twitter.twitter4j;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import com.camunda.fox.demo.twitter.Tweet;

public class TweetDelegate implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		Tweet tweet = (Tweet) execution.getVariable("tweet");
		AccessToken accessToken = new AccessToken("220324559-8hWDVUXMSOaAnmtNNwBuNuhGJ6hOGwNdWHqhdOsU", "sGBZafB7saWYMwnPQGjjwU9Ggr0IJYkmPdyAFU5PI");
		Twitter twitter = new TwitterFactory().getInstance(accessToken);
		twitter.updateStatus(tweet.getContent());
	}

}
