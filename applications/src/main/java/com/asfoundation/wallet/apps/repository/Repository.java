package com.asfoundation.wallet.apps.repository;

import com.asfoundation.wallet.apps.Application;
import io.reactivex.Single;
import java.util.List;

public interface Repository {
  Single<List<Application>> getApps();
}
