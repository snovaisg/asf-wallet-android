package com.asfoundation.wallet.tokenswap;

import com.asfoundation.wallet.repository.WalletRepositoryType;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.web3j.utils.Convert;

public class SwapInteractor {
  private final SwapProofWriter swapBlockchainWriter;
  private final SwapDataMapper swapDataMapper;
  private SwapProofFactory swapProofFactory;
  private SwapRates swapRates;
  private WalletRepositoryType walletRepositoryType;

  public SwapInteractor(SwapProofWriter swapBlockchainWriter, SwapDataMapper swapDataMapper,
      SwapRates swapRates, WalletRepositoryType walletRepositoryType) {
    this.swapBlockchainWriter = swapBlockchainWriter;
    this.swapDataMapper = swapDataMapper;
    this.swapRates = swapRates;
    this.walletRepositoryType = walletRepositoryType;
    this.swapProofFactory = new SwapProofFactory();
  }

  public BigInteger getRates(String srcToken, String destToken,
      String tokenAmount) {
    if (swapRates.exists(srcToken, destToken)) return swapRates.getRate(srcToken, destToken);
    SwapProof swapProof = swapProofFactory.createDefaultSwapProof();
    swapProof.setSrcToken(srcToken);
    swapProof.setDestToken(destToken);
    swapProof.setTokenAmount(Convert.toWei(tokenAmount, Convert.Unit.ETHER));
    swapProof.setFunction(swapDataMapper.getDataExpectedRate(swapProof));

    BigInteger rateWei = swapBlockchainWriter.writeGetterSwapProof(swapProof);
    swapRates.saveRate(srcToken, destToken, rateWei);
    return rateWei;
  }

  public void swapEtherToToken(String destToken, String amount, String ToAddress,
      ResponseListener listener) {
    SwapProof swapProof = swapProofFactory.createDefaultSwapProof();
    swapProof.setDestToken(destToken);
    swapProof.setAmount(Convert.toWei(amount, Convert.Unit.ETHER));
    swapProof.setToAddress(ToAddress);
    swapProof.setData(swapDataMapper.getDataSwapEtherToToken(swapProof));

    swapBlockchainWriter.setListener(listener);
    swapBlockchainWriter.writeSwapProof(swapProof);
  }

  public void tokenToEther(String srcToken, String destToken, String amount, String toAddress,
      ResponseListener listener) {
    SwapProof swapProof = swapProofFactory.createDefaultSwapProof();
    swapProof.setSrcToken(srcToken);
    swapProof.setDestToken(destToken);
    swapProof.setTokenAmount(Convert.toWei(amount, Convert.Unit.ETHER));
    swapProof.setAmount(Convert.toWei("0", Convert.Unit.ETHER));
    swapProof.setToAddress(toAddress);
    swapProof.setData(swapDataMapper.getDataSwapTokenToEther(swapProof));
    swapProof.setGasPrice(Convert.toWei("25", Convert.Unit.GWEI));
    swapProof.setGasLimit(BigDecimal.valueOf(400000));
    swapBlockchainWriter.setListener(listener);
    swapBlockchainWriter.writeSwapProof(swapProof);
  }

  public void approve(String srcToken, String destToken, String amount, String approveAddress,
      ResponseListener listener) {
    SwapProof swapProof = swapProofFactory.createDefaultSwapProof();
    swapProof.setSrcToken(srcToken);
    swapProof.setDestToken(destToken);
    swapProof.setTokenAmount(Convert.toWei(amount, Convert.Unit.ETHER));
    swapProof.setAmount(Convert.toWei("0", Convert.Unit.ETHER));
    swapProof.setToAddress(srcToken);
    swapProof.setApproveAddress(approveAddress);

    swapProof.setData(swapDataMapper.getDataApprove(swapProof));

    swapBlockchainWriter.setListener(listener);
    swapBlockchainWriter.writeSwapProof(swapProof);
  }

  public float getAllowance(String spender, String toAddress) {

    SwapProof swapProof = swapProofFactory.createDefaultSwapProof();
    String ownder = walletRepositoryType.getDefaultWallet()
        .blockingGet().address.toString();
    swapProof.setSrcToken(ownder);
    swapProof.setDestToken(spender);
    swapProof.setToAddress(toAddress);
    swapProof.setFunction(swapDataMapper.getDataAllowance(swapProof));
    swapProof.setAmount(Convert.toWei("0", Convert.Unit.ETHER));

    BigInteger allowanceWei = swapBlockchainWriter.writeGetterSwapProof(swapProof);
    BigDecimal allowance = Convert.fromWei(allowanceWei.toString(), Convert.Unit.ETHER);
    return allowance.floatValue();
  }

  public void swapTokenToToken(String srcToken, String destToken, String amount, String toAddress,
      ResponseListener<String> listener) {
    SwapProof swapProof = swapProofFactory.createDefaultSwapProof();
    swapProof.setSrcToken(srcToken);
    swapProof.setDestToken(destToken);
    swapProof.setTokenAmount(Convert.toWei(amount, Convert.Unit.ETHER));
    swapProof.setAmount(Convert.toWei("0", Convert.Unit.ETHER));
    swapProof.setToAddress(toAddress);
    swapProof.setData(swapDataMapper.getDataSwapTokenToToken(swapProof));
    swapProof.setGasPrice(Convert.toWei("25", Convert.Unit.GWEI));
    swapProof.setGasLimit(BigDecimal.valueOf(400000));
    swapBlockchainWriter.setListener(listener);
    swapBlockchainWriter.writeSwapProof(swapProof);
  }
}
