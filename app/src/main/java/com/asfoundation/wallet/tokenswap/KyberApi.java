package com.asfoundation.wallet.tokenswap;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface KyberApi {

  @GET("/api/tokens/supported?chain=ropsten") Call<List<Object>> getAddresses();
}
