package tao.deepbaytech.com.dayupicturesearch.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import tao.deepbaytech.com.dayupicturesearch.R;
import tao.deepbaytech.com.dayupicturesearch.custom.Jump;
import tao.deepbaytech.com.dayupicturesearch.custom.MTextView;
import tao.deepbaytech.com.dayupicturesearch.entity.ImgMultipleItem;
import tao.deepbaytech.com.dayupicturesearch.entity.ProductItemBean;

/**
 * @author IT烟酒僧
 * created   2018/12/22 9:58
 * desc:
 */
public class DySearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ImgMultipleItem> mData;
    private Context               context;
    private DysearchListener      mDysearchListener;
    private ImageSpan             is;

    public DySearchAdapter(List<ImgMultipleItem> data){
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view=View.inflate(viewGroup.getContext(),R.layout.dy_item_dy_search,null);
        return new NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof NormalViewHolder){
            NormalViewHolder viewHolder1 = (NormalViewHolder) viewHolder;
            final ProductItemBean bean = mData.get(position).getBean();
            Glide.with(context)
                    .load(bean.getCoverImage())
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
                            .placeholder(R.mipmap.dy_search_loading))
                    .into(viewHolder1.dysearchiv);
            Test(viewHolder1.dysearchtitle,bean);
            if (bean.getSales()!=0){
                viewHolder1.dysearchsales.setText("销量:" + bean.getSales());
                viewHolder1.dysearchsales.setVisibility(View.VISIBLE);
            }else {
                viewHolder1.dysearchsales.setVisibility(View.GONE);
            }

            if (bean.getPrice()!=null){
                viewHolder1.dysearchoriginprice.setText("原价 ¥"+bean.getPrice());
                viewHolder1.dysearchoriginprice.setVisibility(View.VISIBLE);
            }else {
                viewHolder1.dysearchoriginprice.setVisibility(View.GONE);
            }

            viewHolder1.dysearchoriginprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG );
            viewHolder1.dysearchnowprice.setText(bean.getNowPrice());

            //类型
            //优惠券
            if (bean.getDisplayType()==1){
                viewHolder1.dysearchrlout.setVisibility(View.VISIBLE);
                viewHolder1.dysearchrlout.setBackgroundResource(R.mipmap.dy_search_youhuiquan_new_2x);
                viewHolder1.dysearchjuancontent.setText(viewHolder.itemView.getContext().getString(R.string.dy_search_juan)+bean.getDisplayContent());
                viewHolder1.dysearchnowpricetitle.setText("劵后¥");
                //折扣
            }else if (bean.getDisplayType()==2){
                viewHolder1.dysearchrlout.setVisibility(View.VISIBLE);
                viewHolder1.dysearchrlout.setBackgroundResource(R.mipmap.dy_search_discount_new_2x);
                viewHolder1.dysearchjuancontent.setText(bean.getDisplayContent()+viewHolder.itemView.getContext().getString(R.string.dy_search_discount));
                viewHolder1.dysearchnowpricetitle.setText("折后¥");
                //返利
            }else if (bean.getDisplayType()==3){
                viewHolder1.dysearchrlout.setVisibility(View.VISIBLE);
                viewHolder1.dysearchrlout.setBackgroundResource(R.mipmap.dy_search_fanli_new_2x);
                viewHolder1.dysearchjuancontent.setText(viewHolder.itemView.getContext().getString(R.string.dy_search_fanli)+bean.getDisplayContent());
                viewHolder1.dysearchnowpricetitle.setText("返后¥");
                //普通
            }else if (bean.getDisplayType()==4){
                viewHolder1.dysearchrlout.setVisibility(View.GONE);
                viewHolder1.dysearchnowpricetitle.setText("现价¥");
            }

            viewHolder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    if ("JD".equals(bean.getPfrom())){
                        Jump.getInstance().openJD(context,bean.getDetailUrl()==null?"":bean.getDetailUrl());
                    }else if ("TB".equals(bean.getPfrom())){
                        Jump.getInstance().openTaobao(context,bean.getDetailUrl()==null?"":bean.getDetailUrl());
                    }else if ("TM".equals(bean.getPfrom())){
                        Jump.getInstance().openTM(context,bean.getDetailUrl()==null?"":bean.getDetailUrl());
                    }
                }
            });
        }
    }
    private void Test(MTextView mTextView,ProductItemBean ware) {
        String text=ware.getTitle();
        String source = "   "+text;
        String s="";
        String texts="";
        if (text.length()>23){
            s= source.substring(0,23);
            texts=s+"...";
        }else {
            texts=source;
        }
        SpannableString ss = new SpannableString(texts);
        if ("TB".equals(ware.getPfrom())){
            is = new ImageSpan(context, R.mipmap.dy_search_indexer_tb_icon);
        }else if ("TM".equals(ware.getPfrom())){
            is = new ImageSpan(context, R.mipmap.dy_search_indexer_tm_icon);
        }else if ("JD".equals(ware.getPfrom())){
            is = new ImageSpan(context, R.mipmap.dy_search_indexer_jd_icon);
        }
        ss.setSpan(is,0,1,0);
        mTextView.setMText(ss);
        mTextView.invalidate();
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder{
        //图片
        ImageView dysearchiv;
        MTextView dysearchtitle;
        TextView dysearchoriginprice;
        TextView dysearchsales;
        TextView dysearchnowpricetitle;
        TextView dysearchnowprice;
        //优惠卷外层
        RelativeLayout dysearchrlout;
        TextView dysearchjuancontent;
        public NormalViewHolder(View itemView) {
            super(itemView);
            dysearchiv=itemView.findViewById(R.id.dy_search_iv);
            dysearchtitle=itemView.findViewById(R.id.dy_search_name_title);
            dysearchoriginprice=itemView.findViewById(R.id.dy_search_orgin_price);
            dysearchsales=itemView.findViewById(R.id.dy_search_sales);
            dysearchnowpricetitle=itemView.findViewById(R.id.dy_search_now_price_title);
            dysearchnowprice=itemView.findViewById(R.id.dy_search_now_price);
            dysearchrlout=itemView.findViewById(R.id.dy_search_juan_rlout);
            dysearchjuancontent=itemView.findViewById(R.id.dy_search_juan_content);
        }
    }

    public interface DysearchListener {
        void onSearch(String key);
    }

    public void setSearchListener(DysearchListener smartSearchListener) {
        this.mDysearchListener = smartSearchListener;
    }
}
