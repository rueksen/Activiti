package com.camunda.fox.demo.twitter.rest;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.el.Expression;
import org.activiti.engine.impl.el.FixedValue;
import org.restlet.Client;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.data.Reference;

import com.thoughtworks.xstream.XStream;


public class RestDelegate implements JavaDelegate {

	private FixedValue method;
	private Expression url;	
	private FixedValue returnValue;
	
  private Expression parameter;	

	private FixedValue basicUser;
	private FixedValue basicPasswd;

	public void execute(DelegateExecution execution) throws Exception {
		String methodName = (String)method.getValue(execution);
		
		// TODO: Clean up code and refactor
		if ("GET".equals(methodName)) {
		    Client restletClient = new Client(new Context(), Protocol.HTTP);
		    String urlString = (String)url.getValue(execution);
		    Request request = new Request(Method.GET, new Reference(urlString));
	
		    if (basicUser!=null && basicPasswd!= null) {
			    ChallengeResponse authentication = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, (String)basicUser.getValue(execution), (String)basicPasswd.getValue(execution));
			    request.setChallengeResponse(authentication);
		    }
		    
		    Response response = restletClient.handle(request);
		    String responseAsText = response.getEntityAsText();
		    
			if (responseAsText!=null) {
				String returnVariableName = (String) returnValue.getValue(execution);
				execution.setVariable(returnVariableName, responseAsText);
			}
		}
		else if ("POST".equals(methodName)) {
      Client restletClient = new Client(new Context(), Protocol.HTTP);
      String urlString = (String)url.getValue(execution);
      Request request = new Request(Method.POST, new Reference(urlString));
      
      String variableasXml = getVariableasXml(execution, (String)parameter.getValue(execution));
      if (variableasXml != null) {
        request.setEntity(variableasXml, MediaType.APPLICATION_ALL_XML);
      }

      if (basicUser!=null && basicPasswd!= null) {
        ChallengeResponse authentication = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, (String)basicUser.getValue(execution), (String)basicPasswd.getValue(execution));
        request.setChallengeResponse(authentication);
      }
      
      Response response = restletClient.handle(request);
      String responseAsText = response.getEntityAsText();
      
    if (responseAsText!=null) {
      String returnVariableName = (String) returnValue.getValue(execution);
      execution.setVariable(returnVariableName, responseAsText);
    }
  }
		else {
			throw new IllegalArgumentException("Method '" + methodName + "' not supported from RestDelegate at the moment"); 
		}
	}

  private String getVariableasXml(DelegateExecution execution, String variableName) {
    Object variable = execution.getVariable(variableName);
    if (variable==null)
      return null;
    
    String result = new XStream().toXML(variable);
    System.out.println(result);
    return result;
  }
}
