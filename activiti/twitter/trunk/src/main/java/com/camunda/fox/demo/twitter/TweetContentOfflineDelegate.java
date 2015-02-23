package com.camunda.fox.demo.twitter;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;


public class TweetContentOfflineDelegate implements JavaDelegate {

  public void execute(DelegateExecution execution) throws Exception {      
    String content = (String) execution.getVariable("content");
    
    System.out.println("\n\n\n######\n\n\n");
    System.out.println("NOW WE WOULD TWITTER: '" + content + "'");
    System.out.println("\n\n\n######\n\n\n");
      
//    AccessToken accessToken = new AccessToken("220324559-8hWDVUXMSOaAnmtNNwBuNuhGJ6hOGwNdWHqhdOsU", "sGBZafB7saWYMwnPQGjjwU9Ggr0IJYkmPdyAFU5PI");
//    Twitter twitter = new TwitterFactory().getOAuthAuthorizedInstance("HW62M0Rxtt39CbgdgP1og", "TJHNqolmPD6aHPtX8ec5Xp5zgIJcsMBTkwMpGCqdGuk", accessToken);
//    twitter.updateStatus(content);
  }

}
