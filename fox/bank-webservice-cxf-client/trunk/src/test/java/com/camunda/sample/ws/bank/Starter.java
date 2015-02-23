package com.camunda.sample.ws.bank;
import com.thomas_bayer.blz.BLZService;
import com.thomas_bayer.blz.DetailsType;



public class Starter {

  public static void main(String[] args) {
    DetailsType bank = new BLZService().getBLZServiceSOAP12PortHttp().getBank("67352565");
    System.out.println(bank.getBezeichnung());
  }
}
