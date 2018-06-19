package com.asfoundation.wallet.ui.appcoins.applications;

public class AppcoinsApplication {

  private final String name;

  public AppcoinsApplication(String name) {
    this.name = name;
  }

  @Override public String toString() {
    return "AppcoinsApplication{" + "name='" + name + '\'' + '}';
  }
}