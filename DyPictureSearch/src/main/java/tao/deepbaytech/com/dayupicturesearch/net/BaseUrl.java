package tao.deepbaytech.com.dayupicturesearch.net;

/**
 * @author IT烟酒僧
 * created   2018/12/19 18:23
 * desc:
 */
public class BaseUrl {

    private static BaseUrl baseUrl = null;

    private BaseUrl() {
    }


    //线上
    private static String API_ON_LINE = "http://api.dayuyoupin.com/";

    //测试
    private static String TEST_PATH="http://192.168.1.28:8000/";

    //全网
    private static String ALL_PATH   = "mobile/api/v1.0.0/";


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
        return getHost() + ALL_PATH;
    }


    //获取域名
    public static String getHost() {
        return API_ON_LINE;
    }
}

