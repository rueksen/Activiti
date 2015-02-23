package com.camunda.sample.ws.bank;


import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ArquillianTestCase {

  @Deployment  
  public static WebArchive createDeployment() {
    MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");
    
    return ShrinkWrap.create(WebArchive.class, "test-webservice.war")
            // prepare as process application archive for fox platform
            .addAsLibraries(resolver.artifact("com.camunda.fox:fox-platform-client").resolveAsFiles())
            .addAsWebResource(EmptyAsset.INSTANCE, "WEB-INF/classes/META-INF/processes.xml")
            .addAsWebResource(EmptyAsset.INSTANCE, "WEB-INF/classes/META-INF/beans.xml")
            // add your own classes (could be done one by one as well)
            .addPackages(true, "com.camunda.sample.ws.bank")
            .addPackages(true, "com.thomas_bayer.blz")
            // add process definition
            .addAsResource("test-webservice.bpmn20.xml");    
  }

  @Inject
  private RuntimeService runtimeService;

  @Inject
  private TaskService taskService;

  @Test
  public void test() throws Exception {
   Map<String, Object> variables = new HashMap<String, Object>();
   variables.put("blz", "12030000");
   ProcessInstance pi = runtimeService.startProcessInstanceByKey("test-webservice", variables);
   
   assertEquals(1, taskService.createTaskQuery().processInstanceId(pi.getId()).count());
   Object bankName = runtimeService.getVariable(pi.getId(), "bankName");
   assertEquals("Deutsche Kreditbank Berlin", bankName);
  }
}
