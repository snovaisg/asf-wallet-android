package com.asfoundation.wallet.tokenswap;

public interface SwapView {

  void showRates(String rates);

  void clickedSwap();

  void setTextTokenTo(String amount);

  void setTextTokenFrom(String amount);

  void showBalances(String text);

  String getTo();

  String getFrom();
}
