package com.asfoundation.wallet.ui.widget.holder;

import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.asf.wallet.R;
import com.asfoundation.wallet.ui.appcoins.applications.AppcoinsApplication;
import com.asfoundation.wallet.widget.CardHeaderTransformation;
import com.asfoundation.wallet.widget.CircleTransformation;
import com.squareup.picasso.Picasso;
import rx.functions.Action1;

public class AppcoinsApplicationViewHolder extends RecyclerView.ViewHolder {

  private final TextView appName;
  private final Action1<AppcoinsApplication> applicationClickListener;
  private final ImageView appIcon;
  private final TextView appRating;
  private final ImageView featuredGraphic;

  public AppcoinsApplicationViewHolder(View itemView,
      Action1<AppcoinsApplication> applicationClickListener) {
    super(itemView);
    appName = itemView.findViewById(R.id.app_name);
    appIcon = itemView.findViewById(R.id.app_icon);
    featuredGraphic = itemView.findViewById(R.id.featured_graphic);
    appRating = itemView.findViewById(R.id.app_rating);
    this.applicationClickListener = applicationClickListener;
  }

  public void bind(AppcoinsApplication appcoinsApplication) {
    appName.setText(appcoinsApplication.getName());
    Picasso.with(itemView.getContext())
        .load(appcoinsApplication.getIcon())
        .transform(new CircleTransformation())
        .into(appIcon);
    int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
        itemView.getContext()
            .getResources()
            .getDisplayMetrics());
    Picasso.with(itemView.getContext())
        .load(appcoinsApplication.getFeaturedGraphic())
        .fit()
        .centerCrop()
        .transform(new CardHeaderTransformation(space))
        .into(featuredGraphic);
    appRating.setText(String.valueOf(appcoinsApplication.getRating()));
    itemView.setOnClickListener(v -> applicationClickListener.call(appcoinsApplication));
  }
}
