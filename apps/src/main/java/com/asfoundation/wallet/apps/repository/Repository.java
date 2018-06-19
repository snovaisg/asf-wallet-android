package com.asfoundation.wallet.apps.repository;

import com.asfoundation.wallet.apps.App;
import io.reactivex.Single;
import java.util.List;

public interface Repository {
  Single<List<App>> getApps();
}
