package com.camunda.fox.demo.twitter.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.camunda.fox.demo.twitter.Tweet;

@WebService
public interface TwitterService {

	@WebMethod 
	public void createTweet(@WebParam(name="email")String email, @WebParam(name="content")String content);
	
	@WebMethod 
	public List<Tweet> getPublishedTweets();
	
}
