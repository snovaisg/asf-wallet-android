package com.asfoundation.wallet.tokenswap;

import java.math.BigDecimal;
import org.web3j.utils.Convert;

public class SwapProofFactory {

  public SwapProof createDefaultSwapProof() {
    int chainId = 3; // ropsten by default
    String toAddress =
        "0x818E6FECD516Ecc3849DAf6845e3EC868087B755"; // hack KyberNetworkProxy Ropsten address by default;
    BigDecimal gasPrice = Convert.toWei("50",
        Convert.Unit.GWEI); // hack hardcoded for now. Returns 50 Gwei ( current accepted gasPrice of Ropsten to swap )
    BigDecimal gasLimit = BigDecimal.valueOf(450000);
    SwapProof swapProof = new SwapProof();
    swapProof.setChainId(chainId);
    swapProof.setGasPrice(gasPrice);
    swapProof.setGasLimit(gasLimit);
    swapProof.setToAddress(toAddress);
    return swapProof;
  }
}
