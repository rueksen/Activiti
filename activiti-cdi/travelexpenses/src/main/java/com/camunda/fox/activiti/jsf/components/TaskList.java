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
package com.camunda.fox.activiti.jsf.components;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.task.Task;

import com.camunda.fox.activiti.cdi.example.travelexpenses.authentication.User;


/**
 * TODO: implement pagination
 * 
 * @author meyerd
 */
@Named
@ConversationScoped
public class TaskList implements Serializable {

  private static final long serialVersionUID = 1L;

  @Inject
  private ProcessEngine processEngine;
  
  @Inject
  private User user;

  public List<Task> getList() {
    return processEngine.getTaskService()
            .createTaskQuery()
            .taskAssignee(user.getUsername())
            .list();
  }

  public List<Task> getTasksByName(String taskName) {
    return processEngine.getTaskService().createTaskQuery()
            .taskAssignee(user.getUsername())
            .taskName(taskName)
            .list();
  }

  public String getFormKey(String taskId) {
    return processEngine.getFormService()
            .getTaskFormData(taskId)
            .getFormKey();
  }

}
