package tao.deepbaytech.com.dayupicturesearch.custom;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

/**
 * @author IT烟酒僧
 * created   2018/12/21 17:12
 * desc:
 */
public class Jump {

    Context mContext;
    private Jump() {
    }

    private volatile static Jump jump;

    public static Jump getInstance() {
        if (jump == null) {
            synchronized (Jump.class) {
                if (jump == null) {
                    jump = new Jump();
                }
            }
        }
        return jump;
    }

    /**
     * 跳转淘宝详情页
     */
    public void openTaobao(Context context,String url,String id) {
        mContext=context;
        if (checkPackage("com.taobao.taobao")) { //url:淘宝商品详情
        String taobaoAppStr_goods = "taobao://item.taobao.com/item.htm?id="+id+"";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(taobaoAppStr_goods));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        }else {
        String taobaoWebStr_goods = "https://item.taobao.com/item.htm?id="+id+"";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(taobaoWebStr_goods));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        }
    }

    /**
     * // * 跳转京东详情页
     */
    public void openJD(Context context,String url,String ids) {
        mContext=context;

        if (checkPackage("com.jingdong.app.mall")) {
            //这是京东商品详情页
            // 需要提取商品id，添加到下面url，不能单独将商品详情页作为url传入
            String jdAppStr_goods = "openApp.jdMobile://virtual?params={\"category\":\"jump\",\"des\":\"productDetail\",\"skuId\":\""+ids+"\",\"sourceType\":\"JSHOP_SOURCE_TYPE\",\"sourceValue\":\"JSHOP_SOURCE_VALUE\"}";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(jdAppStr_goods));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }else {
             String jdWebStr_goods = "https://item.m.jd.com/product/"+ids+".html";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(jdWebStr_goods));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    /**
     * // * 跳转天猫
     */
    public void openTM(Context context,String url,String ids) {
        mContext=context;
        if (checkPackage("com.tmall.wireless")) {
            String jdAppStr_goods = "tmall://tmallclient/?{\"action\":\"item:id="+ids+"\"}";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(jdAppStr_goods));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }else {
            Intent intent=null;
             String jdWebStr_shop = url;
             if (jdWebStr_shop.contains("http")){
                 intent = new Intent(Intent.ACTION_VIEW, Uri.parse(jdWebStr_shop));
             }else {
                  intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https:"+jdWebStr_shop));
             }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    /**
     * 检测该包名所对应的应用是否存在 ** @param packageName * @return
     */
    public boolean checkPackage(String
                                        packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            // 手机已安装，返回true
            mContext.getPackageManager().getApplicationInfo(packageName, PackageManager
                    .GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            //手机未安装，跳转到应用商店下载，并返回false
           // Uri uri = Uri.parse("market://details?id=" + packageName);
           // Intent it = new Intent(Intent.ACTION_VIEW, uri);
           // mContext.startActivity(it);
            return false;
        }
    }
}
