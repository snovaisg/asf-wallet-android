package com.asfoundation.wallet.ui.widget.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.asf.wallet.R;
import com.asfoundation.wallet.ui.appcoins.applications.AppcoinsApplication;

public class AppcoinsApplicationViewHolder extends RecyclerView.ViewHolder {

  private final TextView appName;

  public AppcoinsApplicationViewHolder(View itemView) {
    super(itemView);
    appName = itemView.findViewById(R.id.app_name);
  }

  public void bind(AppcoinsApplication appcoinsApplication) {
    appName.setText(appcoinsApplication.getName());
  }
}
