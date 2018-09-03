package com.asfoundation.wallet.tokenswap;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.asf.wallet.R;
import com.asfoundation.wallet.ui.BaseActivity;
import dagger.android.AndroidInjection;
import java.io.IOException;
import javax.inject.Inject;

public class SwapEtherToTokenActivity extends BaseActivity
    implements SwapView, AdapterView.OnItemSelectedListener {
  @Inject SwapInteractor swapInteractor;
  private SwapPresenter presenter;
  private EditText amountFromView;
  private EditText amountToView;
  private Spinner sTokenFrom;
  private Spinner sTokenTo;
  private TextView tvShowRates;
  private TextView tvSwapBalanceTitle;
  private TextView tvSwapBalanceTokenFrom;
  private TextView tvSwapBalanceTokenTo;
  private final String from = "FROM";
  private final String to = "TO";
  private TextWatcher textWatcherFrom;
  private TextWatcher textWatcherTo;

  public static Intent newIntent(Context context) {
    return new Intent(context, SwapEtherToTokenActivity.class);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_swap_ether_to_token);
    AndroidInjection.inject(this);
    presenter = new SwapPresenter(this, swapInteractor);

    toolbar();

    amountFromView = findViewById(R.id.etAmountFrom);
    amountToView = findViewById(R.id.etAmountTo);
    sTokenFrom = findViewById(R.id.spinnerFrom);
    sTokenTo = findViewById(R.id.spinnerTo);
    tvShowRates = findViewById(R.id.tvRates);
    tvSwapBalanceTitle = findViewById(R.id.tvSwapBalanceTitle);
    tvSwapBalanceTokenFrom = findViewById(R.id.tvSwapBalanceTokenFrom);
    tvSwapBalanceTokenTo = findViewById(R.id.tvSwapBalanceTokenTo);
    findViewById(R.id.bSwap).setOnClickListener(v -> clickedSwap());

    sTokenFrom.setOnItemSelectedListener(this);
    sTokenTo.setOnItemSelectedListener(this);

    textWatcherFrom = new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence textBox, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence textBox, int start, int before, int count) {

      }

      @Override public void afterTextChanged(Editable textBox) {
        String srcTokenAddress = getAddresses(sTokenFrom.getSelectedItem()
            .toString());
        String destTokenAddress = getAddresses(sTokenTo.getSelectedItem()
            .toString());
        //presenter.amountChanged(srcTokenAddress, destTokenAddress, textBox.toString(), from);
        try {
          presenter.rxAmountChanged(srcTokenAddress, destTokenAddress, textBox.toString(), from);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    };
    textWatcherTo = new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence textBox, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence textBox, int start, int before, int count) {

      }

      @Override public void afterTextChanged(Editable textBox) {
        String srcTokenAddress = getAddresses(sTokenFrom.getSelectedItem()
            .toString());
        String destTokenAddress = getAddresses(sTokenTo.getSelectedItem()
            .toString());
        try {
          presenter.rxAmountChanged(srcTokenAddress, destTokenAddress, textBox.toString(), to);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    };
    amountFromView.addTextChangedListener(textWatcherFrom);
    amountToView.addTextChangedListener(textWatcherTo);
    presenter.rxGetAndShowBalance(getString(R.string.RopstenAppCoins));
  }

  @Override public void showRates(String rates) {
    tvShowRates.setText(rates);
  }

  @Override public void clickedSwap() {
    String srcToken = getAddresses(sTokenFrom.getSelectedItem()
        .toString());
    String destToken = getAddresses(sTokenTo.getSelectedItem()
        .toString());
    String amount = amountFromView.getText()
        .toString();
    String toAddress = getString(R.string.RopstenKyberNetworkProxy);
    String approveAddress = getString(R.string.RopstenKyberNetworkProxy);

    String tokenNameFrom = sTokenFrom.getSelectedItem()
        .toString();
    String tokenNameTo = sTokenTo.getSelectedItem()
        .toString();
    String amountFrom = amountFromView.getText()
        .toString();
    String amountTo = amountToView.getText()
        .toString();

    new AlertDialog.Builder(this).setTitle("Swap Invoice")
        .setMessage("Are you sure you want to swap\n\n"
            + amountFrom
            + " "
            + tokenNameFrom
            + "\nfor\n"
            + amountTo
            + " "
            + tokenNameTo
            + " ?")
        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            //Do Something Here
            presenter.swap(srcToken, destToken, amount, toAddress, approveAddress);
          }
        })
        .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            //Do Something Here
          }
        })
        .show();
  }

  @Override public void setTextTokenTo(String amount) {
    amountToView.removeTextChangedListener(textWatcherTo);
    amountToView.setText(amount);
    amountToView.addTextChangedListener(textWatcherTo);
  }

  @Override public void setTextTokenFrom(String amount) {
    amountFromView.removeTextChangedListener(textWatcherFrom);
    amountFromView.setText(amount);
    amountFromView.addTextChangedListener(textWatcherFrom);
  }

  @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    // An item was selected. You can retrieve the selected item using
    // parent.getItemAtPosition(pos)
    String tokenFromAddress = getAddresses(sTokenFrom.getSelectedItem()
        .toString());
    String tokenToAddress = getAddresses(sTokenTo.getSelectedItem()
        .toString());
    String tokenFromAddressName = sTokenFrom.getSelectedItem()
        .toString();
    String tokenToAddressName = sTokenTo.getSelectedItem()
        .toString();

    presenter.SpinnerItemSelected(tokenFromAddress, tokenFromAddressName, tokenToAddress,
        tokenToAddressName);


    switch (parent.getId()) {
      case R.id.spinnerFrom:
        try {
          presenter.rxAmountChanged(tokenFromAddress, tokenToAddress, amountFromView.getText()
              .toString(), from);
        } catch (IOException e) {
          e.printStackTrace();
        }
        break;
      case R.id.spinnerTo:
        try {
          presenter.rxAmountChanged(tokenFromAddress, tokenToAddress, amountFromView.getText()
              .toString(), from);
        } catch (IOException e) {
          e.printStackTrace();
        }
        break;
    }
  }

  @Override public void onNothingSelected(AdapterView<?> parent) {
  }


  public String getAddresses(String token) {
    String address = "";
    switch (token) {
      case "KNC":
        address = getString(R.string.RopstenKNC);
        break;
      case "APPC":
        address = getString(R.string.RopstenAppCoins);
        break;
      case "EOS":
        address = getString(R.string.RopstenEOS);
        break;
      case "Ether":
        address = getString(R.string.RopstenEther);
        break;
      case "BAT":
        address = getString(R.string.RopstenBAT);
        break;
      case "ADX":
        address = getString(R.string.RopstenAdEx);
        break;
    }
    return address;
  }

  public String getTo() {
    return to;
  }

  public String getFrom() {
    return from;
  }

  @Override public void showToast(String text) {
    Toast.makeText(this, text, Toast.LENGTH_LONG)
        .show();
  }

  @Override public void showBalanceTitle(String text) {
    tvSwapBalanceTitle.setText(text);
  }

  @Override public void showBalanceTokenFrom(String text) {
    tvSwapBalanceTokenFrom.setText(text);
  }

  @Override public void showBalanceTokenTo(String text) {
    tvSwapBalanceTokenTo.setText(text);
  }
}




