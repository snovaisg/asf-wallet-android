package com.asfoundation.wallet.tokenswap;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class KyberAddressesApi {

  public void getJson() {

    Gson gson = new GsonBuilder().setLenient()
        .create();

    Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://tracker.kyber.network/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    // Retrofit instance which was created earlier
    Retrofit retrofit = builder.build();

    KyberApi client = retrofit.create(KyberApi.class);

    Call<List<Object>> call = client.getAddresses();
    call.enqueue(new Callback<List<Object>>() {
      @Override public void onResponse(Call call, Response response) {
        Log.d("swpLog9", response.body()
            .toString());
      }

      @Override public void onFailure(Call call, Throwable t) {
        Log.d("swpLog9.1", t.getMessage());
      }
    });
  }
}
