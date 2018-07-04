package com.asfoundation.wallet.apps.repository.webservice.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class List {

  @SerializedName("id") @Expose private int id;
  @SerializedName("name") @Expose private String name;
  @SerializedName("package") @Expose private String _package;
  @SerializedName("uname") @Expose private String uname;
  @SerializedName("size") @Expose private int size;
  @SerializedName("icon") @Expose private String icon;
  @SerializedName("graphic") @Expose private String graphic;
  @SerializedName("added") @Expose private String added;
  @SerializedName("modified") @Expose private String modified;
  @SerializedName("updated") @Expose private String updated;
  @SerializedName("uptype") @Expose private String uptype;
  @SerializedName("store") @Expose private Store store;
  @SerializedName("file") @Expose private File file;
  @SerializedName("stats") @Expose private Stats stats;
  @SerializedName("obb") @Expose private Object obb;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPackage() {
    return _package;
  }

  public void setPackage(String _package) {
    this._package = _package;
  }

  public String getUname() {
    return uname;
  }

  public void setUname(String uname) {
    this.uname = uname;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getGraphic() {
    return graphic;
  }

  public void setGraphic(String graphic) {
    this.graphic = graphic;
  }

  public String getAdded() {
    return added;
  }

  public void setAdded(String added) {
    this.added = added;
  }

  public String getModified() {
    return modified;
  }

  public void setModified(String modified) {
    this.modified = modified;
  }

  public String getUpdated() {
    return updated;
  }

  public void setUpdated(String updated) {
    this.updated = updated;
  }

  public String getUptype() {
    return uptype;
  }

  public void setUptype(String uptype) {
    this.uptype = uptype;
  }

  public Store getStore() {
    return store;
  }

  public void setStore(Store store) {
    this.store = store;
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public Stats getStats() {
    return stats;
  }

  public void setStats(Stats stats) {
    this.stats = stats;
  }

  public Object getObb() {
    return obb;
  }

  public void setObb(Object obb) {
    this.obb = obb;
  }
}
