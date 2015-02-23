package com.camunda.fox.demo.orderconfirmation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class JavaDelegationBean {

  private String simulatedErpError = null;
  private String simulatedProductionError = null;
  private String simulatedValidationError = null;
  
  public void validateOrder() {
    System.out.println("validate order; error: '" + simulatedValidationError + "'");
    if (simulatedValidationError!=null && simulatedValidationError.length()>0) {
      throw new BusinessException(simulatedValidationError);
    }
  }

  public void createOrder() {
    callErp();
  }

  public void createInvoice() {
    callErp();
  }

  private void callErp() {
    System.out.println("call erp; error: '" + simulatedValidationError + "'");
    if (simulatedErpError!=null && simulatedErpError.length()>0) {
      throw new BusinessException(simulatedErpError);
    }
  }

  public void planProduction() {
    System.out.println("plan production; error: '" + simulatedValidationError + "'");
    if (simulatedProductionError!=null && simulatedProductionError.length()>0) {
      throw new BusinessException(simulatedProductionError);
    }
  }
  
  public void apply() {    
  }

  public String getSimulatedErpError() {
    return simulatedErpError;
  }

  public void setSimulatedErpError(String simulatedErpError) {
    this.simulatedErpError = simulatedErpError;
  }

  public String getSimulatedProductionError() {
    return simulatedProductionError;
  }

  public void setSimulatedProductionError(String simulatedProductionError) {
    this.simulatedProductionError = simulatedProductionError;
  }

  public String getSimulatedValidationError() {
    return simulatedValidationError;
  }

  public void setSimulatedValidationError(String simulatedValidationError) {
    this.simulatedValidationError = simulatedValidationError;
  }

}
