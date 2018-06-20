package com.asfoundation.wallet.ui.widget.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.asf.wallet.R;
import com.asfoundation.wallet.ui.appcoins.applications.AppcoinsApplication;
import rx.functions.Action1;

public class AppcoinsApplicationViewHolder extends RecyclerView.ViewHolder {

  private final TextView appName;
  private final Action1<AppcoinsApplication> applicationClickListener;

  public AppcoinsApplicationViewHolder(View itemView,
      Action1<AppcoinsApplication> applicationClickListener) {
    super(itemView);
    appName = itemView.findViewById(R.id.app_name);
    this.applicationClickListener = applicationClickListener;
  }

  public void bind(AppcoinsApplication appcoinsApplication) {
    appName.setText(appcoinsApplication.getName());
    itemView.setOnClickListener(v -> applicationClickListener.call(appcoinsApplication));
  }
}
