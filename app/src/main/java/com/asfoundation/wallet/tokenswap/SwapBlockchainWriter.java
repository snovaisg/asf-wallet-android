package com.asfoundation.wallet.tokenswap;

import android.annotation.SuppressLint;
import android.util.Log;
import com.asfoundation.wallet.repository.TransactionException;
import com.asfoundation.wallet.repository.Web3jProvider;
import io.reactivex.Single;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Consumer;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Numeric;

public class SwapBlockchainWriter implements SwapProofWriter {

  private final Web3jProvider web3jProvider;
  private final SwapTransactionFactory swapTransactionFactory;
  private ResponseListener resL;

  public SwapBlockchainWriter(Web3jProvider web3jProvider,
      SwapTransactionFactory swapTransactionFactory) {
    this.web3jProvider = web3jProvider;
    this.swapTransactionFactory = swapTransactionFactory;
  }

  @SuppressLint("CheckResult") @Override public void writeSwapProof(SwapProof swapProof) {
    swapTransactionFactory.createTransaction(swapProof)
        .flatMap(this::sendTransaction)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object result) {
            resL.onResponse(result.toString());
            Log.d("swapLog", "class = " + resL.getClass()
                .toString());
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable error) {
            resL.onError(error);
          }
        });
  }

  @Override public void setListener(@Nullable ResponseListener listener) {
    this.resL = listener;
  }

  private Single<String> sendTransaction(byte[] transaction) {
    return Single.fromCallable(() -> {
      EthSendTransaction sentTransaction = web3jProvider.getDefault()
          .ethSendRawTransaction(Numeric.toHexString(transaction))
          .send();
      if (sentTransaction.hasError()) {
        throw new TransactionException(sentTransaction.getError()
            .getCode(), sentTransaction.getError()
            .getMessage(), sentTransaction.getError()
            .getData());
      }
      return sentTransaction.getResult();
    });
  }
}
