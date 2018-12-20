package tao.deepbaytech.com.dayupicturesearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import tao.deepbaytech.com.dayupicturesearch.R;
import tao.deepbaytech.com.dayupicturesearch.custom.OneKeyClearEditText;
import tao.deepbaytech.com.dayupicturesearch.entity.ImgMultipleItem;
import tao.deepbaytech.com.dayupicturesearch.entity.ProductItemBean;

/**
 * @author IT烟酒僧
 * created   2018/12/19 16:54
 * desc:
 */
public class SmartSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ImgMultipleItem> mData;
    private Context               context;
    private SmartSearchListener   searchListener;

    public SmartSearchAdapter(List<ImgMultipleItem> data) {
        this.mData = (List) (data == null ? new ArrayList<>() : data);
    }

    public void setNewData(List<ImgMultipleItem> data) {
        this.mData = (List) (data == null ? new ArrayList<>() : data);
        notifyDataSetChanged();
    }

    public void addData(List<ImgMultipleItem> data) {
        this.mData.addAll(data);
        this.notifyItemRangeInserted(this.mData.size() - data.size(), data.size());
        this.notifyDataSetChanged();
    }

    public List<ImgMultipleItem> getData() {
        return this.mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        RecyclerView.ViewHolder holder = null;
        View view = null;
        switch (viewType) {
            case ImgMultipleItem.BLANK:
                view = LayoutInflater.from(context).inflate(R.layout.item_img_search_blank, parent, false);
                holder = new BlankView(view);
                break;
            case ImgMultipleItem.FLAG:
                view = LayoutInflater.from(context).inflate(R.layout.item_img_search_flag, parent, false);
                holder = new FlagView(view);
                break;
            case ImgMultipleItem.WARE:
                view = LayoutInflater.from(context).inflate(R.layout.item_img_search_ware, parent, false);
                holder = new NormalView(view);
                break;
            case ImgMultipleItem.SEARCH:
                view = LayoutInflater.from(context).inflate(R.layout.item_img_search_edit, parent, false);
                holder = new SearchView(view);
                break;
            case ImgMultipleItem.TRANS:
                view = LayoutInflater.from(context).inflate(R.layout.item_img_search_trans, parent, false);
                holder = new TransView(view);
                break;
            default:
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ImgMultipleItem.BLANK:
                break;
            case ImgMultipleItem.FLAG:
                FlagView flagView = (FlagView) holder;
                flagView.bind(position);
                break;
            case ImgMultipleItem.WARE:
                NormalView normalView = (NormalView) holder;
                normalView.bind(position);
                break;
            case ImgMultipleItem.SEARCH:
                SearchView searchView = (SearchView) holder;
                searchView.bind(position);
                break;
            case ImgMultipleItem.TRANS:
                TransView transView = (TransView) holder;
                transView.bind(position);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getItemType();
    }

    class NormalView extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView tvAmountPrice;
        LinearLayout llWareItemAmount;
        TextView tvRebatePrice;
        LinearLayout llWareItemRebate;
        TextView     itemFrom;
        TextView itemName;
        TextView itemPrice;
        TextView itemCommant;

        public NormalView(View view) {
            super(view);
            itemImage=view.findViewById(R.id.category_gridview_item_image);
            tvAmountPrice=view.findViewById(R.id.tv_amount_price);
            llWareItemAmount=view.findViewById(R.id.ll_ware_item_amount);
            tvRebatePrice=view.findViewById(R.id.tv_rebate_price);
            llWareItemRebate=view.findViewById(R.id.ll_ware_item_rebate);

            itemFrom=view.findViewById(R.id.tv_category_gridview_item_from);
            itemName=view.findViewById(R.id.category_gridview_item_name);
            itemPrice=view.findViewById(R.id.category_gridview_item_price);
            itemCommant=view.findViewById(R.id.category_gridview_item_commant);
        }

        public void bind(int position) {
            final ProductItemBean wareDetailEntity = mData.get(position).getBean();
            if (wareDetailEntity.getDisplayType() == 3) {
                llWareItemAmount.setVisibility(View.VISIBLE);
                llWareItemRebate.setVisibility(View.GONE);
                tvAmountPrice.setText("￥" + wareDetailEntity.getDisplayContent() + "");
            } else if (wareDetailEntity.getDisplayType() == 1) {
                llWareItemRebate.setVisibility(View.VISIBLE);
                llWareItemAmount.setVisibility(View.GONE);
                tvRebatePrice.setText("￥" + wareDetailEntity.getDisplayContent() + "");
            } else {
                llWareItemRebate.setVisibility(View.GONE);
                llWareItemAmount.setVisibility(View.GONE);
            }
            itemName.setText(wareDetailEntity.getTitle());

            if (wareDetailEntity.getNowPrice() != null) {
//                RxTextTool.getBuilder("")
//                        .append("¥ ")
//                        .setProportion(0.7f)
//                        .append(PriceFormat.priceFormat(wareDetailEntity.getNowPrice()))
//                        .into(itemPrice);
                itemPrice.setVisibility(View.VISIBLE);
            } else {
                itemPrice.setVisibility(View.GONE);
            }


            itemCommant.setText(String.format("月销量:%s", wareDetailEntity.getSales()));

            switch (wareDetailEntity.getPfrom()) {
                case "JD":
                    itemFrom.setBackgroundResource(R.mipmap.indexer_jd_icon);
                    break;
                case "TB":
                    itemFrom.setBackgroundResource(R.mipmap.indexer_tb_icon);
                    break;
                case "TM":
                    itemFrom.setBackgroundResource(R.mipmap.indexer_tm_icon);
                    break;
            }

            Glide.with(context)
                    .load(wareDetailEntity.getCoverImage())
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
                            .placeholder(R.mipmap.loading))
                    .into(itemImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context, AWareActivity.class);
//                    intent.putExtra("productId", wareDetailEntity.getId());
//                    context.startActivity(intent);
                }
            });
        }
    }

    class SearchView extends RecyclerView.ViewHolder {
        OneKeyClearEditText searchEt;
        FrameLayout         imgSearch001;

        public SearchView(View view) {
            super(view);
            searchEt=view.findViewById(R.id.imgsearch_et);
            imgSearch001=view.findViewById(R.id.img_search_001);
        }

        public void bind(int position) {
            searchEt.setText(mData.get(position).getFlag());
            searchEt.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
            searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (searchListener != null) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            String sKey = searchEt.getText().toString().trim();
                            searchListener.onSearch(sKey);
                            //if (sKey.isEmpty()) {
                            //  Toast.makeText(context, "search null", Toast.LENGTH_SHORT).show();
                            //} else {
                            //  Toast.makeText(context, "search " + sKey, Toast.LENGTH_SHORT).show();
                            //}
                        }
                    }
                    return false;
                }
            });
        }
    }

    class TransView extends RecyclerView.ViewHolder {
        TextView       tvPenCut;
        RelativeLayout rlPenCut;
        TextView       tvPenFilter;
        RelativeLayout rlPenFilter;
        LinearLayout   imgSearch002;
        TextView       tvHotTjImgSearchMore;
        RelativeLayout rlImgSearchHot;

        public TransView(View view) {
            super(view);
            tvPenCut = view.findViewById(R.id.tv_pen_cut);
            rlPenCut = view.findViewById(R.id.rl_pen_cut);

            tvPenFilter = view.findViewById(R.id.tv_pen_filter);

            rlPenFilter = view.findViewById(R.id.rl_pen_filter);

            imgSearch002 = view.findViewById(R.id.img_search_002);
            tvHotTjImgSearchMore = view.findViewById(R.id.tv_hot_tj_img_search_more);
            rlImgSearchHot = view.findViewById(R.id.rl_img_search_hot);

        }

        public void bind(int position) {
            rlPenCut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            rlPenFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    class FlagView extends RecyclerView.ViewHolder {
        private TextView tv;

        public FlagView(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.img_search_flag);
        }

        public void bind(int position) {
            tv.setText(mData.get(position).getFlag());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    private class BlankView extends RecyclerView.ViewHolder {
        public BlankView(View view) {
            super(view);
        }
    }

    public interface SmartSearchListener {
        void onSearch(String key);
    }

    public void setSearchListener(SmartSearchListener smartSearchListener) {
        this.searchListener = smartSearchListener;
    }
}

