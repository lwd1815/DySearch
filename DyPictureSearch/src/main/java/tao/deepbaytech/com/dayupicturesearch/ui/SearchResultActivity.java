package tao.deepbaytech.com.dayupicturesearch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import tao.deepbaytech.com.dayupicturesearch.R;
import tao.deepbaytech.com.dayupicturesearch.adapter.DySearchAdapter;
import tao.deepbaytech.com.dayupicturesearch.custom.DeeHeaderAdapter;
import tao.deepbaytech.com.dayupicturesearch.custom.DeeHeaderGridDivider;
import tao.deepbaytech.com.dayupicturesearch.custom.DeeHeaderGridLayoutManager;
import tao.deepbaytech.com.dayupicturesearch.custom.DrawableTextView;
import tao.deepbaytech.com.dayupicturesearch.custom.MyHorizontalScrollView;
import tao.deepbaytech.com.dayupicturesearch.entity.CategoryEntity;
import tao.deepbaytech.com.dayupicturesearch.entity.ImgMultipleItem;
import tao.deepbaytech.com.dayupicturesearch.entity.ImgSearchEntity;
import tao.deepbaytech.com.dayupicturesearch.entity.ProductItemBean;
import tao.deepbaytech.com.dayupicturesearch.entity.RangeEntity;
import tao.deepbaytech.com.dayupicturesearch.net.HttpSearch;

public class SearchResultActivity extends AppCompatActivity implements DySearchAdapter.DysearchListener {
    private TextView       dytitle;
    private TextView       dyback;
    private TextView       dyfilter;
    private RelativeLayout dytoolbar;

    private ImageView              dycorp;
    private RadioGroup             dyradioclass;
    private MyHorizontalScrollView dyscrollview;
    private DrawableTextView       dyclassmore;
    private LinearLayout           dycutfilter;

    private RecyclerView       dyrv;
    private SmartRefreshLayout dysmart;

    private RadioButton dysortsmile;
    private RadioButton dysortprice;
    private RadioGroup  dysortradio;

    private Button      dytopbtn;
    private Button      dyfeedbtb;
    private FrameLayout dyprofy;

    private RelativeLayout dyrl;

    private GridLayoutManager  layoutManager;
    private DySearchAdapter adapter;

    private Map<String, Object> normalParams;
    private Map<String, Object> extraParams;
    private RangeEntity         range;
    private Subscription        subscription;
    private Map<String, Object> totalParams;

    private List<ImgMultipleItem> normalData;
    private ImgSearchEntity       entity;

    private boolean isFromCrop;
    private final int CUTIMGCODE = 2003;
    private boolean canBack;

    private String         searchImgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        dytitle = findViewById(R.id.dy_title_bar);
        dyback = findViewById(R.id.dy_back_bar);
        dyfilter = findViewById(R.id.dy_filter);
        dytoolbar = findViewById(R.id.dy_toolbar);

        dycorp = findViewById(R.id.dy_crop);
        dyradioclass = findViewById(R.id.dy_radio_class);
        dyscrollview = findViewById(R.id.dy_srcollview);
        dyclassmore = findViewById(R.id.dy_class_more);
        dycutfilter = findViewById(R.id.dy_ll_cut_filter);

        dyrv = findViewById(R.id.dy_rv);
        dysmart = findViewById(R.id.dy_smartrefresh);

        dysortsmile = findViewById(R.id.dy_sort_smile);
        dysortprice = findViewById(R.id.dy_sort_price);
        dysortradio = findViewById(R.id.dy_sort_radio);

        dytopbtn = findViewById(R.id.dy_top_btn);
        dyfeedbtb = findViewById(R.id.feedback_btn);
        dyprofy = findViewById(R.id.dy_progress_fy);

        dyrl = findViewById(R.id.dy_rl);


        totalParams = new HashMap<>();
        normalParams = new HashMap<>();
        extraParams = new HashMap<>();

        normalData = new ArrayList<>();
        initclick();

        Bundle bundle = getIntent().getExtras();
        entity = (ImgSearchEntity) bundle.getParcelable("dy_result");
        initRv();
        refresh();
    }

    private void refresh() {
        dysmart.setRefreshHeader(new ClassicsHeader(this));
        dysmart.setRefreshFooter(new ClassicsFooter(this));
        //下来刷新
        dysmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                dysmart.finishRefresh(2000);
                refreshData();
            }
        });

        //上啦加载
        dysmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                dysmart.finishLoadMore(2000);
            }
        });
    }

    private void refreshData() {
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

    private void unSub() {
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
    private void initRv() {
        adapter = new DySearchAdapter(null);
        adapter.setSearchListener(this);

        DeeHeaderAdapter deeHeaderAdapter = new DeeHeaderAdapter(adapter);
        DeeHeaderGridLayoutManager gridLayoutManager =
                new DeeHeaderGridLayoutManager(this, 2, deeHeaderAdapter);
        dyrv.setHasFixedSize(true);
        dyrv.addItemDecoration(new DeeHeaderGridDivider(this, 0));
        dyrv.setLayoutManager(gridLayoutManager);
        dyrv.addItemDecoration(new DeeHeaderGridDivider(this, 0));
        dyrv.setAdapter(adapter);

        initData();
        initcut();
    }

    private void initcut() {
        searchImgPath = getIntent().getStringExtra("bitmapUriPath");
        //transitionImgPath = getIntent().getStringExtra("transitionImgPath");
        Glide.with(this).load(searchImgPath).into(dycorp);
    }

    private void initData() {
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
    }

    private void initclick() {
        dyback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dycorp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goImgCut();
            }
        });

        dyclassmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dyscrollview.fullScroll(ScrollView.FOCUS_RIGHT);
            }
        });
    }

    private void initClassRadio(ArrayList<CategoryEntity> categoryEntities, int categoryId) {
        if (categoryEntities == null || categoryEntities.size() < 1) {
            return;
        }
        dyradioclass.removeAllViews();
        dyradioclass.setOnCheckedChangeListener(null);

        if (dyradioclass.getChildCount() > 0) {
            return;
        }
        int initCheckIndex = 0;

        for (int i = 0; i < categoryEntities.size(); i++) {
            CategoryEntity entity = categoryEntities.get(i);
            if (entity.getKey() == categoryId) {
                initCheckIndex = i;
            }
            RadioButton button = (RadioButton) LayoutInflater.from(this)
                    .inflate(R.layout.radiogroup_img_search_class, dyradioclass, false);
            button.setText(entity.getValue());
            button.setTag(entity.getKey());
            dyradioclass.addView(button);
        }

        ((RadioButton) dyradioclass.getChildAt(initCheckIndex)).setChecked(true);

        dyradioclass.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View view = group.findViewById(checkedId);
                normalParams.put("category", view.getTag() + "");
                extraParams.clear();
                showWaiting(true);
                refreshData();
            }
        });

        //System.err.println("scrollClass.getMaxScrollAmount() : " + scrollClass.getMaxScrollAmount());

        if (dyradioclass.getChildCount() < 8) {
            dyclassmore.setVisibility(View.INVISIBLE);
        } else {
            dyclassmore.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 选择状态栏时,等待
     *
     * @param isShow
     */
    public void showWaiting(boolean isShow) {
        if (isShow) {
            dyrl.setAlpha(0.3f);
            dyprofy.setOnClickListener(null);
            dyprofy.setVisibility(View.VISIBLE);
        } else {
            dyrl.setAlpha(1f);
            dyprofy.setVisibility(View.GONE);
        }
    }

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
        startActivityForResult(intentImg, CUTIMGCODE);
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
    @Override
    public void onSearch(String key) {

    }
}
