package tao.deepbaytech.com.dayupicturesearch.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import tao.deepbaytech.com.dayupicturesearch.R;
import tao.deepbaytech.com.dayupicturesearch.adapter.SmartSearchAdapter;
import tao.deepbaytech.com.dayupicturesearch.custom.DrawableTextView;
import tao.deepbaytech.com.dayupicturesearch.custom.GridDivider;
import tao.deepbaytech.com.dayupicturesearch.custom.MyHorizontalScrollView;
import tao.deepbaytech.com.dayupicturesearch.custom.MyWebView;
import tao.deepbaytech.com.dayupicturesearch.custom.RxBus;
import tao.deepbaytech.com.dayupicturesearch.entity.CategoryEntity;
import tao.deepbaytech.com.dayupicturesearch.entity.ImgDataEvent;
import tao.deepbaytech.com.dayupicturesearch.entity.ImgMultipleItem;
import tao.deepbaytech.com.dayupicturesearch.entity.ImgSearchEntity;
import tao.deepbaytech.com.dayupicturesearch.entity.ProductItemBean;
import tao.deepbaytech.com.dayupicturesearch.entity.RangeEntity;
import tao.deepbaytech.com.dayupicturesearch.net.HttpSearch;

public class SearchResultActivity extends AppCompatActivity implements SmartSearchAdapter.SmartSearchListener,View.OnClickListener {
    private TextView toolbarTitle;
    private TextView toolbarBack;
    private TextView toolbarFilter;
    private Toolbar  toolbar;
    private ImageView searchImg;
    private RadioGroup  radiogroupClass;
    private MyHorizontalScrollView scrollClass;
    private DrawableTextView   classMore;
    private LinearLayout llCutFilter;
    private RecyclerView       rvSearchNormal;
    private SmartRefreshLayout swipeLayout;
    private RadioButton        imgsearchSortSimilar;
    private RadioButton    imgsearchSortPrice;
    private RadioGroup     imgsearchSortGroup;
    private Button   topBtn;
    private Button   feedbackBtn;
    private FrameLayout progreessLayout;
    private RelativeLayout mostLayout;
    private String         searchImgPath;
    private String         transitionImgPath;
    //private DStateLayout   stateLayout;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private GridLayoutManager layoutManager;
    private SmartSearchAdapter adapter;

    private Map<String, Object> normalParams;
    private Map<String, Object> extraParams;
    private RangeEntity         range;
    private Subscription        subscription;

    private List<ImgMultipleItem> normalData;

