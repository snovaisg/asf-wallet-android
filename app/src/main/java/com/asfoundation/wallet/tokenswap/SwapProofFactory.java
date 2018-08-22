package com.asfoundation.wallet.tokenswap;

import java.math.BigDecimal;
import org.web3j.utils.Convert;

public class SwapProofFactory {

  public SwapProof createDefaultSwapProof() {
    int chainId = 3; // ropsten by default
    String fromAddress =
        "0x9dc8f43a6321f5f85a41ee042da2a2263c15b717"; //hack default address. Need to get address dynamically
    String toAddress =
        "0x818E6FECD516Ecc3849DAf6845e3EC868087B755"; // hack KyberNetworkProxy Ropsten address;
    BigDecimal gasPrice = Convert.toWei("50",
        Convert.Unit.GWEI); // hack hardcoded for now. Returns 50 Gwei ( current accepted gasPrice of Ropsten to swap )
    BigDecimal gasLimit = BigDecimal.valueOf(250000);
    SwapProof swapProof = new SwapProof();
    swapProof.setChainId(chainId);
    swapProof.setFromAddress(fromAddress);
    swapProof.setGasPrice(gasPrice);
    swapProof.setGasLimit(gasLimit);
    swapProof.setSrcToken("0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    swapProof.setToAddress(toAddress);
    return swapProof;
  }

  public SwapProof createEmptySwapProof() {
    return new SwapProof();
  }
}