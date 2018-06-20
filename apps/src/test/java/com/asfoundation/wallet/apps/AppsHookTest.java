package com.asfoundation.wallet.apps;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class AppsHookTest {

  private Applications appsHook;
  private List<App> appsList;

  @Before public void setUp() {
    appsList = new ArrayList<>();
    appsList.add(new App("name", 5.0, "icon"));
    appsHook = new Applications(() -> Single.just(appsList));
  }

  @Test public void getApps() {
    TestObserver<List<App>> subscriber = new TestObserver<>();
    appsHook.getApps()
        .subscribe(subscriber);
    subscriber.assertValue(appsList);
  }
}