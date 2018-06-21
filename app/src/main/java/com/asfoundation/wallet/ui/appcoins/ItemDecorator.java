package com.asfoundation.wallet.ui.appcoins;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemDecorator extends RecyclerView.ItemDecoration {
  private final int spaceInDp;

  public ItemDecorator(int spaceInDp) {
    this.spaceInDp = spaceInDp;
  }

  @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    if (parent.getChildAdapterPosition(view) == 0) {
      outRect.left = spaceInDp;
      outRect.bottom = 0;
    }

    if (parent.getChildAdapterPosition(view) == state.getItemCount() - 1) {
      outRect.right = spaceInDp;
      outRect.top = 0; //don't forget about recycling...
    }
  }
}
