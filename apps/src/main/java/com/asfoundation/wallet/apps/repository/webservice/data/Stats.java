package com.asfoundation.wallet.apps.repository.webservice.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stats {

  @SerializedName("downloads") @Expose private int downloads;
  @SerializedName("pdownloads") @Expose private int pdownloads;
  @SerializedName("rating") @Expose private Rating rating;
  @SerializedName("prating") @Expose private Prating prating;

  public int getDownloads() {
    return downloads;
  }

  public void setDownloads(int downloads) {
    this.downloads = downloads;
  }

  public int getPdownloads() {
    return pdownloads;
  }

  public void setPdownloads(int pdownloads) {
    this.pdownloads = pdownloads;
  }

  public Rating getRating() {
    return rating;
  }

  public void setRating(Rating rating) {
    this.rating = rating;
  }

  public Prating getPrating() {
    return prating;
  }

  public void setPrating(Prating prating) {
    this.prating = prating;
  }
}
