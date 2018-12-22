package tao.deepbaytech.com.dayupicturesearch.custom;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * @author IT烟酒僧
 * created   2018/12/22 11:13
 * desc:
 */
public class DeeHeaderGridLayoutManager extends GridLayoutManager {
    private DeeHeaderAdapter mAdapter;

    public DeeHeaderGridLayoutManager(Context context, int spanCount, DeeHeaderAdapter adapter) {
        super(context, spanCount);
        this.mAdapter = adapter;
        setSpanSizeLookup();
    }

    public DeeHeaderGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr,
                                      int defStyleRes, DeeHeaderAdapter adapter) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mAdapter = adapter;
        setSpanSizeLookup();
    }

    public DeeHeaderGridLayoutManager(Context context, int spanCount, int orientation,
                                      boolean reverseLayout, DeeHeaderAdapter adapter) {
        super(context, spanCount, orientation, reverseLayout);
        this.mAdapter = adapter;
        setSpanSizeLookup();
    }

    private void setSpanSizeLookup() {
        super.setSpanSizeLookup(new SpanSizeLookup() {
            @Override public int getSpanSize(int position) {
                if (mAdapter != null) {
                    if (mAdapter.isHeader(position) || mAdapter.isFooter(position)) {
                        //如果是HeaderView 或者 FooterView，就让它占满一行。
                        return getSpanCount();
                    } else {
                        int adjPosition = position - mAdapter.getHeadersCount();
                        return DeeHeaderGridLayoutManager.this.getSpanSize(adjPosition);
                    }
                } else {
                    return 1;
                }
            }
        });
    }

    /**
     * 提供这个方法可以使外部改变普通列表项的SpanSize。
     * 这个方法的作用跟{@link SpanSizeLookup#getSpanSize(int)}一样。
     *
     * @param position 去掉HeaderView和FooterView后的position。
     */
    public int getSpanSize(int position) {
        return 1;
    }

    // 不允许外部设置SpanSizeLookup。
    @Override public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
    }
}

