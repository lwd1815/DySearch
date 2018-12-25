package tao.deepbaytech.com.dayupicturesearch.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author IT烟酒僧
 * created   2018/12/25 16:13
 * desc:
 */
public class NetUtil {

    private volatile static NetUtil      netUtil;
    public static NetUtil getInstance() {
        if (netUtil == null) {
            synchronized (NetUtil.class) {
                if (netUtil == null) {
                    netUtil = new NetUtil();
                }
            }
        }
        return netUtil;
    }

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
