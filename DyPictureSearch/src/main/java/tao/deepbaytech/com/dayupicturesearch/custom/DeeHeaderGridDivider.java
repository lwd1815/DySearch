package tao.deepbaytech.com.dayupicturesearch.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * @author IT烟酒僧
 * created   2018/12/22 11:08
 * desc:
 */
public class DeeHeaderGridDivider extends RecyclerView.ItemDecoration {

    private final int[]    ATTRS = new int[] { android.R.attr.listDivider };
    private       Drawable mDivider;
    private       int      mHeaderCount;

    public DeeHeaderGridDivider(Context context, int headerCount) {
        this.mHeaderCount = headerCount;
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    private boolean isLastColum(RecyclerView parent, int pos) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos + 1) % 2 == 0) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {

        }
        return false;
    }

    @Override public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        //int spanCount = getSpanCount(parent);
        if (itemPosition < mHeaderCount) {
            //outRect.set(0, 0, 0, 0);
            return;
        }
        if (isLastColum(parent, itemPosition - mHeaderCount)) {
            outRect.set(mDivider.getIntrinsicWidth() / 2, mDivider.getIntrinsicHeight(),
                    mDivider.getIntrinsicWidth(), 0);
        } else {
            outRect.set(mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight(),
                    mDivider.getIntrinsicWidth() / 2, 0);
        }
    }
}

