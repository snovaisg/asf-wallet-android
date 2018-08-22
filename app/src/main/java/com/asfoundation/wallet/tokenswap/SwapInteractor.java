package com.asfoundation.wallet.tokenswap;

import java.math.BigInteger;
import org.web3j.utils.Convert;

public class SwapInteractor {
  private final SwapProofWriter swapBlockchainWriter;
  private final SwapDataMapper swapDataMapper;
  private SwapProofFactory swapProofFactory;
  private SwapRates swapRates;

  public SwapInteractor(SwapProofWriter swapBlockchainWriter, SwapDataMapper swapDataMapper,
      SwapRates swapRates) {
    this.swapBlockchainWriter = swapBlockchainWriter;
    this.swapDataMapper = swapDataMapper;
    this.swapRates = swapRates;
    this.swapProofFactory = new SwapProofFactory();
  }

  public BigInteger getRates(String srcToken, String destToken,
      String tokenAmount) {
    SwapProof swapProof = swapProofFactory.createDefaultSwapProof();
    swapProof.setSrcToken(srcToken);
    swapProof.setDestToken(destToken);
    swapProof.setTokenAmount(Convert.toWei(tokenAmount, Convert.Unit.ETHER));

    BigInteger rateWei = swapBlockchainWriter.writeGetterSwapProof(swapProof);
    saveRates(srcToken, destToken, rateWei);
    return rateWei;
  }

  public void swapEtherToToken(String srcToken, String destToken, String amount, String ToAddress,
      ResponseListener listener) {
    SwapProof swapProof = swapProofFactory.createDefaultSwapProof();

    swapProof.setSrcToken(srcToken);
    swapProof.setDestToken(destToken);
    swapProof.setAmount(Convert.toWei(amount, Convert.Unit.ETHER));
    swapProof.setToAddress(ToAddress);
    swapProof.setData(swapDataMapper.getDataSwapEtherToToken(swapProof));

    swapBlockchainWriter.setListener(listener);
    swapBlockchainWriter.writeSwapProof(swapProof);
  }

  public void saveRates(String srcToken, String destToken, BigInteger rate) {
    //Eth to Appc use case
    if (srcToken.equals("0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee") && destToken.equals(
        "0x2799f05B55d56be756Ca01Af40Bf7350787F48d4")) {
      swapRates.setEthToAppc(rate);
    }
  }

  public float calcRate(String srcToken, String destToken) {
    //Ether to Appc use case
    try {
      if (srcToken.equals("0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee") && destToken.equals(
          "0x2799f05B55d56be756Ca01Af40Bf7350787F48d4")) {
        BigInteger rateWei = swapRates.getEthToAppc();

        if (rateWei == null) {
          return 0;
        }
        float rate = Convert.fromWei(rateWei.toString(), Convert.Unit.ETHER)
            .floatValue();
        return rate;
      }
      return 0;
    } catch (Exception e) {
      return 0;
    }
  }
}
