package tao.deepbaytech.com.dayupicturesearch.call;

import android.content.Context;

import tao.deepbaytech.com.dayupicturesearch.call.callback.DySearchCallbackListener;
import tao.deepbaytech.com.dayupicturesearch.config.Constans;
import tao.deepbaytech.com.dayupicturesearch.config.SDKStatusCode;
import tao.deepbaytech.com.dayupicturesearch.custom.ByteToFile;
import tao.deepbaytech.com.dayupicturesearch.net.NetUtil;
import tao.deepbaytech.com.dayupicturesearch.net.SearchImpl;

/**
 * @author IT烟酒僧
 * created   2018/12/19 15:22
 * desc:
 */
public class DySearch {
    private boolean CheckInit;
    private String  title;

    private DySearch() {
    }

    private volatile static DySearch dySearch;

    public static DySearch getInstance() {
        if (dySearch == null) {
            synchronized (DySearch.class) {
                if (dySearch == null) {
                    dySearch = new DySearch();
                }
            }
        }
        return dySearch;
    }

    /**
     * sdk 初始化
     *
     * @param context
     * @param o
     * @param callbackListener
     */
    public void init(Context context, final Object o, final DySearchCallbackListener<String> callbackListener) {
        callbackListener.callback(SDKStatusCode.SUCCESS, "初始化成功");
        CheckInit = true;
    }

    /**
     * toolbar title
     *
     * @param title
     */
    public void title(String title) {
        SearchImpl.getInstance().setTitle(title);
    }


    /**
     * 根据截图返回路径
     *
     * @param bfile
     * @return
     */
    public String getFilePath(byte[] bfile) {
        String path = ByteToFile.getInstance().getFilePath(bfile);
        return path;
    }

    /**
     * 搜索
     *
     * @param context
     * @param imgPath
     * @param callbackListener
     */
    public void search(Context context, String imgPath, final DySearchCallbackListener<String> callbackListener) {
        //检查网络
        boolean networkConnected = NetUtil.getInstance().isNetworkConnected(context);
        if (!networkConnected) {
            callbackListener.callback(Constans.NETWORKSTATUE, Constans.NETWORKERROR);
            return;
        }
       SearchImpl.getInstance().getCode(context, imgPath,callbackListener);
    }


    public void JumpCut(Context context,String imgPath){


    }


}
