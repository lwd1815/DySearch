package tao.deepbaytech.com.dayupicturesearch.net;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import tao.deepbaytech.com.dayupicturesearch.entity.BaseResponse;
import tao.deepbaytech.com.dayupicturesearch.entity.ImgSearchEntity;
import tao.deepbaytech.com.dayupicturesearch.entity.SDXA;
import tao.deepbaytech.com.dayupicturesearch.net.fastadapter.FastJsonConverterFactory;

/**
 * @author IT烟酒僧
 * created   2018/12/19 16:05
 * desc:
 */
public class HttpSearch {

    public static final  String   BASE_COMMUNITY_URL = BaseUrl.getAllPath();
    private static final int      DEFAULT_TIMEOUT    = 1000;
    private              Retrofit retrofit;
    private              ApiNet   homeVisitorApi;

    protected Subscription changeThread(Observable o, Subscriber s) {
        return o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    private HttpSearch() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        //.addHeader("dpbay-token", DeeSpUtil.getInstance().getString(UserConstant.AUTO_TOKEN))
                        .method(original.method(), original.body()).build();
                return chain.proceed(request);
            }
        });
        retrofit = new Retrofit.Builder().client(builder.build())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_COMMUNITY_URL)
                .build();
        homeVisitorApi = retrofit.create(ApiNet.class);
    }

    private static class SingletonHolder {
        private static final HttpSearch INSTANCE = new HttpSearch();
    }

    public static HttpSearch getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Subscription postImgSeach(Map<String, Object> fieldMap,
                                     Subscriber<ImgSearchEntity> subscriber) {
        return changeThread(
                homeVisitorApi.postImgSearch(fieldMap).map(new Func1<ImgSearchEntity, ImgSearchEntity>() {

                    @Override
                    public ImgSearchEntity call(ImgSearchEntity imgSearchEntity) {
                        System.out.println(imgSearchEntity.toString());
                        if (imgSearchEntity.getState() != 0) {
                            throw new HomeApiException(imgSearchEntity.getState());
                        }
                        return imgSearchEntity;
                    }
                }), subscriber);
    }

    public Subscription postImg(String url, MultipartBody.Part file, Subscriber<BaseResponse> subscriber) {
        return homeVisitorApi.postImg(url, file)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(subscriber);
    }

    public Subscription postImgSearch(MultipartBody.Part file, long time, Subscriber<ImgSearchEntity> subscriber) {
        return changeThread(homeVisitorApi.postImgSearch(file, time), subscriber);
    }

    public Subscription postPreSearch(String url, String tfsid, Subscriber<List<SDXA>> subscriber) {
        return changeThread(homeVisitorApi.postPreSearch(url, tfsid), subscriber);
    }
}
