package com.camunda.fox.demo.twitter;

import org.activiti.engine.test.ActivitiTestCase;
import org.restlet.Client;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Preference;
import org.restlet.data.Protocol;
import org.restlet.data.Reference;

/**
 * For this test case, the Activiti Server must be running and the TwitterWS
 * process must be deployed!
 */
public class RestClientTestCase extends ActivitiTestCase {

	private String user = "kermit";
	private String passwd = "kermit";
	private String processName = "TwitterWS";
	private String processVersion = "1";
	// TODO: change the processId according to your database
	private String processId = "716";

	public void testApprovedWs() {
		Client restletClient = new Client(new Context(), Protocol.HTTP);

		Request request = new Request(Method.POST, new Reference(
				"http://localhost:8080/activiti-rest/service/process-instance"));
		request.getClientInfo().getAcceptedMediaTypes()
				.add(new Preference<MediaType>(MediaType.APPLICATION_JSON));

		ChallengeResponse authentication = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, user, passwd);
		request.setChallengeResponse(authentication);

		String content = "Hello from the JUnit test";
		String email = "activiti@camunda.com";

		request.setEntity("{" //
				+ "\"processDefinitionId\":\"" + processName + ":" + processVersion + ":" + processId + "\"," //
				+ "\"content\":\"" + content + "\"," //
				+ "\"email\":\"" + email + "\"" //
				+ "}", MediaType.APPLICATION_JSON);
		Response response = restletClient.handle(request);

		String responseText = response.getEntityAsText();

		assertTrue("NO successfull response, see response:\n" + responseText,
				responseText.contains("\"id\":"));
	}

}