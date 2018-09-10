package com.asfoundation.wallet.tokenswap;

public class SwapPendingTransactions {

  private String lastSwapTxHash;

  public boolean isEmpty() {
    return lastSwapTxHash.isEmpty();
  }

  public void set(String txHash) {
    lastSwapTxHash = txHash;
  }

  public String get() {
    return lastSwapTxHash;
  }
}
