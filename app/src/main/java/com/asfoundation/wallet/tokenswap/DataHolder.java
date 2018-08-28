package com.asfoundation.wallet.tokenswap;

public class DataHolder {
  private static final DataHolder holder = new DataHolder();
  private long nonce = -1;

  public static DataHolder getInstance() {
    return holder;
  }

  public long getNonce() {
    return nonce;
  }

  public void setNonce(long nonce) {
    this.nonce = nonce;
  }
}
