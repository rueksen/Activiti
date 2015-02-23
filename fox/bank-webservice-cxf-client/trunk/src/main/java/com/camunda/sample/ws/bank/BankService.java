package com.camunda.sample.ws.bank;

import javax.inject.Named;

import com.thomas_bayer.blz.BLZService;
import com.thomas_bayer.blz.DetailsType;


@Named
public class BankService {

  public String getName(String blz) {
    DetailsType bank = new BLZService().getBLZServiceSOAP12PortHttp().getBank(blz);
    System.out.println(bank.getBezeichnung());
    return bank.getBezeichnung();
  }
}
