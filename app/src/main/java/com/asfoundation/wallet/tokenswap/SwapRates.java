package com.asfoundation.wallet.tokenswap;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class SwapRates {

  Map<String, BigInteger> rates;

  public SwapRates() {
    rates = new HashMap<>();
  }

  public boolean saveRate(String tokenAddressFrom, String tokenAddressTo, BigInteger rate) {
    try {
      String addressesConcat = tokenAddressFrom.concat(tokenAddressTo);
      rates.put(addressesConcat, rate);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean exists(String tokenAddressFrom, String tokenAddressTo) {
    String addressesConcat = tokenAddressFrom.concat(tokenAddressTo);
    return (rates.containsKey(addressesConcat));
  }

  public BigInteger getRate(String tokenAddressFrom, String tokenAddressTo) {
    String addressesConcat = tokenAddressFrom.concat(tokenAddressTo);

    if (rates.containsKey(addressesConcat)) {
      return rates.get(addressesConcat);
    }
    return BigInteger.ZERO;
  }
  

}
