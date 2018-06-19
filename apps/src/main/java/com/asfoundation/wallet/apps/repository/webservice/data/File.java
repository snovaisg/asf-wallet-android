package com.asfoundation.wallet.apps.repository.webservice.data;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class File {

  @SerializedName("vername") @Expose private String vername;
  @SerializedName("vercode") @Expose private int vercode;
  @SerializedName("md5sum") @Expose private String md5sum;
  @SerializedName("filesize") @Expose private int filesize;
  @SerializedName("tags") @Expose private List<Object> tags = null;

  public String getVername() {
    return vername;
  }

  public void setVername(String vername) {
    this.vername = vername;
  }

  public int getVercode() {
    return vercode;
  }

  public void setVercode(int vercode) {
    this.vercode = vercode;
  }

  public String getMd5sum() {
    return md5sum;
  }

  public void setMd5sum(String md5sum) {
    this.md5sum = md5sum;
  }

  public int getFilesize() {
    return filesize;
  }

  public void setFilesize(int filesize) {
    this.filesize = filesize;
  }

  public List<Object> getTags() {
    return tags;
  }

  public void setTags(List<Object> tags) {
    this.tags = tags;
  }
}
