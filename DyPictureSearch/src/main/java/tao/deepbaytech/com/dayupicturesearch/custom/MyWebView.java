package tao.deepbaytech.com.dayupicturesearch.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;

/**
 * @author IT烟酒僧
 * created   2018/12/19 16:20
 * desc:
 */
public class MyWebView extends WebView {
    public interface ScrollViewListener{
        void onScrollChanged(View scrollView, int x, int y, int oldx, int oldy);
    }

    private ScrollViewListener scrollViewListener;

    public void setScrollViewListener(ScrollViewListener scrollViewListener){
        this.scrollViewListener = scrollViewListener;
    }

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
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
