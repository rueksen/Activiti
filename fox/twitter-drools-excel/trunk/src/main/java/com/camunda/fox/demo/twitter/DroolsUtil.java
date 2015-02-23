package com.camunda.fox.demo.twitter;

import java.util.Arrays;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.DecisionTableConfiguration;
import org.drools.builder.DecisionTableInputType;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;

/**
 * This class contain the Drools utilities
 */
public class DroolsUtil {

  private DecisionTableConfiguration dtableconfiguration;
  private KnowledgeBuilder kbuilder;
  private KnowledgeBase kbase;
  private boolean print = false;

  public void loadDecisionTable(String table) throws Exception {
    dtableconfiguration = KnowledgeBuilderFactory.newDecisionTableConfiguration();
    dtableconfiguration.setInputType(DecisionTableInputType.XLS);

    kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

    kbuilder.add(ResourceFactory.newClassPathResource(table, getClass()), ResourceType.DTABLE, dtableconfiguration);

    // for debugging purposes
    if (print) {
      printDLR(table);
    }

    if (kbuilder.hasErrors()) {
      StringBuffer message = new StringBuffer();
      KnowledgeBuilderErrors errors = kbuilder.getErrors();
      for (KnowledgeBuilderError error : errors) {
        message.append("\n").append(error.getMessage());
      }
      throw new Exception("Errors parsing decision table: " + message.toString());
    }
  }

  public void executeDrools(Tweet t) throws Exception {
    kbase = KnowledgeBaseFactory.newKnowledgeBase();
    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

    // typical decision tables are used statelessly
    StatelessKnowledgeSession ksession = kbase.newStatelessKnowledgeSession();

    // insert data into Drools
    ksession.execute(Arrays.asList(new Object[] { t }));
  }

  public void executeDrools(Tweet[] t) throws Exception {
    kbase = KnowledgeBaseFactory.newKnowledgeBase();
    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

    // typical decision tables are used statelessly
    StatelessKnowledgeSession ksession = kbase.newStatelessKnowledgeSession();

    // insert data into Drools
    ksession.execute(Arrays.asList(t));
  }

  private void printDLR(String table) {
    System.out.println(new SpreadsheetCompiler().compile(this.getClass().getResourceAsStream(table), InputType.XLS));
  }
}
