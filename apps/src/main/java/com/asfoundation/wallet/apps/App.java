package com.asfoundation.wallet.apps;

public class App {
  private final String name;
  private final double rating;
  private final String iconUrl;
  private final String featuredGraphic;

  public App(String name, double rating, String iconUrl, String featuredGraphic) {
    this.name = name;
    this.rating = rating;
    this.iconUrl = iconUrl;
    this.featuredGraphic = featuredGraphic;
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

  public String getFeaturedGraphic() {
    return featuredGraphic;
  }
}
