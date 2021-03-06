package tao.deepbaytech.com.dayupicturesearch.net;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import tao.deepbaytech.com.dayupicturesearch.call.callback.DySearchCallbackListener;
import tao.deepbaytech.com.dayupicturesearch.config.Constans;
import tao.deepbaytech.com.dayupicturesearch.entity.ImgSearchEntity;
import tao.deepbaytech.com.dayupicturesearch.entity.RangeEntity;
import tao.deepbaytech.com.dayupicturesearch.entity.SDXA;
import tao.deepbaytech.com.dayupicturesearch.ui.CutPhotoActivity;
import tao.deepbaytech.com.dayupicturesearch.ui.SearchResultActivity;

/**
 * @author IT烟酒僧
 * created   2018/12/25 12:18
 * desc:
 */
public class SearchImpl {

    //crop
    private Subscription    subscription;
    private ImgSearchEntity mEntity;
    private String          title;
    private Context         mContext;
    private String          filename;
    int code = -1;
    private volatile static SearchImpl search;



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



    public void getCode(Context context,final String local ,final String imagePath,final DySearchCallbackListener<String> callbackListener) {
        this.mContext = context;


        String[] split = imagePath.split("/");
        for (int i = 0; i < split.length; i++) {
            this.filename =split[split.length-1];
        }
            xxZipFilex(local,filename,callbackListener);

    }


