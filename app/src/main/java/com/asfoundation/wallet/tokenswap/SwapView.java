package com.asfoundation.wallet.tokenswap;

public interface SwapView {

  void showRates(String rates);

  void clickedSwap();

  void setTextTokenTo(String amount);

  void setTextTokenFrom(String amount);

  void showBalanceTitle(String text);

  void showBalanceTokenFrom(String text);

  void showBalanceTokenTo(String text);

  String getTo();

  String getFrom();
}
