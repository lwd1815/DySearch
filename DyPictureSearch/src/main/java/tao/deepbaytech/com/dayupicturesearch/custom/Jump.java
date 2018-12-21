package tao.deepbaytech.com.dayupicturesearch.custom;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

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
    public void openTaobao(Context context,String url) {
        mContext=context;
        if (checkPackage("com.taobao.taobao")) { //url:淘宝商品详情
            String urls = "https://item.taobao.com/item" +
                    ".htm?id=539789035577&ali_refid=a3_430406_1007:1124066525:N:485184283370953001_0_100" +
                    ":d45485b3013535b0cc4164b7cd5b7523&ali_trackid=1_d45485b3013535b0cc4164b7cd5b7523&spm " +
                    "=a21bo.50862.201874-sales.8.UYm99R";
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(url);
            intent.setData(uri);
            intent.setClassName("com.taobao.taobao", "com.taobao.tao.detail.activity.DetailActivity");
            context.startActivity(intent);
        }else {
            Toast.makeText(context, "没有安装淘宝", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * // * 跳转京东详情页
     */
    public void openJD(Context context,String url) {
        mContext=context;
        if (checkPackage("com.jingdong.app.mall")) {
            String urlss = "https://item.jd.com/231023.html";
            //这是京东商品详情页
            String id = "231023";
            // 需要提取商品id，添加到下面url，不能单独将商品详情页作为url传入
            String urlsss = "openapp.jdmobile://virtual?params=%7B%22sourceValue%22:%220_productDetail_97%22, " +
                    "%22des%22:%22productDetail%22,%22skuId%22:%22" + id + "%22,%22category%22:%22jump%22, " +
                    "%22sourceType%22:%22PCUBE_CHANNEL%22%7D";
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(url);
            intent.setData(uri); // intent.setClassName("com.jingdong.app.mall","com.jd.lib.productdetail
            // .ProductDetailActivity");//不需要 startActivity(intent);
        }else {
            Toast.makeText(context, "没有安装京东", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * // * 跳转京东详情页
     */
    public void openTM(Context context,String url) {
        mContext=context;
        if (checkPackage("com.tmall.wireless")) {
            String urls = "https://item.jd.com/231023.html";
            //这是京东商品详情页
            String id = "231023";
            // 需要提取商品id，添加到下面url，不能单独将商品详情页作为url传入
            String urlss = "openapp.jdmobile://virtual?params=%7B%22sourceValue%22:%220_productDetail_97%22, " +
                    "%22des%22:%22productDetail%22,%22skuId%22:%22" + id + "%22,%22category%22:%22jump%22, " +
                    "%22sourceType%22:%22PCUBE_CHANNEL%22%7D";
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(url);
            intent.setData(uri); // intent.setClassName("com.jingdong.app.mall","com.jd.lib.productdetail
            // .ProductDetailActivity");//不需要 startActivity(intent);
        }else {
            Toast.makeText(context, "没有安装天猫", Toast.LENGTH_SHORT).show();
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
            Uri uri = Uri.parse("market://details?id=" + packageName);
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            mContext.startActivity(it);
            return false;
        }
    }
}
