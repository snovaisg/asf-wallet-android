package com.asfoundation.wallet.tokenswap;

public interface SwapProofWriter {

  void writeSwapProof(SwapProof swapProof);

  void setListener(ResponseListener resL);
}
