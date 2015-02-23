package com.camunda.fox.demo.twitter.rest;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

public class RestServer extends Application {

  /**
   * Creates a root Restlet that will receive all incoming calls.
   */
  @Override
  public synchronized Restlet createInboundRoot() {
    Router router = new Router(getContext());

    router.attach("/tweet/", TweetResource.class);
    router.attach("/account/{accountName}/list", TwitterAccountResource.class);

    return router;
  }

  public static Component createRestComponent() throws Exception {
    // Create a new Component.
    Component component = new Component();

    // Add a new HTTP server listening on port 8182.
    component.getServers().add(Protocol.HTTP, 8182);
    // Attach the sample application.
    component.getDefaultHost().attach("/rest", new RestServer());
    
    // Start the component.
    component.start();
    
    return component;
  }
  
  public static void main(String[] args) throws Exception {
    createRestComponent();    
    System.out.println("Now you can query tweets on http://localhost:8182/rest/account/xxx/list via GET");
    System.out.println("And post new tweets (as XML, JSON, Java-Binary) on http://localhost:8182/rest/tweet/ via POST");
  }
}
