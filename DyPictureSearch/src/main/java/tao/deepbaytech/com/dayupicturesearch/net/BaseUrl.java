package tao.deepbaytech.com.dayupicturesearch.net;

/**
 * @author IT烟酒僧
 * created   2018/12/19 18:23
 * desc:
 */
public class BaseUrl {

    private static BaseUrl baseUrl = null;
    private BaseUrl() {}

    /**
     * TYPE:
     * 0:线下
     * 1:线上
     */
    private static int TYPE=1;
    /**
     *HOST
     * @return
     */
    //线下
    private static String API_UNDER_LINE="http://api.deepbaytech.com/";
    //线上
    private static String API_ON_LINE="https://api.dayuyoupin.com/";

    /**
     * 不加版本号
     */
    //全网
    private static String all="mobile/";
    //自营
    private static String autotrophy="shop/";
    //社区
    private static String community="community/";

    /**
     * PATH
     * @return
     */

    //全网
    private static String ALL_PATH="mobile/api/v1.0.0/";
    //自营
    private static String AUTOTROPHY="shop/api/v1.0.0/";
    //社区
    private static String COMMUNITY="community/api/v1.0.0/";

    /**
     * H5
     * @return
     */
    //线上
    private static String H5_PATH_ON_LINE="test1.deepbaytech.com";
    //线下
    private static String H5_PATH_UNDER_LINE="wx.dayuyoupin.com";



    public static BaseUrl getInstance() {
        synchronized (BaseUrl.class) {
            if (baseUrl == null) {
                baseUrl = new BaseUrl();
            }
        }
        return baseUrl;
    }

    //全网路径拼接
    public static String getAllPath() {
        return getHost()+ALL_PATH;
    }

    //自营路径拼接
    public static String getAUTOTROPHY() {
        return getHost()+AUTOTROPHY;
    }

    //社区路径拼接

    public static String getCOMMUNITY() {
        return getHost()+COMMUNITY;
    }

    //三方调用路径拼接

    public static String getThridPath(){
        return getHost()+all;
    }

    //获取域名
    public static String getHost(){
        if (TYPE==0){
            return API_UNDER_LINE;
        }else if (TYPE==1){
            return API_ON_LINE;
        }
        return "";
    }
}

