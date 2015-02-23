package com.camunda.fox.demo.twitter.rest;

import java.util.ArrayList;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.camunda.fox.demo.twitter.Tweet;


public class TwitterAccountResource extends ServerResource {

  /**
   * easiest possible implementation for now, just remember the tweets in a list :-)
   */
  public static List<Tweet> publishedTweets = new ArrayList<Tweet>();

  @Get
  public List<Tweet> getPublishedTweets() {
    return publishedTweets;
  }

}
