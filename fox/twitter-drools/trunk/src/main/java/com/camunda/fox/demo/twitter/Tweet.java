package com.camunda.fox.demo.twitter;

import java.io.Serializable;


public class Tweet implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  private String email;
  private String twitterAccount;
  private String content;
  
  private boolean approved = true;
  
  private String rejectionComment;
  
  public void reject(String reason) {
    this.setApproved(false);
    if (rejectionComment==null) {
      rejectionComment = reason;
    }
    else {
      rejectionComment += " AND " + reason;
    }
  }
  
  public String getRejectionComment() {
    return rejectionComment;
  }
  
  public void setRejectionComment(String rejectionComment) {
    this.rejectionComment = rejectionComment;
  }

  public boolean isApproved() {
    return approved;
  }
  
  public void setApproved(boolean approved) {
    this.approved = approved;
  }

  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getTwitterAccount() {
    return twitterAccount;
  }
  
  public void setTwitterAccount(String twitterAccount) {
    this.twitterAccount = twitterAccount;
  }
  
  public String getContent() {
    return content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }

}
