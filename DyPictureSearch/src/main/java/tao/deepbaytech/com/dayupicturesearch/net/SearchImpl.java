package tao.deepbaytech.com.dayupicturesearch.net;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
import tao.deepbaytech.com.dayupicturesearch.config.Constans;
import tao.deepbaytech.com.dayupicturesearch.custom.ImgCompress;
import tao.deepbaytech.com.dayupicturesearch.custom.RxBus;
import tao.deepbaytech.com.dayupicturesearch.entity.BaseResponse;
import tao.deepbaytech.com.dayupicturesearch.entity.ImgDataEvent;
import tao.deepbaytech.com.dayupicturesearch.entity.ImgSearchEntity;
import tao.deepbaytech.com.dayupicturesearch.entity.RangeEntity;
import tao.deepbaytech.com.dayupicturesearch.entity.XSdz;
import tao.deepbaytech.com.dayupicturesearch.ui.SearchResultActivity;
import top.zibin.luban.OnCompressListener;

/**
 * @author IT烟酒僧
 * created   2018/12/25 12:18
 * desc:
 */
public class SearchImpl {

    //crop
    private                 Subscription    subscription;
    private                 ImgSearchEntity mEntity;
    private String title;
    private Context mContext;
    private String filepath;
    int code=-1;
    private volatile static SearchImpl      search;


    public static SearchImpl getInstance() {
        if (search == null) {
            synchronized (SearchImpl.class) {
                if (search == null) {
                    search = new SearchImpl();
                }
            }
        }
        return search;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int search(Context context, final String imagePath) {
        this.mContext=context;
        this.filepath=imagePath;

        File file = new File(imagePath);
        if (file.length() < 512 || !file.exists()) {
            code= Constans.PICTURE_ERROR;
            //Toast.makeText(context, "图片识别错误，请重新选择。。", Toast.LENGTH_SHORT).show();
        }

        if (file.length() > 300 * 1024) {
            ImgCompress.zipImg(context, imagePath, new OnCompressListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(File mFile) {
                    xxZipFile(mFile);
                    code=Constans.PICTURE_SUCCESS;
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }
            });
        } else {
            code= xxZipFile(file);
        }

        return code;
    }

    private int  xxZipFile(File mfile) {
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
                                code=Constans.PICTURE_ERROR;
                            }

                            @Override
                            public void onNext(BaseResponse baseResponse) {
                                if (baseResponse.getState() == 0) {
                                    code=fdsvf(baseResponse.getData().toString());
                                } else {

                                }
                            }
                        });
        return code;
    }

    private int fdsvf(final String s) {
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
                        code=Constans.PICTURE_ERROR;
                    }

                    @Override
                    public void onNext(XSdz xSdz) {
                        code=Constans.PICTURE_SUCCESS;
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
                        mEntity.setRange(range_trans);
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
                                                            Bundle bundle=new Bundle();
                                                            bundle.putParcelable("dy_result",imgSearchEntity);
                                                            Intent intent = new Intent(mContext,SearchResultActivity.class);
                                                            intent.putExtras(bundle);
                                                            intent.putExtra("bitmapUriPath", filepath);
                                                            intent.putExtra("title",title);
                                                            mContext.startActivity(intent);
                                                        } else {
                                                        }
                                                    }
                                                });
                                    }
                                });
                    }
                });
        return code;
    }

}
