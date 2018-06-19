package com.asfoundation.wallet.apps;

import com.asfoundation.wallet.apps.repository.NetworkAppsRepository;
import com.asfoundation.wallet.apps.repository.Repository;
import io.reactivex.Single;
import java.util.List;

public class Applications {
  private final Repository appsService;

  public Applications(Repository appsService) {
    this.appsService = appsService;
  }

  public Single<List<App>> getApps() {
    return appsService.getApps();
  }

  public static class Builder {
    private Repository repository;
    private AppsApi api;

    public Builder setRepository(Repository repository) {
      this.repository = repository;
      return this;
    }

    public Builder setApi(AppsApi api) {
      this.api = api;
      return this;
    }

    public Applications build() {
      if (api == null) {
        throw new IllegalArgumentException("you must provide an api implementation");
      }
      if (repository == null) {
        repository = new NetworkAppsRepository(api);
      }
      return new Applications(repository);
    }
  }
}
