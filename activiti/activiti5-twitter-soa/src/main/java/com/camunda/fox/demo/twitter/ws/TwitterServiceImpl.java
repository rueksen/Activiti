package com.camunda.fox.demo.twitter.ws;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import com.camunda.fox.demo.twitter.Tweet;

@WebService(endpointInterface = "com.camunda.fox.demo.twitter.ws.TwitterService")
public class TwitterServiceImpl implements TwitterService {

  /**
   * easiest possible implementation for now, just remember the tweets in a list :-)
   */
  private static List<Tweet> publishedTweets = new ArrayList<Tweet>();

  public void createTweet(String email, String content) {
    System.out.println("Now we tweet via Webservice: " + content);
    Tweet tweet = new Tweet();
    tweet.setEmail(email);
    tweet.setContent(content);
    publishedTweets.add(tweet);
  }

  public List<Tweet> getPublishedTweets() {
    return publishedTweets;
  }
}
