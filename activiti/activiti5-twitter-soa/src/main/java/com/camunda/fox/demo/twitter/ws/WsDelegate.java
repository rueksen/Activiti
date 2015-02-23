package com.camunda.fox.demo.twitter.ws;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.el.Expression;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

/**
 * IMPORTANT: You need a JDK to run this, otherwise you will get an exception
 * like java.lang.IllegalStateException: Unable to create JAXBContext for
 * generated packages: "ws.training.camunda.com" doesn't contain
 * ObjectFactory.class or jaxb.index
 * 
 */
public class WsDelegate implements JavaDelegate {

	private Expression wsdl;	
	private Expression operation;	
	private Expression parameters;	
	private Expression returnValue;

	public void execute(DelegateExecution execution) throws Exception {
		String wsdlString = (String)wsdl.getValue(execution);

		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(wsdlString);

		ArrayList<String> paramStrings = getParamStringValues(execution);
		Object response = client.invoke((String)operation.getValue(execution), paramStrings.toArray(new Object[0]));
		
		if (returnValue!=null) {
			String returnVariableName = (String) returnValue.getValue(execution);
			execution.setVariable(returnVariableName, response);
		}
	}

  private ArrayList<String> getParamStringValues(DelegateExecution execution) {
    ArrayList<String> paramStrings = new ArrayList<String>();
		if (parameters!=null) {			
			// Very easy implementation to fetch the parameters :-) Must be improved for real live
			StringTokenizer st = new StringTokenizer((String)parameters.getValue(execution), ",");
			while (st.hasMoreTokens()) {
				String token = st.nextToken().trim();
				paramStrings.add(token);
			}			
		}
    return paramStrings;
  }

// Test to access webservice via CXF	
//	public static void main(String[] args) throws Exception {
//		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//		Client client = dcf
//				.createClient("http://localhost:18080/TwitterService?wsdl");
//
//		Object[] parameters = new Object[] { "bernd", // userId
//				"5" // number of days
//		};
//		Object[] res = client.invoke("saveVacationApproval", parameters);
//		System.out.println("Echo response: " + res);
//
//	}
}
