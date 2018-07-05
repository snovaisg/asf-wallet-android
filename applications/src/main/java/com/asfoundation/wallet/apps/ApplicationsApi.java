package com.asfoundation.wallet.apps;

import com.asfoundation.wallet.apps.repository.webservice.data.Application;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApplicationsApi {
  String API_BASE_URL = "https://ws75.aptoide.com/api/7/";

  @GET("listApps/store_name=asf-store/group_name=group-10609/order=rand/limit=10")
  Single<Application> getApplications();
}
