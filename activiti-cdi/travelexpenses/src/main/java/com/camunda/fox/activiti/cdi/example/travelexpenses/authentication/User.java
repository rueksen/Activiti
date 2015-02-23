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
package com.camunda.fox.activiti.cdi.example.travelexpenses.authentication;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author Daniel Meyer
 */
@Named
@SessionScoped
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  private String username;

  public boolean isLoggedIn() {
    return username != null;
  }

  public void setLoggedIn(String username) {
    this.username = username;
  }

  public void logout() {
    // TODO: there are certainly better ways to logout...
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext ec = context.getExternalContext();
    HttpServletRequest request = (HttpServletRequest) ec.getRequest();
    request.getSession(false).invalidate();
    this.username = null;
  }

  public String getUsername() {
    return username;
  }

}
