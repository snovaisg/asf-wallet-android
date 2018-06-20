package com.asfoundation.wallet.apps.repository;

import com.asfoundation.wallet.apps.App;
import com.asfoundation.wallet.apps.AppsApi;
import com.asfoundation.wallet.apps.repository.webservice.data.Application;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;

public class NetworkAppsRepository implements Repository {
  private final AppsApi appsApi;

  public NetworkAppsRepository(AppsApi appsApi) {
    this.appsApi = appsApi;
  }

  @Override public Single<List<App>> getApps() {
    return appsApi.getApps()
        .map(this::map);
  }

  private List<App> map(Application applications) {
    ArrayList<App> apps = new ArrayList<>();
    for (com.asfoundation.wallet.apps.repository.webservice.data.List application : applications
        .getDatalist()
        .getList()) {
      apps.add(new App(application.getName(), application.getStats()
          .getPrating()
          .getAvg(), application.getIcon()));
    }
    return apps;
  }
}
