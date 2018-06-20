package com.asfoundation.wallet.ui.appcoins;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import com.asfoundation.wallet.ui.appcoins.applications.AppcoinsApplication;
import com.asfoundation.wallet.ui.widget.holder.TokenHolder;
import java.util.List;

public class AppcoinsApplicationAdapter extends RecyclerView.Adapter {
  private static final String TAG = AppcoinsApplicationAdapter.class.getSimpleName();
  private List<AppcoinsApplication> applications;

  @NonNull @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new TokenHolder(viewType, parent);
  }

  @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

  }

  @Override public int getItemCount() {
    return 0;
  }

  public void setApplications(List<AppcoinsApplication> applications) {
    Log.d(TAG, "setApplications() called with: applications = [" + applications + "]");
    this.applications = applications;
  }
}
