package com.asfoundation.wallet.tokenswap;

public interface SwapView {
  void clickedGetRates();

  void showRates(String rates);

  void clickedSwap();

  void setTextTokenTo(String amount);

  void setTextTokenFrom(String amount);

  void showToast();

  String getTo();

  String getFrom();
}