    private void xxZipFilex(final String mfile,final String name, final DySearchCallbackListener<String> callbackListener) {
                                    if (subscription != null && subscription.isUnsubscribed()) {
                                        subscription.unsubscribe();
                                    }
                                    subscription = HttpSearch.getInstance()
                                            .postPreSearch("http://image-search.dayuyoupin.com/pre-multibox",
                                                    name, new Subscriber<List<SDXA>>() {
                                                        @Override
                                                        public void onCompleted() {
                                                        }

                                                        @Override
                                                        public void onError(Throwable e) {
                                                            code = Constans.PICTURE_ERROR;
                                                            switch (code) {
                                                                case Constans.PICTURE_SUCCESS:
                                                                    callbackListener.callback(Constans.PICTURE_SUCCESS, Constans.SEARCH_SUCCESS);
                                                                    break;
                                                                case Constans.PICTURE_ERROR:
                                                                    callbackListener.callback(Constans.PICTURE_ERROR, Constans.SEARCH_FAILUER);
                                                                    break;
                                                            }
                                                        }

                                                        @Override
                                                        public void onNext(List<SDXA> xSdz) {
                                                            if (xSdz.size()==0){
                                                                RangeEntity range = new RangeEntity();
                                                                range.setX1(0);
                                                                range.setY1(0);
                                                                range.setX2(10000);
                                                                range.setY2(10000);
                                                                callbackListener.callback(Constans.PICTURE_ERROR, Constans.SEARCH_FAILUER);
                                                                goImgCut(mContext,mfile, range,name);
                                                                return;
                                                            }
                                                            mEntity = new ImgSearchEntity();
                                                            final RangeEntity range_trans = new RangeEntity();
                                                            RangeEntity range_up = new RangeEntity();
                                                            range_up.setX1((int) (xSdz.get(0).getSulw() * 100));
                                                            range_up.setY1((int) (xSdz.get(0).getSulh() * 100));
                                                            range_up.setX2((int) (xSdz.get(0).getSdrw() * 100));
                                                            range_up.setY2((int) (xSdz.get(0).getSdrh() * 100));

                                                            range_trans.setX1((int) (xSdz.get(0).getSulh() * 100));
                                                            range_trans.setY1((int) (xSdz.get(0).getSulw() * 100));
                                                            range_trans.setX2((int) (xSdz.get(0).getSdrh() * 100));
                                                            range_trans.setY2((int) (xSdz.get(0).getSdrw() * 100));
                                                            mEntity.setId(name);
                                                            mEntity.setRange(range_up);
                                                            mEntity.setCategoryId(xSdz.get(0).getCate());
                                                            mEntity.setAttribute(xSdz.get(0).getGender());

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
                                                                            normalParams.put("userBox", 1);

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
                                                                                            code = Constans.PICTURE_SUCCESS;
                                                                                            switch (code) {
                                                                                                case Constans.PICTURE_SUCCESS:
                                                                                                    callbackListener.callback(Constans.PICTURE_SUCCESS, Constans.SEARCH_SUCCESS);
                                                                                                    break;
                                                                                                case Constans.PICTURE_ERROR:
                                                                                                    callbackListener.callback(Constans.PICTURE_ERROR, Constans.SEARCH_FAILUER);
                                                                                                    break;
                                                                                            }
                                                                                            goImgCut(mContext,mfile, range,name);
                                                                                        }

                                                                                        @Override
                                                                                        public void onNext(ImgSearchEntity imgSearchEntity) {
                                                                                            if (imgSearchEntity.getState()==0){
                                                                                                code = Constans.PICTURE_SUCCESS;
                                                                                                switch (code) {
                                                                                                    case Constans.PICTURE_SUCCESS:
                                                                                                        callbackListener.callback(Constans.PICTURE_SUCCESS, Constans.SEARCH_SUCCESS);
                                                                                                        break;
                                                                                                    case Constans.PICTURE_ERROR:
                                                                                                        callbackListener.callback(Constans.PICTURE_ERROR, Constans.SEARCH_FAILUER);
                                                                                                        break;
                                                                                                }
                                                                                                Bundle bundle = new Bundle();
                                                                                                bundle.putParcelable("dy_result", imgSearchEntity);
                                                                                                Intent intent = new Intent(mContext, SearchResultActivity
                                                                                                        .class);
                                                                                                intent.putExtras(bundle);
                                                                                                intent.putExtra("flag",2000);
                                                                                                intent.putExtra("bitmapUriPath", mfile);
                                                                                                intent.putExtra("title", title);
                                                                                                mContext.startActivity(intent);
                                                                                            }else{
                                                                                                callbackListener.callback(Constans.PICTURE_ERROR, Constans.SEARCH_FAILUER);
                                                                                                goImgCut(mContext,mfile, range,name);
                                                                                            }

                                                                                        }
                                                                                    });
                                                                        }
                                                                    });
                                                        }

                                                    });

    }





    public void Exrequest(Context context, Intent intent) {
        RangeEntity mRangeEntity=null;
        this.mContext = context;
        String imagePath=intent.getStringExtra("bitmapUriPath");
        int[] zuobiao = intent.getIntArrayExtra("zuobiao");
        final String name = intent.getStringExtra("name");
        if (zuobiao != null && zuobiao.length == 4) {
            mRangeEntity = new RangeEntity();
            mRangeEntity.setX1(zuobiao[0]);
            mRangeEntity.setY1(zuobiao[1]);
            mRangeEntity.setX2(zuobiao[2]);
            mRangeEntity.setY2(zuobiao[3]);

        } else {
            code = Constans.PICTURE_ERROR;
            mJumpListener.code(code);
        }
            ExZipFilex(mRangeEntity,imagePath,name);
    }


    private void ExZipFilex(final RangeEntity range, final String mfile, final String name) {

        final Map<String, Object> normalParams = new HashMap<>();
        normalParams.put("searchCode", name);
        normalParams.put("picRange", range.getX1()
                + ","
                + range.getY1()
                + ","
                + range.getX2()
                + ","
                + range.getY2());
        normalParams.put("userBox", 1);
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

                    }

                    @Override
                    public void onNext(ImgSearchEntity imgSearchEntity) {
                        if (imgSearchEntity.getState()==0){
                            code = Constans.PICTURE_SUCCESS;
                            mJumpListener.code(code);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("dy_result", imgSearchEntity);
                            Intent intent = new Intent(mContext, SearchResultActivity
                                    .class);
                            intent.putExtras(bundle);
                            intent.putExtra("flag",2000);
                            intent.putExtra("bitmapUriPath", mfile);
                            intent.putExtra("title", title);
                            mContext.startActivity(intent);
                        }else {
                            code = Constans.PICTURE_ERROR;
                            mJumpListener.code(code);
                            RangeEntity range = new RangeEntity();
                            range.setX1(0);
                            range.setY1(0);
                            range.setX2(10000);
                            range.setY2(10000);
                            goImgCut(mContext,mfile, range,name);
                        }

                    }
                });

    }


    private void goImgCut(Context context,String searchImgPath,RangeEntity range,String ivname) {
        if (range == null) {
            range = new RangeEntity();
            range.setX1(0);
            range.setY1(0);
            range.setX2(10000);
            range.setY2(10000);
        }
        Intent intentImg = new Intent(context,  CutPhotoActivity.class);
        intentImg.putExtra("bitmapUriPath", searchImgPath);
        intentImg.putExtra("input",2009);
        intentImg.putExtra("name",ivname);
        intentImg.putExtra("zuobiao", new float[] {
                (float) (range.getX1() * 1.00 / 10000), (float) (range.getY1() * 1.00 / 10000),
                (float) (range.getX2() * 1.00 / 10000), (float) (range.getY2() * 1.00 / 10000)
        });
        context.startActivity(intentImg);
    }

    public interface JumpListener{
        int code(int code);
    }

    JumpListener mJumpListener;

    public void setJumpListener(JumpListener jumpListener) {
        mJumpListener = jumpListener;
    }
}
