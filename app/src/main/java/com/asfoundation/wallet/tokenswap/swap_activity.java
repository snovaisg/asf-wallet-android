package com.asfoundation.wallet.tokenswap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import com.asf.wallet.R;
import dagger.android.AndroidInjection;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.inject.Inject;
import org.web3j.utils.Convert;

public class swap_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

  public SwapDataMapper swapDataMapper;
  @Inject SwapProofWriter swapBlockChainWriter;
  @Inject SwapProof swapProof;
  Spinner toToken;
  String coinSelected;

  @Override protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_swap);
    AndroidInjection.inject(this);
    swapDataMapper = new SwapDataMapper();

    toToken = findViewById(R.id.spinnerTo);
    toToken.setOnItemSelectedListener(this);

    swapBlockChainWriter.setListener(new ResponseListener<String>() {
      @Override public void onResponse(String txHash) {
        runOnUiThread(new Runnable() {
          @Override public void run() {
            setText(txHash);
          }
        });
      }

      @Override public void onError(Throwable error) {
        error.printStackTrace();
      }
    });
  }

  public void setText(String text) {
    TextView tv = findViewById(R.id.et1);
    tv.setText(text);
  }

  public void testTransaction(View v) {
    swapBlockChainWriter.writeSwapProof(swapProof);
  }

  public void testSwap(View view) {

    String amountEth;
    TextView tvAmount = findViewById(R.id.et1);
    amountEth = tvAmount.getText()
        .toString();
    try {
      BigDecimal amountWei = Convert.toWei(amountEth, Convert.Unit.ETHER);
      swapProof.setAmount(amountWei);

      swapProof.setData(swapDataMapper.getDataSwapEtherToToken(swapProof));
      swapBlockChainWriter.writeSwapProof(swapProof);
    } catch (Exception e) {
      Log.d("swapErr", e.toString());
    }
  }

  public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    // An item was selected. You can retrieve the selected item using
    // parent.getItemAtPosition(pos)
    String tokenAddress = "";
    String a = parent.getItemAtPosition(pos)
        .toString();
    coinSelected = a;
    switch (a) {
      case "KNC":
        tokenAddress = getString(R.string.RopstenKNC);
        break;
      case "APPC":
        tokenAddress = getString(R.string.RopstenAppCoins);
        break;
      case "EOS":
        tokenAddress = getString(R.string.RopstenEOS);
        break;
    }
    Log.d("swapLog", "tokenAdd = " + tokenAddress);
    swapProof.setDestToken(tokenAddress);
  }

  public void onNothingSelected(AdapterView<?> parent) {
    // Another interface callback
  }

  public void getExpectedRate(View v) {
    String srcToken = swapProof.getDestToken();
    String destToken = getString(R.string.RopstenAppCoins); // hack RopstenAPPC
    swapProof.setSrcToken(srcToken);
    swapProof.setDestToken(destToken);
    String number = "1";
    swapProof.setAmount(Convert.toWei(number, Convert.Unit.ETHER));
    BigInteger rateWei = swapBlockChainWriter.writeGetterSwapProof(swapProof);
    String rateWeiStr = rateWei.toString();
    BigDecimal rate = Convert.fromWei(rateWeiStr, Convert.Unit.ETHER);
    setText("1 " + coinSelected + " = " + rate.toString() + " APPC");
  }
}

