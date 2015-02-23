/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.camunda.fox.activiti.cdi.example.travelexpenses.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.cdi.BusinessProcess;
import org.activiti.cdi.annotation.StartProcess;

import com.camunda.fox.activiti.cdi.example.travelexpenses.authentication.User;

/**
 * 
 * @author Daniel Meyer
 */
@Named
@Stateless
public class BusinessTripRequestService {

	@Inject 
	private User user;

	@Inject 
	private BusinessProcess businessProcess;

	@StartProcess("authorizeBusinessTripRequest")
	public String submitRequest() {
	  // any process variables that are set on the businessProcess bean before a process is started are 
	  // passed in to the process instance as process variables 
		businessProcess.setVariable("businessTripRequesterUsername", user.getUsername());
		return "success";
	}

}
