package com.camunda.fox.demo.twitter.ws;

import javax.xml.ws.Endpoint;

public class WebserviceServer {

	public static void main(String[] args) {
	  createWebserviceEnpoint();	
		System.out.println("Now listening on: http://localhost:18080/TwitterService?wsdl");
	}

  public static Endpoint createWebserviceEnpoint() {
    TwitterServiceImpl service = new TwitterServiceImpl();		
		return Endpoint.publish("http://localhost:18080/TwitterService", service);
  }
}
