package com.asfoundation.wallet.tokenswap;

import io.reactivex.Single;
import java.io.IOException;
import java.math.BigInteger;
import org.web3j.protocol.core.methods.response.EthCall;

public interface SwapProofWriter {

  void writeSwapProof(SwapProof swapProof);

  void setListener(ResponseListener resL);

  BigInteger writeGetterSwapProof(SwapProof swapProof);

  Single<EthCall> rxWriteGetterSwapProof(SwapProof swapProof) throws IOException;
}
