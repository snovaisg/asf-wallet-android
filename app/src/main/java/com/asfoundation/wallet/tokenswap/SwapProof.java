package com.asfoundation.wallet.tokenswap;

import com.asfoundation.wallet.repository.GasSettingsRepositoryType;
import java.math.BigDecimal;

public class SwapProof {

  private int chainId;
  private BigDecimal gasPrice;
  private BigDecimal gasLimit;
  private BigDecimal amount;
  private String fromAddress;
  private String toAddress;
  private String srcToken;
  private String destToken;
  private Float minConversionRate;
  private byte[] data;

  public SwapProof(int chainId, GasSettingsRepositoryType gasSettingsRepositoryType,
      BigDecimal amount, String fromAddress, String toAddress, String srcToken, String destToken,
      Float minConversionRate, String data) {

    this.chainId = chainId;
    this.gasPrice = gasSettingsRepositoryType.getGasSettings(false)
        .blockingGet().gasPrice.multiply(BigDecimal.TEN)
        .multiply(BigDecimal.TEN);
    this.gasLimit = BigDecimal.valueOf(250000);
    this.amount = amount;
    this.fromAddress = fromAddress;
    this.toAddress = toAddress;
    this.srcToken = srcToken;
    this.destToken = destToken;
    this.minConversionRate = minConversionRate;
    this.data = data.getBytes();
  }

  public Float getMinConversionRate() {
    return minConversionRate;
  }

  public void setMinConversionRate(Float minConversionRate) {
    this.minConversionRate = minConversionRate;
  }

  public String getDestToken() {
    return destToken;
  }

  public void setDestToken(String destToken) {
    this.destToken = destToken;
  }

  public String getSrcToken() {
    return srcToken;
  }

  public void setSrcToken(String srcToken) {
    this.srcToken = srcToken;
  }

  public String getFromAddress() {
    return fromAddress;
  }

  public void setFromAddress(String fromAddress) {
    this.fromAddress = fromAddress;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  public String getToAddress() {
    return toAddress;
  }

  public void setToAddress(String toAddress) {
    this.toAddress = toAddress;
  }

  public BigDecimal getGasPrice() {
    return gasPrice;
  }

  public BigDecimal getGasLimit() {
    return gasLimit;
  }

  public int getChainId() {
    return chainId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
