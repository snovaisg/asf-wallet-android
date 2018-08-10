package com.asfoundation.wallet.tokenswap;

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
    Uint minConversionRate = new Uint(BigInteger.ONE);
    List<Type> params = Arrays.asList(destToken, minConversionRate);
    List<TypeReference<?>> returnTypes = Collections.singletonList(new TypeReference<Bool>() {
    });
    Function function = new Function("swapEtherToToken", params, returnTypes);
    String encodedFunction = FunctionEncoder.encode(function);
    return Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(encodedFunction));
  }
}
