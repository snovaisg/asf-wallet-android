package com.asfoundation.wallet.apps.repository.webservice.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Application {

  @SerializedName("info") @Expose private Info info;
  @SerializedName("datalist") @Expose private Datalist datalist;

  public Info getInfo() {
    return info;
  }

  public void setInfo(Info info) {
    this.info = info;
  }

  public Datalist getDatalist() {
    return datalist;
  }

  public void setDatalist(Datalist datalist) {
    this.datalist = datalist;
  }
}
