package com.camunda.fox.trainingmanager.view.security;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * This filter makes sure that if a page below /app is requested, 
 * that the user is logged in. 
 * 
 * @author Daniel Meyer
 */
//@WebFilter(urlPatterns={"/app/*"}, description="Login Filter")
public class LoginFilter implements Filter {
    
  private FilterConfig config = null;
  
  @Inject Authenticator authenticator;
 
  @Override
  public void init(FilterConfig config) throws ServletException {   
    this.config = config;
  }
 
  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
    if(!loggedIn()) {
      req.getServletContext().getRequestDispatcher("/dologin.jsf").forward(req, resp);   
    }
    chain.doFilter(req, resp);
  }
 
  protected boolean loggedIn() {
    return authenticator.isLoggedIn();
  }

  @Override
  public void destroy() {
    config.getServletContext().log("TimerFilter destroyed");
  }
 
}
