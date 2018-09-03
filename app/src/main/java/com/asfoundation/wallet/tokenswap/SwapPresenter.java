package com.asfoundation.wallet.tokenswap;

import android.annotation.SuppressLint;
import android.util.Log;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
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
        String msg = "Error! Insufficient funds";
        Single.just("")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(ignorable -> view.showToast(msg));
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
        swapInteractor.swapTokenToEther(srcToken, destToken, amount, ToAddress,
            transactionSentListener);
      }

      @Override public void onError(Throwable error) {
        error.printStackTrace();
      }
    };
    swapInteractor.approve(srcToken, destToken, amount, approveAddress, resListenner);
  }

  @SuppressLint("CheckResult")
  public void showRatio(String srcToken, String destToken, String srcTokenAddress,
      String destTokenAddress) throws IOException {
    String tokenAmount = "1";
    swapInteractor.rxGetRates(srcTokenAddress, destTokenAddress, tokenAmount)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(rateWei -> {
          BigDecimal rate = Convert.fromWei(rateWei.toString(), Convert.Unit.ETHER);
          String ratio = "1 " + srcToken + " = " + rate.toString() + " " + destToken;
          view.showRates(ratio);
        }, throwable -> printError(throwable));
  }

  public void amountChangedRate(float rate, float userInput, String source) {
    try {
      float amount;
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

  @SuppressLint("CheckResult")
  public void rxAmountChanged(String srcTokenAddress, String destTokenAddress, String userInputStr,
      String source) throws IOException {
    if (userInputStr.toString()
        .isEmpty()) {
      view.setTextTokenFrom("");
      view.setTextTokenTo("");
      return;
    }
    float userInput = Float.parseFloat(userInputStr.toString());
    rxCalcRate(srcTokenAddress, destTokenAddress, userInputStr.toString()).subscribeOn(
        Schedulers.newThread())
        .subscribe(rate -> amountChangedRate(rate, userInput, source),
            throwable -> printError(throwable));
  }

  @SuppressLint("CheckResult") public void printError(Throwable e) {
    String msg = "Please check your connection to the Internet";
    Single.just("")
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(ignorable -> view.showToast(msg));
  }

  @SuppressLint("CheckResult")
  public Single<Float> rxCalcRate(String srcToken, String destToken, String amount)
      throws IOException {
    return swapInteractor.rxGetRates(srcToken, destToken, amount)
        .subscribeOn(Schedulers.newThread())
        .flatMap(rateWei -> {
          float rate = Convert.fromWei(rateWei.toString(), Convert.Unit.ETHER)
              .floatValue();
          return Single.just(rate);
        });
  }

  public void swap(String srcToken, String destToken, String amount, String toAddress, String approveAddress) {
    final String ether_add = "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
    //ether to token
    if (srcToken.equals(ether_add)) {
      swapEtherToToken(destToken, amount, toAddress);
    }
    //token to ether
    else if ((!srcToken.equals(ether_add)) && (destToken.equals(ether_add))) {
      swapTokenToEther(srcToken, destToken, amount, toAddress, approveAddress);
    }
    //token to token
    else if ((!srcToken.equals(ether_add)) && (!destToken.equals(ether_add))) {
      swapTokenToToken(srcToken, destToken, amount, toAddress, approveAddress);
    }
  }
  private void swapTokenToTokenApprove(String srcToken, String destToken, String amount,
      String toAddress, String approveAddress) {
    resListenner = new ResponseListener() {
      @Override public void onResponse(Object o) {
        swapInteractor.swapTokenToToken(srcToken, destToken, amount, toAddress,
            transactionSentListener);
      }

      @Override public void onError(Throwable error) {
        error.printStackTrace();
      }
    };
    swapInteractor.approve(srcToken, destToken, amount, approveAddress, resListenner);
  }

  private void swapTokenToToken(String srcToken, String destToken, String amount, String toAddress,
      String approveAddress) {
    float allowance = checkAllowance(approveAddress, srcToken);
    if (allowance >= Float.parseFloat(amount)) {
      swapInteractor.swapTokenToToken(srcToken, destToken, amount, toAddress,
          transactionSentListener);
    }
    swapTokenToTokenApprove(srcToken, destToken, amount, toAddress, approveAddress);
  }

  public void swapTokenToEther(String srcToken, String destToken, String amount, String toAddress,
      String approveAddress) {
    float allowance = checkAllowance(approveAddress, srcToken);
    if (allowance >= Float.parseFloat(amount)) {
      swapInteractor.swapTokenToEther(srcToken, destToken, amount, toAddress,
          transactionSentListener);
    } else {
      //need to approve first
      swapTokenToEtherApprove(srcToken, destToken, amount, toAddress, approveAddress);
    }
  }

  public float checkAllowance(String spender, String toAddress) {
    return swapInteractor.getAllowance(spender, toAddress);
  }

  @SuppressLint("CheckResult") public void rxGetAndShowBalance(String contractAddress) {
    swapInteractor.rxGetBalance(contractAddress)
        .subscribeOn(Schedulers.newThread())
        .subscribe(x -> {
          BigInteger balanceWei = swapInteractor.getResponseResult(x, contractAddress);
          Log.d("swapLog7", "result is " + balanceWei.toString());
        });
  }

  @SuppressLint("CheckResult")
  public void rxUpdateBalance(String tokenFrom, String tokenFromName, String source) {
    getBalance(tokenFrom).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(tokenFromString -> {
          if (source.equals("FROM")) {
            view.showBalanceTitle("balance");
            view.showBalanceTokenFrom(String.format(tokenFromName + " %.6f",
                Convert.fromWei(tokenFromString, Convert.Unit.ETHER)
                    .floatValue()));
          } else {
            view.showBalanceTitle("balance");
            view.showBalanceTokenTo(String.format(tokenFromName + " %.6f",
                Convert.fromWei(tokenFromString, Convert.Unit.ETHER)
                    .floatValue()));
          }
        }, throwable -> printError(throwable));
  }

  public Single<String> getBalance(String tokenAdress) {
    final String ether_add = "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
    switch (tokenAdress) {
      case ether_add:
        return swapInteractor.rxGetEtherBalance()
            .subscribeOn(Schedulers.newThread())
            .flatMap(n -> Single.just(n.toString()));
      default:
        return swapInteractor.rxGetBalance(tokenAdress)
            .subscribeOn(Schedulers.newThread())
            .flatMap(ethCall -> Single.just(swapInteractor.getResponseResult(ethCall, tokenAdress)
                .toString()));
    }
  }

  public void SpinnerItemSelected(String tokenFromAddress, String tokenFromAddressName,
      String tokenToAddress, String tokenToAddressName) {
    try {
      showRatio(tokenFromAddressName, tokenToAddressName, tokenFromAddress, tokenToAddress);
    } catch (IOException e) {
      e.printStackTrace();
    }

    rxUpdateBalance(tokenFromAddress, tokenFromAddressName, "FROM");
    rxUpdateBalance(tokenToAddress, tokenToAddressName, "TO");
  }

  public void testApi() throws IOException {
    swapInteractor.testApi();
  }
}
