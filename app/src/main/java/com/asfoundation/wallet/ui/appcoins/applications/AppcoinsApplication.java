package com.asfoundation.wallet.ui.appcoins.applications;

public class AppcoinsApplication {

  private final String name;
  private final double rating;
  private final String iconUrl;
  private final String featuredGraphic;

  public AppcoinsApplication(String name, double rating, String iconUrl, String featuredGraphic) {
    this.name = name;
    this.rating = rating;
    this.iconUrl = iconUrl;
    this.featuredGraphic = featuredGraphic;
  }

  @Override public String toString() {
    return "AppcoinsApplication{"
        + "name='"
        + name
        + '\''
        + ", rating="
        + rating
        + ", iconUrl='"
        + iconUrl
        + '\''
        + '}';
  }

  public String getName() {
    return name;
  }

  public double getRating() {
    return rating;
  }

  public String getIcon() {
    return iconUrl;
  }

  public String getFeaturedGraphic() {
    return featuredGraphic;
  }
}