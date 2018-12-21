package tao.deepbaytech.com.dayupicturesearch.call;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import tao.deepbaytech.com.dayupicturesearch.call.callback.DySearchCallbackListener;
import tao.deepbaytech.com.dayupicturesearch.config.SDKStatusCode;
import tao.deepbaytech.com.dayupicturesearch.custom.ImgCompress;
import tao.deepbaytech.com.dayupicturesearch.custom.RxBus;
import tao.deepbaytech.com.dayupicturesearch.entity.BaseResponse;
import tao.deepbaytech.com.dayupicturesearch.entity.ImgDataEvent;
import tao.deepbaytech.com.dayupicturesearch.entity.ImgSearchEntity;
import tao.deepbaytech.com.dayupicturesearch.entity.RangeEntity;
import tao.deepbaytech.com.dayupicturesearch.entity.XSdz;
import tao.deepbaytech.com.dayupicturesearch.net.HttpSearch;
import top.zibin.luban.OnCompressListener;

/**
 * @author IT烟酒僧
 * created   2018/12/19 15:22
 * desc:
 */
public class DySearch {
    private boolean CheckInit;

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
     * 初始化
     */
    public void init(Context context, final Object o, final DySearchCallbackListener<String> callbackListener) {
        callbackListener.callback(SDKStatusCode.SUCCESS, "初始化成功");
        CheckInit = true;
    }


    public void title(Context context, String title) {
        if (title == null || title.isEmpty()) {

        } else {

        }
    }

    /**
     * 搜索
     *
     * @param context
     * @param url              图片地址
     * @param callbackListener
     */
    public void search(Context context, String url, final DySearchCallbackListener<String> callbackListener) {
        if (CheckInit) {

        }
    }

    /**
     * 搜索
     *
     * @param context
     * @param bitmap           bitmap图
     * @param callbackListener
     */
    public void search(Context context, Bitmap bitmap, final DySearchCallbackListener<String> callbackListener) {
        if (CheckInit) {

        }
    }

    /**
     * 搜索
     *
     * @param context
     * @param layoutId         本地图片
     * @param callbackListener
     */
    public void search(Context context, int layoutId, final DySearchCallbackListener<String> callbackListener) {
        if (CheckInit) {

        }
    }

    //crop
    private Subscription    subscription;
    private float[]         zuobiao = null;
    private RangeEntity     range;
    private ImgSearchEntity mEntity;


    private void getCropRect(Context context, String imagePath) {
        File file = new File(imagePath);
        if (file.length() < 512 || !file.exists()) {
            Toast.makeText(context, "图片识别错误，请重新选择。。", Toast.LENGTH_SHORT).show();
            //onBackPressedSupport();
        }

        if (file.length() > 300 * 1024) {
            ImgCompress.zipImg(context, imagePath, new OnCompressListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(File mFile) {
                    xxZipFile(mFile);
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }
            });
        } else {
            xxZipFile(file);
        }
    }

    private void xxZipFile(File mfile) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), mfile);
        MultipartBody.Part part =
                MultipartBody.Part.createFormData("file", mfile.getName(), requestBody);

        subscription = HttpSearch.getInstance()
                .postImg("http://image-search.dayuyoupin.com/upimage", part,
                        new Subscriber<BaseResponse>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onNext(BaseResponse baseResponse) {
                                if (baseResponse.getState() == 0) {
                                    fdsvf(baseResponse.getData().toString());
                                } else {

                                }
                            }
                        });
    }

    private void fdsvf(final String s) {
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = HttpSearch.getInstance()
                .postPreSearch("http://image-search.dayuyoupin.com/pre-search", s, new Subscriber<XSdz>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(XSdz xSdz) {
                        mEntity = new ImgSearchEntity();
                        final RangeEntity range_trans = new RangeEntity();
                        RangeEntity range_up = new RangeEntity();
                        range_up.setX1((int) (xSdz.getSulw() * 100));
                        range_up.setY1((int) (xSdz.getSulh() * 100));
                        range_up.setX2((int) (xSdz.getSdrw() * 100));
                        range_up.setY2((int) (xSdz.getSdrh() * 100));
                        range_trans.setX1((int) (xSdz.getSulh() * 100));
                        range_trans.setY1((int) (xSdz.getSulw() * 100));
                        range_trans.setX2((int) (xSdz.getSdrh() * 100));
                        range_trans.setY2((int) (xSdz.getSdrw() * 100));
                        mEntity.setId(s);
                        mEntity.setRange(range_up);
                        mEntity.setCategoryId(xSdz.getCate());
                        mEntity.setAttribute(xSdz.getGender());

                        Observable.just(mEntity)
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe(new Action1<ImgSearchEntity>() {
                                    @Override
                                    public void call(ImgSearchEntity cropData) {
                                        System.out.println(cropData.toString());
                                        final Map<String, Object> normalParams = new HashMap<>();
                                        normalParams.put("searchCode", cropData.getId());
                                        final RangeEntity range = cropData.getRange();
                                        normalParams.put("picRange", range.getX1()
                                                + ","
                                                + range.getY1()
                                                + ","
                                                + range.getX2()
                                                + ","
                                                + range.getY2());
                                        normalParams.put("category", cropData.getCategoryId());
                                        normalParams.put("sex", cropData.getAttribute());
                                        normalParams.put("userBox", 0);

                                        if (subscription != null && subscription.isUnsubscribed()) {
                                            subscription.unsubscribe();
                                        }
                                        subscription = HttpSearch.getInstance()
                                                .postImgSeach(normalParams, new Subscriber<ImgSearchEntity>() {
                                                    @Override
                                                    public void onCompleted() {

                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {
                                                        RxBus.getDefault().postSticky(new ImgDataEvent(normalParams, false));
                                                        //showCamera();
                                                    }

                                                    @Override
                                                    public void onNext(ImgSearchEntity imgSearchEntity) {
                                                        System.out.println(imgSearchEntity.toString());
                                                        if (imgSearchEntity.getState() == 0) {
                                                            RxBus.getDefault().postSticky(new ImgDataEvent
                                                                    (imgSearchEntity, true));
                                                            //calculateAnimateParam(range_trans);
                                                        } else {
                                                            //showCamera();
                                                        }
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }

}
