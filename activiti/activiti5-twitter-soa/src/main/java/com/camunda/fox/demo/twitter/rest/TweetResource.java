package com.camunda.fox.demo.twitter.rest;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.camunda.fox.demo.twitter.Tweet;


public class TweetResource extends ServerResource {

  @Post
  public void createTweet(Tweet tweet) {
    System.out.println("Now we tweet via REST: " + tweet);
    TwitterAccountResource.publishedTweets.add(tweet);
  }
  

}
