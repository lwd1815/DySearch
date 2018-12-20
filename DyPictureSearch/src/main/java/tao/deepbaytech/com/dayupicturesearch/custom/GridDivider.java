package tao.deepbaytech.com.dayupicturesearch.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @author IT烟酒僧
 * created   2018/12/19 17:51
 * desc:
 */
public class GridDivider extends RecyclerView.ItemDecoration {

    private final int[]    ATTRS = new int[] { android.R.attr.listDivider };
    private       Drawable mDivider;
    private       boolean  hasSearch;

    public GridDivider(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    public void setHasSearch(boolean hasSearch) {
        this.hasSearch = hasSearch;
    }

    private boolean isLastColum(RecyclerView parent, int pos) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if (!hasSearch) {
                pos++;
            }
            if (pos % 2 == 0) {
                return true;
            }
        }
        return false;
    }

    @Override public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (itemPosition == 0 && hasSearch) {
            outRect.set(mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight(),
                    mDivider.getIntrinsicWidth(), 0);
        } else {
            if (isLastColum(parent, itemPosition)) {
                outRect.set(mDivider.getIntrinsicWidth() / 2, mDivider.getIntrinsicHeight(),
                        mDivider.getIntrinsicWidth(), 0);
            } else {
                outRect.set(mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight(),
                        mDivider.getIntrinsicWidth() / 2, 0);
            }
        }
    }
}

