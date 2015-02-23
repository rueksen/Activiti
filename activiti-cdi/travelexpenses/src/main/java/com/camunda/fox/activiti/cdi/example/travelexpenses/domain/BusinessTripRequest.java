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
package com.camunda.fox.activiti.cdi.example.travelexpenses.domain;

import java.io.Serializable;

import javax.inject.Named;

import org.activiti.cdi.annotation.BusinessProcessScoped;


/**
 * 
 * @author Daniel Meyer
 */
@Named
@BusinessProcessScoped
public class BusinessTripRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String startDate;

	private String endDate;

	private String to;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return startDate + ", " + endDate + ": " + to;
	}

}
