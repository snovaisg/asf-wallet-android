package com.asfoundation.wallet.apps.repository.webservice.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datalist {

  @SerializedName("total") @Expose private int total;
  @SerializedName("count") @Expose private int count;
  @SerializedName("offset") @Expose private int offset;
  @SerializedName("limit") @Expose private int limit;
  @SerializedName("next") @Expose private int next;
  @SerializedName("hidden") @Expose private int hidden;
  @SerializedName("loaded") @Expose private boolean loaded;
  @SerializedName("list") @Expose private java.util.List<List> list = null;

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getOffset() {
    return offset;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  public int getNext() {
    return next;
  }

  public void setNext(int next) {
    this.next = next;
  }

  public int getHidden() {
    return hidden;
  }

  public void setHidden(int hidden) {
    this.hidden = hidden;
  }

  public boolean isLoaded() {
    return loaded;
  }

  public void setLoaded(boolean loaded) {
    this.loaded = loaded;
  }

  public java.util.List<List> getList() {
    return list;
  }

  public void setList(java.util.List<List> list) {
    this.list = list;
  }
}
