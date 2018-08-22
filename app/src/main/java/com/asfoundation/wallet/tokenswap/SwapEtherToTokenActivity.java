package com.asfoundation.wallet.tokenswap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.asf.wallet.R;
import dagger.android.AndroidInjection;
import javax.inject.Inject;

public class SwapEtherToTokenActivity extends AppCompatActivity
    implements SwapView, AdapterView.OnItemSelectedListener {
  @Inject SwapInteractor swapInteractor;
  private EditText etSrcAmount;
  private ResponseListener<String> showTxHash;
  private SwapPresenter presenter;
  private EditText amountFromView;
  private EditText amountToView;
  private Spinner sTokenFrom;
  private Spinner sTokenTo;
  private TextView tvShowRates;


  public static Intent newIntent(Context context) {
    return new Intent(context, SwapEtherToTokenActivity.class);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_swap_ether_to_token);
    AndroidInjection.inject(this);
    presenter = new SwapPresenter(this, swapInteractor);

    etSrcAmount = findViewById(R.id.etAmountFrom);
    amountFromView = findViewById(R.id.etAmountFrom);
    amountToView = findViewById(R.id.etAmountTo);
    sTokenFrom = findViewById(R.id.spinnerFrom);
    sTokenTo = findViewById(R.id.spinnerTo);
    tvShowRates = findViewById(R.id.tvRates);

    sTokenFrom.setOnItemSelectedListener(this);
    sTokenTo.setOnItemSelectedListener(this);
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
    clickedGetRates();
  }

  @Override public void clickedGetRates() {
    String srcToken = getString(R.string.RopstenEther);
    String destToken = getString(R.string.RopstenAppCoins);
    String tokenAmount = etSrcAmount.getText()
        .toString();

    presenter.getRates(srcToken, destToken, tokenAmount);
  }


  @Override public void showRates(String rates) {
    tvShowRates.setText(rates);
  }

  @Override public void clickedSwap() {
    String srcToken = getString(R.string.RopstenEther);
    String destToken = getString(R.string.RopstenAppCoins);
    String amount = amountFromView.getText()
        .toString();
    String toAddress = getString(R.string.RopstenKyberNetworkProxy);

    presenter.swapEtherToToken(srcToken, destToken, amount, toAddress, showTxHash);
  }

  @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    // An item was selected. You can retrieve the selected item using
    // parent.getItemAtPosition(pos)
    String tokenFromAddress = "";
    String tokenToAddress = "";
    switch (sTokenFrom.getSelectedItem()
        .toString()) {
      case "KNC":
        tokenFromAddress = getString(R.string.RopstenKNC);
        break;
      case "APPC":
        tokenFromAddress = getString(R.string.RopstenAppCoins);
        break;
      case "EOS":
        tokenFromAddress = getString(R.string.RopstenEOS);
        break;
      case "Ether":
        tokenFromAddress = getString(R.string.RopstenEther);
        break;
      case "BAT":
        tokenFromAddress = getString(R.string.RopstenBAT);
        break;
      case "ADX":
        tokenFromAddress = getString(R.string.RopstenAdEx);
        break;
    }

    switch (sTokenTo.getSelectedItem()
        .toString()) {
      case "KNC":
        tokenToAddress = getString(R.string.RopstenKNC);
        break;
      case "APPC":
        tokenToAddress = getString(R.string.RopstenAppCoins);
        break;
      case "EOS":
        tokenToAddress = getString(R.string.RopstenEOS);
        break;
      case "Ether":
        tokenToAddress = getString(R.string.RopstenEther);
        break;
      case "BAT":
        tokenToAddress = getString(R.string.RopstenBAT);
        break;
      case "ADX":
        tokenToAddress = getString(R.string.RopstenAdEx);
        break;
    }

    presenter.showRatio(sTokenFrom.getSelectedItem()
        .toString(), sTokenTo.getSelectedItem()
        .toString(), tokenFromAddress, tokenToAddress);
  }

  @Override public void onNothingSelected(AdapterView<?> parent) {

  }
}




