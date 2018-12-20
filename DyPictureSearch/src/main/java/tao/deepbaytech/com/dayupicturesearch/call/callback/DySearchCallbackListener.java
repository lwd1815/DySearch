package tao.deepbaytech.com.dayupicturesearch.call.callback;

/**
 * @author IT烟酒僧
 * created   2018/12/19 15:26
 * desc:
 */
public interface DySearchCallbackListener<T> {
    void callback(int code,T response);
}
