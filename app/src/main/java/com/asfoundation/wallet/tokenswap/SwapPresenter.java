package com.asfoundation.wallet.tokenswap;

import android.text.Editable;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.web3j.utils.Convert;

public class SwapPresenter {

  private SwapView view;
  private SwapInteractor swapInteractor;

  public SwapPresenter(SwapView view, SwapInteractor swapInteractor) {
    this.view = view;
    this.swapInteractor = swapInteractor;
  }

  public void swapEtherToToken(String srcToken, String destToken, String amount, String ToAddress,
      ResponseListener listener) {
    swapInteractor.swapEtherToToken(srcToken, destToken, amount, ToAddress, listener);
    // ToDo: get confirmation of transaction and handle Ux
  }

  public void getRates(String srcToken, String destToken, String tokenAmount) {
    // fetch rates 1:1
    if (tokenAmount.isEmpty()) tokenAmount = "1";
    BigInteger rateWei = swapInteractor.getRates(srcToken, destToken, tokenAmount);
    // Convert to UI/UX
    Float amount = Float.parseFloat(tokenAmount);
    Float swapValueWei = rateWei.floatValue() * amount;
    String swapValueWeiStr = swapValueWei.toString();
    BigDecimal swapValueEth = Convert.fromWei(swapValueWeiStr, Convert.Unit.ETHER);
    String swapValueEthStr = swapValueEth.toString();
    view.showRates(swapValueEthStr);
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

  public void amountChanged(String srcTokenAddress, String destTokenAddress, Editable s) {
    float userInput;
    try {
      userInput = Float.parseFloat(s.toString());
      float amount = swapInteractor.calcRate(srcTokenAddress, destTokenAddress, userInput);
      String amount_str = String.valueOf(amount);
      view.setTextTokenTo(amount_str);
    } catch (Exception e) {
      view.setTextTokenTo("0");
    }
  }
}
