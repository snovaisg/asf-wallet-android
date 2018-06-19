package com.asfoundation.wallet.apps.repository.webservice.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

  @SerializedName("status") @Expose private String status;
  @SerializedName("time") @Expose private Time time;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Time getTime() {
    return time;
  }

  public void setTime(Time time) {
    this.time = time;
  }
}
