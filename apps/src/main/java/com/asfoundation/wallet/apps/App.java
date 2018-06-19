package com.asfoundation.wallet.apps;

public class App {
  private final String name;

  public App(String name) {

    this.name = name;
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
}
