package com.asfoundation.wallet.tokenswap;

import android.util.Log;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.utils.Numeric;

public class SwapDataMapper {
  public byte[] getDataSwapEtherToToken(SwapProof swapProof) {
    Address destToken = new Address(swapProof.getDestToken());
    Uint minConversionRate = new Uint(BigInteger.ZERO);
    List<Type> params = Arrays.asList(destToken, minConversionRate);
    List<TypeReference<?>> returnTypes = Collections.singletonList(new TypeReference<Bool>() {
    });
    Function function = new Function("swapEtherToToken", params, returnTypes);
    String encodedFunction = FunctionEncoder.encode(function);
    return Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(encodedFunction));
  }

  public byte[] getDataSwapTokenToEther(SwapProof swapProof) {
    Address srcToken = new Address(swapProof.getSrcToken());
    Uint srcQnty = new Uint(swapProof.getTokenAmount()
        .toBigInteger());
    Uint minConversionRate = new Uint(BigInteger.ZERO);
    List<Type> params = Arrays.asList(srcToken, srcQnty, minConversionRate);
    List<TypeReference<?>> returnTypes = Collections.singletonList(new TypeReference<Bool>() {
    });
    Function function = new Function("swapTokenToEther", params, returnTypes);
    String encodedFunction = FunctionEncoder.encode(function);
    return Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(encodedFunction));
  }

  public Function getDataExpectedRate(SwapProof swapProof) {
    Address srcToken = new Address(swapProof.getSrcToken());
    Address destToken = new Address(swapProof.getDestToken());
    Uint srcQnty = new Uint(swapProof.getTokenAmount()
        .toBigInteger());
    List<Type> params = Arrays.asList(srcToken, destToken, srcQnty);
    List<TypeReference<?>> returnTypes = Collections.singletonList(new TypeReference<Uint>() {
    });
    Function getRates = new Function("getExpectedRate", params, returnTypes);
    return getRates;
  }

  public byte[] getDataApprove(SwapProof swapProof) {
    String spender = swapProof.getApproveAddress();
    BigDecimal amount = swapProof.getTokenAmount();
    Log.d("swapLog2", "amount = " + String.valueOf(amount));
    List<Type> params = Arrays.asList(new Address(spender), new Uint(amount.toBigInteger()));
    List<TypeReference<?>> returnTypes = Collections.singletonList(new TypeReference<Bool>() {
    });
    Function function = new Function("approve", params, returnTypes);
    String encodedFunction = FunctionEncoder.encode(function);
    return Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(encodedFunction));
  }

  public Function getDataAllowance(SwapProof swapProof) {
    String owner = swapProof.getSrcToken();
    String spender = swapProof.getDestToken();
    List<Type> params = Arrays.asList(new Address(owner), new Address(spender));
    List<TypeReference<?>> returnTypes = Collections.singletonList(new TypeReference<Uint>() {
    });
    Function function = new Function("allowance", params, returnTypes);
    return function;
  }

  public byte[] getDataSwapTokenToToken(SwapProof swapProof) {
    Address srcToken = new Address(swapProof.getSrcToken());
    Address destToken = new Address(swapProof.getDestToken());
    Uint srcQnty = new Uint(swapProof.getTokenAmount()
        .toBigInteger());
    Uint minConversionRate = new Uint(BigInteger.ZERO);
    List<Type> params = Arrays.asList(srcToken, srcQnty, destToken, minConversionRate);
    List<TypeReference<?>> returnTypes = Collections.singletonList(new TypeReference<Bool>() {
    });
    Function function = new Function("swapTokenToToken", params, returnTypes);
    String encodedFunction = FunctionEncoder.encode(function);
    return Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(encodedFunction));
  }

  public Function getBalanceOf(SwapProof swapProof) {
    Address src = new Address(swapProof.getFromAddress());
    List<Type> params = Arrays.asList(src);
    List<TypeReference<?>> returnTypes = Collections.singletonList(new TypeReference<Uint>() {
    });
    Function function = new Function("balanceOf", params, returnTypes);
    return function;
  }
}
