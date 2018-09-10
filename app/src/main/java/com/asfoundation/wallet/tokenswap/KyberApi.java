package com.asfoundation.wallet.tokenswap;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface KyberApi {

  @GET("/api/tokens/supported?chain=ropsten") Call<List<AddressResponse>> getAddresses();

  class AddressResponse {
    String symbol;
    String cmcName;

    @Override public String toString() {
      return "AddressResponse{" + "symbol='" + symbol + '\'' + ", cmcName='" + cmcName + '\'' + '}';
    }

    public String getSymbol() {
      return symbol;
    }

    public void setSymbol(String symbol) {
      this.symbol = symbol;
    }

    public String getCmcName() {
      return cmcName;
    }

    public void setCmcName(String cmcName) {
      this.cmcName = cmcName;
    }
  }
}
