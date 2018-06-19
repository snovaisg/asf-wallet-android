package com.asfoundation.wallet.apps;

import com.asfoundation.wallet.apps.repository.webservice.data.Application;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface AppsApi {
  @GET("listApps/store_name=asf-store/group_name=group-10609") Single<Application> getApps();
}