    //private FeedbackDialog feedbackDialog;
    private final int FILTERCODE = 1003;
    private final int CUTIMGCODE = 2003;
    private Map<String, Object> totalParams;
    private boolean canBack;
    private boolean isFromCrop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        toolbarTitle=findViewById(R.id.imgsearch_toolbar_title);
        toolbarBack=findViewById(R.id.imgsearch_toolbar_back);
        toolbarFilter=findViewById(R.id.imgsearch_toolbar_filter);
        toolbar=findViewById(R.id.imgsearch_toolbar);
        searchImg=findViewById(R.id.cropOverlayView);
        radiogroupClass=findViewById(R.id.imgsearch_radiogroup_class);
        scrollClass=findViewById(R.id.imgsearch_scroll_class);
        classMore=findViewById(R.id.imgsearch_class_more);
        llCutFilter=findViewById(R.id.ll_cut_filter);
        rvSearchNormal=findViewById(R.id.rv_search_normal);
        swipeLayout=findViewById(R.id.swipeLayout);
        imgsearchSortSimilar=findViewById(R.id.imgsearch_sort_similar);
        imgsearchSortPrice=findViewById(R.id.imgsearch_sort_price);
        imgsearchSortGroup=findViewById(R.id.imgsearch_sort_group);
        topBtn=findViewById(R.id.top_btn);
        feedbackBtn=findViewById(R.id.feedback_btn);
        progreessLayout=findViewById(R.id.imgsearch_progreess_layout);
        mostLayout=findViewById(R.id.imgsearch_most_layout);
        initRecycler();
        initclick();

    }

    private void initclick() {
        toolbarBack.setOnClickListener(this);
        searchImg.setOnClickListener(this);
        topBtn.setOnClickListener(this);
        feedbackBtn.setOnClickListener(this);
    }

    private void initListener() {
        classMore.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                scrollClass.fullScroll(ScrollView.FOCUS_RIGHT);
            }
        });

        scrollClass.setScrollViewListener(new MyWebView.ScrollViewListener() {
            @Override public void onScrollChanged(View scrollView, int x, int y, int oldx, int oldy) {
                View view = ((HorizontalScrollView) scrollView).getChildAt(0);

                if (view.getMeasuredWidth() <= scrollView.getScrollX() + scrollView.getWidth() + 2) {
                    //滑动到底部时
                    classMore.setVisibility(View.INVISIBLE);
                } else {
                    //并没有滑动到底部时
                    classMore.setVisibility(View.VISIBLE);
                }
            }
        });

        rvSearchNormal.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                hideSoftInput();
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && layoutManager.findFirstVisibleItemPosition() > 2) {
                    topBtn.setVisibility(View.VISIBLE);
                } else {
                    topBtn.setVisibility(View.GONE);
                }
            }

            @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    private void initData() {
        totalParams = new HashMap<>();
        normalParams = new HashMap<>();
        extraParams = new HashMap<>();

        normalData = new ArrayList<>();

        RxBus.getDefault()
                .toObservableSticky(ImgDataEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ImgDataEvent>() {
                    @Override public void call(ImgDataEvent imgDataEvent) {
                        if (imgDataEvent.isHasData()) {
                            ImgSearchEntity entity = imgDataEvent.getEntity();
                            initClassRadio(entity.getCategoryItems(), entity.getCategoryId());
                            List<ProductItemBean> wareList = entity.getProductItems();
                            for (ProductItemBean bean : wareList) {
                                normalData.add(
                                        new ImgMultipleItem(ImgMultipleItem.WARE, ImgMultipleItem.WARE_SPAN_SIZE,
                                                bean));
                            }
                            adapter.setNewData(normalData);
                            //stateLayout.showContent();

                            normalParams.put("searchCode", entity.getId());
                            range = entity.getRange();
                            normalParams.put("picRange",
                                    range.getX1() + "," + range.getY1() + "," + range.getX2() + "," + range.getY2());
                            normalParams.put("category", entity.getCategoryId());
                            normalParams.put("sex", entity.getAttribute());
                            normalParams.put("userBox", 0);
                        } else {
                            // TODO: 2018/1/18 图片搜索没数据,保存图片id上传
                            //stateLayout.showEmpty();
                            normalParams = imgDataEvent.getNormalParams();
                        }
                    }
                });
    }

    public void refreshData() {
        totalParams.clear();
        totalParams.putAll(normalParams);
        totalParams.putAll(extraParams);

        unSub();
        subscription =
                HttpSearch.getInstance().postImgSeach(totalParams, new Subscriber<ImgSearchEntity>() {
                    @Override public void onCompleted() {
                        showWaiting(false);
                    }

                    @Override public void onError(Throwable e) {
                        e.printStackTrace();
                        showWaiting(false);
                        //stateLayout.showEmpty();
                    }

                    @Override public void onNext(ImgSearchEntity entity) {
                        if (isFromCrop) {
                            initClassRadio(entity.getCategoryItems(), entity.getCategoryId());
                            isFromCrop = false;
                        }
                        List<ProductItemBean> wareList = entity.getProductItems();
                        List<CategoryEntity> subList = entity.getSubClassItems();
                        List<CategoryEntity> brandList = entity.getBrandItems();

                        normalData.clear();
                        //normalData.add(
                        //    new ImgMultipleItem(ImgMultipleItem.SEARCH, ImgMultipleItem.SEARCH_SPAN_SIZE, ""));
                        for (ProductItemBean bean : wareList) {
                            normalData.add(
                                    new ImgMultipleItem(ImgMultipleItem.WARE, ImgMultipleItem.WARE_SPAN_SIZE, bean));
                        }
                        //normalData.add(new ImgMultipleItem(ImgMultipleItem.TRANS,ImgMultipleItem.TRANS_SPAN_SIZE,"trans"));

                        adapter.setNewData(normalData);
                        //stateLayout.showContent();

                        //text search ,category 可能会变
                        normalParams.put("category", entity.getCategoryId());
                    }
                });
    }

    /*反馈内容*/
    private void initClassRadio(ArrayList<CategoryEntity> categoryEntities, int categoryId) {
        if (categoryEntities == null || categoryEntities.size() < 1) {
            return;
        }
        radiogroupClass.removeAllViews();
        radiogroupClass.setOnCheckedChangeListener(null);

        if (radiogroupClass.getChildCount() > 0) {
            return;
        }
        int initCheckIndex = 0;

        for (int i = 0; i < categoryEntities.size(); i++) {
            CategoryEntity entity = categoryEntities.get(i);
            if (entity.getKey() == categoryId) {
                initCheckIndex = i;
            }
            RadioButton button = (RadioButton) LayoutInflater.from(this)
                    .inflate(R.layout.radiogroup_img_search_class, radiogroupClass, false);
            button.setText(entity.getValue());
            button.setTag(entity.getKey());
            radiogroupClass.addView(button);
        }

        ((RadioButton) radiogroupClass.getChildAt(initCheckIndex)).setChecked(true);

        radiogroupClass.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
                View view = group.findViewById(checkedId);
                normalParams.put("category", view.getTag() + "");
                extraParams.clear();
                showWaiting(true);
                refreshData();
            }
        });

        System.err.println("scrollClass.getMaxScrollAmount() : " + scrollClass.getMaxScrollAmount());

        if (radiogroupClass.getChildCount() < 8) {
            classMore.setVisibility(View.INVISIBLE);
        } else {
            classMore.setVisibility(View.VISIBLE);
        }
    }

    private void initRecycler() {
        swipeLayout.setEnabled(false);
        layoutManager = new GridLayoutManager(this, 4);
        adapter = new SmartSearchAdapter(null);
        adapter.setSearchListener(this);
        GridDivider gridDivider = new GridDivider(this);
        gridDivider.setHasSearch(false);//是否有搜索框
        rvSearchNormal.addItemDecoration(gridDivider);
        //rvSearchNormal.invalidateItemDecorations();
        //rvSearchNormal.setHasFixedSize(true);
        //rvSearchNormal.setNestedScrollingEnabled(false);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override public int getSpanSize(int position) {
                if (adapter.getData() == null) {
                    return 0;
                } else {
                    return adapter.getData().get(position).getSpanSize();
                }
            }
        });
        rvSearchNormal.setLayoutManager(layoutManager);
        rvSearchNormal.setAdapter(adapter);

        //stateLayout.showLoading();
        initData();
    }

    private void initView() {
//        searchImgPath = getIntent().getStringExtra("bitmapUriPath");
//        transitionImgPath = getIntent().getStringExtra("transitionImgPath");
//        Glide.with(this).load(searchImgPath).into(searchImg);
//
//       // stateLayout = DStateLayout.wrap(swipeLayout);
//       // stateLayout.setEmptyImage(R.mipmap.empty_img_search);
//        SpannableStringBuilder ssb = new SpannableStringBuilder();
//        //String xx = getResources().getString(R.string.sorry_no_relate_picture);
//        SpannableString ss1 = new SpannableString(xx);
//        ss1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.deep_normal_color)), 23, 30,
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        ss1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.deep_normal_color)), 31,
//                xx.length(),
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        ssb.append(ss1);
        //stateLayout.setEmptyText(ssb);
        verifyStoragePermissions(this);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    public void showWaiting(boolean isShow) {
        if (isShow) {
            mostLayout.setAlpha(0.3f);
            progreessLayout.setOnClickListener(null);
            progreessLayout.setVisibility(View.VISIBLE);
        } else {
            mostLayout.setAlpha(1f);
            progreessLayout.setVisibility(View.GONE);
        }
    }

    public void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission =
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

    /**
     * hide softInput
     */
    private void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(swipeLayout.getWindowToken(), 0);
        }
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CUTIMGCODE) {
            if (resultCode == 110) {
                int[] zuobiao = data.getIntArrayExtra("zuobiao");
                if (zuobiao != null && zuobiao.length == 4) {
                    RangeEntity rangeEntity = new RangeEntity();
                    rangeEntity.setY1(zuobiao[0]);
                    rangeEntity.setX1(zuobiao[1]);
                    rangeEntity.setY2(zuobiao[2]);
                    rangeEntity.setX2(zuobiao[3]);

                    range = rangeEntity;
                    normalParams.put("picRange",
                            range.getX1() + "," + range.getY1() + "," + range.getX2() + "," + range.getY2());
                    extraParams.clear();
                    normalParams.remove("category");
                    normalParams.remove("sex");
                    if (normalParams.containsKey("userBox")){
                        normalParams.remove("userBox");
                    }
                    normalParams.put("userBox",1);


                    showWaiting(true);
                    isFromCrop = true;
                    refreshData();
                } else {
                    Toast.makeText(this, "图片标记错误，请重新标记！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        unSub();
        RxBus.getDefault().removeStickyEvent(ImgDataEvent.class);
    }

    private void unSub() {
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

//    private void showFeedbackDialog() {
//        if (feedbackDialog == null) {
//            feedbackDialog = new FeedbackDialog(this);
//        }
//        feedbackDialog.show();
//    }

    private void goImgCut() {
        if (range == null) {
            range = new RangeEntity();
            range.setX1(0);
            range.setY1(0);
            range.setX2(10000);
            range.setY2(10000);
        }
        Intent intentImg = new Intent(this, CutPhotoActivity.class);
        intentImg.putExtra("bitmapUriPath", searchImgPath);
        System.out.println(range.toString());
        intentImg.putExtra("zuobiao", new float[] {
                (float) (range.getY1() * 1.00 / 10000), (float) (range.getX1() * 1.00 / 10000),
                (float) (range.getY2() * 1.00 / 10000), (float) (range.getX2() * 1.00 / 10000)
        });
        //ActivityCompat.startActivityForResult(ImgSearchResActivity.this, intentImg, CUTIMGCODE,
        //    ActivityOptions.makeSceneTransitionAnimation(ImgSearchResActivity.this, searchImg,
        //        "sharedView").toBundle());
        startActivityForResult(intentImg, CUTIMGCODE);
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (canBack) {
            extraParams.clear();
            canBack = false;
            showWaiting(true);
            refreshData();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override public void onSearch(String sKey) {
        if (sKey.isEmpty()) {
            if (canBack) {
                extraParams.clear();
                canBack = false;
                showWaiting(true);
                refreshData();
            } else {
                Toast.makeText(SearchResultActivity.this, "请输入需要搜索的内容", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SearchResultActivity.this, "search " + sKey, Toast.LENGTH_SHORT).show();
            extraParams.clear();
            normalParams.remove("category");
            extraParams.put("keyword", sKey);
            canBack = true;
            hideSoftInput();
            showWaiting(true);
            refreshData();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.imgsearch_toolbar_back){
            finish();
        }else if (v.getId()==R.id.cropOverlayView){
            goImgCut();
        }else if (v.getId()==R.id.top_btn){
            rvSearchNormal.smoothScrollToPosition(0);
        }else if (v.getId()==R.id.feedback_btn){
           // showFeedbackDialog();
        }
    }
}
