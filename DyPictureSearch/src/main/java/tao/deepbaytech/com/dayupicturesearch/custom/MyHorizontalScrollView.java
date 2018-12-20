package tao.deepbaytech.com.dayupicturesearch.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * @author IT烟酒僧
 * created   2018/12/19 16:20
 * desc:
 */
public class MyHorizontalScrollView extends HorizontalScrollView {
    private MyWebView.ScrollViewListener scrollViewListener;
    public void setScrollViewListener(MyWebView.ScrollViewListener scrollViewListener){
        this.scrollViewListener = scrollViewListener;
    }
    public MyHorizontalScrollView(Context context) {
        super(context);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollViewListener !=null){
            scrollViewListener.onScrollChanged(this,l,t,oldl,oldt);
        }
    }
}
