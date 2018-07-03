package com.asfoundation.wallet.apps.repository.webservice.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Time {

  @SerializedName("seconds") @Expose private double seconds;
  @SerializedName("human") @Expose private String human;

  public double getSeconds() {
    return seconds;
  }

  public void setSeconds(double seconds) {
    this.seconds = seconds;
  }

  public String getHuman() {
    return human;
  }

  public void setHuman(String human) {
    this.human = human;
  }
}
