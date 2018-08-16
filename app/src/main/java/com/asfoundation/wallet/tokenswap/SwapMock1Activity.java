package com.asfoundation.wallet.tokenswap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import com.asf.wallet.R;
import dagger.android.AndroidInjection;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.inject.Inject;
import org.web3j.utils.Convert;

public class SwapMock1Activity extends AppCompatActivity {

  @Inject SwapProofWriter swapBlockChainWriter;
  @Inject SwapProof swapProof;
  EditText etSrcAmount;
  ResponseListener<String> showTxHash;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_swap_mock1);
    AndroidInjection.inject(this);
    etSrcAmount = findViewById(R.id.etFrom);
    findViewById(R.id.bSwap).setOnClickListener(v -> swap());
    findViewById(R.id.imgArrow).setOnClickListener(v -> writeRates());

    showTxHash = new ResponseListener<String>() {
      @Override public void onResponse(String txHash) {
        runOnUiThread(new Runnable() {
          @Override public void run() {

            Log.d("swapLog2", "hey");
          }
        });
      }

      @Override public void onError(Throwable error) {
        error.printStackTrace();
      }
    };
  }

  //get Expected Rates
  public void writeRates() {
    swapBlockChainWriter.setListener(showTxHash);
    swapProof.setToAddress(getString(R.string.RopstenKyberNetworkProxy));
    swapProof.setAmount(BigDecimal.ZERO);
    swapProof.setSrcToken(getString(R.string.RopstenEther));
    swapProof.setDestToken(getString(R.string.RopstenAppCoins));
    String srcAmount = etSrcAmount.getText()
        .toString();
    swapProof.setTokenAmount(Convert.toWei(srcAmount, Convert.Unit.ETHER));
    BigInteger rateWei = swapBlockChainWriter.writeGetterSwapProof(swapProof);
    Float amount = Float.parseFloat(srcAmount);
    Float swapValueWei = rateWei.floatValue() * amount;
    String swapValueWeiStr = swapValueWei.toString();
    BigDecimal swapValueEth = Convert.fromWei(swapValueWeiStr, Convert.Unit.ETHER);
    String swapValueEthStr = swapValueEth.toString();
    setText(swapValueEthStr);
  }

  public void swap() {
    swapBlockChainWriter.setListener(showTxHash);
    swapProof.setSrcToken(getString(R.string.RopstenEther));
    String srcAmount = etSrcAmount.getText()
        .toString();
    swapProof.setAmount(Convert.toWei(srcAmount, Convert.Unit.ETHER));
    swapProof.setToAddress(getString(R.string.RopstenKyberNetworkProxy));
    swapProof.setDestToken(getString(R.string.RopstenAppCoins));
    swapProof.setData(new SwapDataMapper().getDataSwapEtherToToken(swapProof));
    swapBlockChainWriter.writeSwapProof(swapProof);
  }

  public void setText(String text) {
    EditText tv = findViewById(R.id.etTo);
    tv.setText(text);
  }
}



