package com.asfoundation.wallet.apps;

public class App {
  private final String name;
  private final double rating;
  private final String iconUrl;

  public App(String name, double rating, String iconUrl) {
    this.name = name;
    this.rating = rating;
    this.iconUrl = iconUrl;
  }

  public String getName() {
    return name;
  }

  @Override public int hashCode() {
    return name.hashCode();
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof App)) return false;

    App app = (App) o;

    return name.equals(app.name);
  }

  @Override public String toString() {
    return "App{" + "name='" + name + '\'' + '}';
  }

  public double getRating() {
    return rating;
  }

  public String getIconUrl() {
    return iconUrl;
  }
}
