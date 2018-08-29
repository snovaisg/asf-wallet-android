package com.asfoundation.wallet.tokenswap;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.web3j.utils.Convert;

public class SwapPresenter {

  private SwapView view;
  private SwapInteractor swapInteractor;
  private ResponseListener resListenner;
  private ResponseListener<String> transactionSentListener;

  public SwapPresenter(SwapView view, SwapInteractor swapInteractor) {
    this.view = view;
    this.swapInteractor = swapInteractor;
    this.transactionSentListener = new ResponseListener<String>() {
      @Override public void onResponse(String s) {
      }

      @Override public void onError(Throwable error) {
        error.printStackTrace();
      }
    };
  }

  public void swapEtherToToken(String destToken, String amount, String ToAddress) {
    swapInteractor.swapEtherToToken(destToken, amount, ToAddress, transactionSentListener);
  }

  public void swapTokenToEtherApprove(String srcToken, String destToken, String amount,
      String ToAddress, String approveAddress) {
    resListenner = new ResponseListener() {
      @Override public void onResponse(Object o) {
        callSwapTokenToEther(srcToken, destToken, amount, ToAddress, transactionSentListener);
      }

      @Override public void onError(Throwable error) {
        error.printStackTrace();
      }
    };
    swapInteractor.approve(srcToken, destToken, amount, approveAddress, resListenner);
  }

  public void showRatio(String srcToken, String destToken, String srcTokenAddress,
      String destTokenAddress) {
    String tokenAmount = "1";
    BigInteger rateWei = swapInteractor.getRates(srcTokenAddress, destTokenAddress, tokenAmount);
    //Convert to UI/UX
    BigDecimal rate = Convert.fromWei(rateWei.toString(), Convert.Unit.ETHER);
    String ratio = "1 " + srcToken + " = " + rate.toString() + " " + destToken;
    view.showRates(ratio);
  }

  public void amountChanged(String srcTokenAddress, String destTokenAddress, String userInputStr,
      String source) {
    float userInput;
    float amount;
    try {
      userInput = Float.parseFloat(userInputStr.toString());
      float rate = calcRate(srcTokenAddress, destTokenAddress, userInputStr.toString());
      if (source.equals(view.getTo())) {
        amount = 1 / rate * userInput;
        String amount_str = String.format("%.6f", amount);
        view.setTextTokenFrom(amount_str);
      } else {
        amount = rate * userInput;
        String amount_str = String.format("%.6f", amount);
        view.setTextTokenTo(amount_str);
      }
    } catch (Exception e) {
      if (source.equals(view.getFrom())) {
        view.setTextTokenTo("0");
      } else {
        view.setTextTokenFrom("0");
      }
    }
  }

  public float calcRate(String srcToken, String destToken, String amount) {
    try {

      BigInteger rateWei = swapInteractor.getRates(srcToken, destToken, amount);
      float rate = Convert.fromWei(rateWei.toString(), Convert.Unit.ETHER)
          .floatValue();
      return rate;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  public void swap(String srcToken, String destToken, String amount, String toAddress,
      String approveAddress) {
    String ether_add = "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
    if (srcToken.equals(ether_add)) {
      swapEtherToToken(destToken, amount, toAddress);
    } else if ((!srcToken.equals(ether_add)) && (destToken.equals(ether_add))) {
      swapTokenToEther(srcToken, destToken, amount, toAddress, approveAddress);
    }
  }

  public void swapTokenToEther(String srcToken, String destToken, String amount, String toAddress,
      String approveAddress) {
    float allowance = checkAllowance(approveAddress, srcToken);
    if (allowance >= Float.parseFloat(amount)) {
      swapInteractor.tokenToEther(srcToken, destToken, amount, toAddress, transactionSentListener);
    } else {
      //need to approve first
      swapTokenToEtherApprove(srcToken, destToken, amount, toAddress, approveAddress);
    }
  }

  public float checkAllowance(String spender, String toAddress) {
    return swapInteractor.getAllowance(spender, toAddress);
  }

  public void callSwapTokenToEther(String srcToken, String destToken, String amount,
      String toAddress, ResponseListener<String> listenner) {
    swapInteractor.tokenToEther(srcToken, destToken, amount, toAddress, listenner);
  }

}
