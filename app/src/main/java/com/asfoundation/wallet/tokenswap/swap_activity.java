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
  Spinner fromToken;
  Spinner toToken;
  String coinSelected;
  ResponseListener<String> showTxHash;

  @Override protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_swap);
    AndroidInjection.inject(this);
    swapDataMapper = new SwapDataMapper();

    fromToken = findViewById(R.id.spinnerFrom);
    fromToken.setOnItemSelectedListener(this);
    toToken = findViewById(R.id.spinnerTo);
    toToken.setOnItemSelectedListener(this);

    showTxHash = new ResponseListener<String>() {
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
    };
  }

  public void setText(String text) {
    TextView tv = findViewById(R.id.et1);
    tv.setText(text);
  }

  public void testTransaction(View v) {
    swapBlockChainWriter.writeSwapProof(swapProof);
  }

  public void testApprove(View view) {
    swapBlockChainWriter.setListener(showTxHash);

    TextView tvAmount = findViewById(R.id.et1);
    String amountEth = tvAmount.getText()
        .toString();

    try {
      swapProof.setAmount(BigDecimal.ZERO);
      swapProof.setToAddress(swapProof.getSrcToken());//hack remove
      BigDecimal amountWei = Convert.toWei(amountEth, Convert.Unit.ETHER);
      Log.d("swapLog3", "amountWei = " + amountWei.toString());
      swapProof.setTokenAmount(amountWei);
      swapProof.setAmount(BigDecimal.ZERO);
      swapProof.setData(swapDataMapper.getDataApprove(swapProof));
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
      case "ETHER":
        tokenAddress = getString(R.string.RopstenEther);
    }

    if (parent.getId() == R.id.spinnerFrom) {
      swapProof.setSrcToken(tokenAddress);
    } else if (parent.getId() == R.id.spinnerTo) {
      swapProof.setDestToken(tokenAddress);
    }
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

