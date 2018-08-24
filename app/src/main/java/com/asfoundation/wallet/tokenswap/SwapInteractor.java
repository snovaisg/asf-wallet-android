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
    if (swapRates.exists(srcToken, destToken)) return swapRates.getRate(srcToken, destToken);
    SwapProof swapProof = swapProofFactory.createDefaultSwapProof();
    swapProof.setSrcToken(srcToken);
    swapProof.setDestToken(destToken);
    swapProof.setTokenAmount(Convert.toWei(tokenAmount, Convert.Unit.ETHER));

    BigInteger rateWei = swapBlockchainWriter.writeGetterSwapProof(swapProof);
    swapRates.saveRate(srcToken, destToken, rateWei);
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
}
