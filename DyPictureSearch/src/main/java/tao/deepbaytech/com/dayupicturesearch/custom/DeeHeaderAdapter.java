package tao.deepbaytech.com.dayupicturesearch.custom;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author IT烟酒僧
 * created   2018/12/22 11:12
 * desc:
 */
public class DeeHeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //被包装的Adapter。
    private RecyclerView.Adapter mAdapter;

    //用于存放HeaderView
    private final List<FixedViewInfo> mHeaderViewInfos = new ArrayList<>();

    //用于存放FooterView
    private final List<FixedViewInfo> mFooterViewInfos = new ArrayList<>();

    //用于监听被包装的Adapter的数据变化的监听器。它将被包装的Adapter的数据变化映射成DeeHeaderAdapter的变化。
    private RecyclerView.AdapterDataObserver mObserver = new RecyclerView.AdapterDataObserver() {
        @Override public void onChanged() {
            notifyDataSetChanged();
        }

        @Override public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
        }

        @Override public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            notifyItemRangeChanged(getHeadersCount() + positionStart, itemCount, payload);
        }

        @Override public void onItemRangeInserted(int positionStart, int itemCount) {
            notifyItemRangeInserted(getHeadersCount() + positionStart, itemCount);
        }

        @Override public void onItemRangeRemoved(int positionStart, int itemCount) {
            notifyItemRangeRemoved(getHeadersCount() + positionStart, itemCount);
        }

        @Override public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            notifyItemMoved(getHeadersCount() + fromPosition, getHeadersCount() + toPosition);
        }
    };

    public DeeHeaderAdapter(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
        if (mAdapter != null) {
            //注册mAdapter的数据变化监听
            mAdapter.registerAdapterDataObserver(mObserver);
        }
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 根据viewType查找对应的HeaderView 或 FooterView。如果没有找到则表示该viewType是普通的列表项。
        View view = findViewForInfos(viewType);
        if (view != null) {
            return new ViewHolder(view);
        } else {
            //交由mAdapter处理。
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // 如果是HeaderView 或者是 FooterView则不绑定数据。
        // 因为HeaderView和FooterView是由外部传进来的，它们不由列表去更新。
        if (isHeader(position) || isFooter(position)) {
            return;
        }

        //将列表实际的position调整成mAdapter对应的position。
        //交由mAdapter处理。
        int adjPosition = position - getHeadersCount();
        mAdapter.onBindViewHolder(holder, adjPosition);
    }

    @Override public int getItemCount() {
        return mHeaderViewInfos.size() + mFooterViewInfos.size() + (mAdapter == null ? 0
                : mAdapter.getItemCount());
    }

    @Override public int getItemViewType(int position) {
        //如果当前item是HeaderView，则返回HeaderView对应的itemViewType。
        if (isHeader(position)) {
            return mHeaderViewInfos.get(position).itemViewType;
        }

        //如果当前item是HeaderView，则返回HeaderView对应的itemViewType。
        if (isFooter(position)) {
            return mFooterViewInfos.get(
                    position - mHeaderViewInfos.size() - mAdapter.getItemCount()).itemViewType;
        }

        //将列表实际的position调整成mAdapter对应的position。
        //交由mAdapter处理。
        int adjPosition = position - getHeadersCount();
        return mAdapter.getItemViewType(adjPosition);
    }

    /**
     * 设置被包装的adapter。
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter instanceof DeeHeaderAdapter) {
            //被包装的adapter不能是DeeHeaderAdapter。
            throw new IllegalArgumentException("Cannot wrap a DeeHeaderAdapter");
        }
        this.mAdapter = adapter;
        notifyDataSetChanged();
    }

    /**
     * 获取被包装的adapter
     */
    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    /**
     * 判断当前位置是否是头部View。
     *
     * @param position 这里的position是整个列表(包含HeaderView和FooterView)的position。
     */
    public boolean isHeader(int position) {
        return position < getHeadersCount();
    }

    /**
     * 判断当前位置是否是尾部View。
     *
     * @param position 这里的position是整个列表(包含HeaderView和FooterView)的position。
     */
    public boolean isFooter(int position) {
        return getItemCount() - position <= getFootersCount();
    }

    /**
     * 获取HeaderView的个数
     */
    public int getHeadersCount() {
        return mHeaderViewInfos.size();
    }

    /**
     * 获取FooterView的个数
     */
    public int getFootersCount() {
        return mFooterViewInfos.size();
    }

    /**
     * 添加HeaderView
     */
    public void addHeaderView(View view) {
        addHeaderView(view, generateUniqueViewType());
    }

    private void addHeaderView(View view, int viewType) {
        //包装HeaderView数据并添加到列表
        FixedViewInfo info = new FixedViewInfo();
        info.view = view;
        info.itemViewType = viewType;
        mHeaderViewInfos.add(info);
        notifyDataSetChanged();
    }

    /**
     * 删除HeaderView
     *
     * @return 是否删除成功
     */
    public boolean removeHeaderView(View view) {
        for (FixedViewInfo info : mHeaderViewInfos) {
            if (info.view == view) {
                mHeaderViewInfos.remove(info);
                notifyDataSetChanged();
                return true;
            }
        }
        return false;
    }

    /**
     * 添加FooterView
     */
    public void addFooterView(View view) {
        addFooterView(view, generateUniqueViewType());
    }

    private void addFooterView(View view, int viewType) {
        // 包装FooterView数据并添加到列表
        FixedViewInfo info = new FixedViewInfo();
        info.view = view;
        info.itemViewType = viewType;
        mFooterViewInfos.add(info);
        notifyDataSetChanged();
    }

    /**
     * 删除FooterView
     *
     * @return 是否删除成功
     */
    public boolean removeFooterView(View view) {
        for (FixedViewInfo info : mFooterViewInfos) {
            if (info.view == view) {
                mFooterViewInfos.remove(info);
                notifyDataSetChanged();
                return true;
            }
        }
        return false;
    }

    /**
     * 生成一个唯一的数，用于标识HeaderView或FooterView的type类型，并且保证类型不会重复。
     */
    private int generateUniqueViewType() {
        int count = getItemCount();
        while (true) {
            //生成一个随机数。
            int viewType = (int) (Math.random() * Integer.MAX_VALUE) + 1;

            //判断该viewType是否已使用。
            boolean isExist = false;
            for (int i = 0; i < count; i++) {
                if (viewType == getItemViewType(i)) {
                    isExist = true;
                    break;
                }
            }

            //判断该viewType还没被使用，则返回。否则进行下一次循环，重新生成随机数。
            if (!isExist) {
                return viewType;
            }
        }
    }

    /**
     * 根据viewType查找对应的HeaderView 或 FooterView。没有找到则返回null。
     *
     * @param viewType 查找的viewType
     */
    private View findViewForInfos(int viewType) {
        for (FixedViewInfo info : mHeaderViewInfos) {
            if (info.itemViewType == viewType) {
                return info.view;
            }
        }

        for (FixedViewInfo info : mFooterViewInfos) {
            if (info.itemViewType == viewType) {
                return info.view;
            }
        }

        return null;
    }

    @Override public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        if (holder instanceof ViewHolder) {
            super.onViewAttachedToWindow(holder);
        } else {
            mAdapter.onViewAttachedToWindow(holder);
        }

        //处理StaggeredGridLayout，保证HeaderView和FooterView占满一行。
        if (isStaggeredGridLayout(holder)) {
            handleLayoutIfStaggeredGridLayout(holder, holder.getLayoutPosition());
        }
    }

    private boolean isStaggeredGridLayout(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            return true;
        }
        return false;
    }

    private void handleLayoutIfStaggeredGridLayout(RecyclerView.ViewHolder holder, int position) {
        if (isHeader(position) || isFooter(position)) {
            StaggeredGridLayoutManager.LayoutParams p =
                    (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            p.setFullSpan(true);
        }
    }

    @Override public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        if (holder instanceof ViewHolder) {
            super.onViewDetachedFromWindow(holder);
        } else {
            mAdapter.onViewDetachedFromWindow(holder);
        }
    }

    @Override public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
        if (holder instanceof ViewHolder) {
            return super.onFailedToRecycleView(holder);
        } else {
            return mAdapter.onFailedToRecycleView(holder);
        }
    }

    @Override public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (mAdapter != null) {
            mAdapter.onAttachedToRecyclerView(recyclerView);
        }
    }

    @Override public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        if (mAdapter != null) {
            mAdapter.onDetachedFromRecyclerView(recyclerView);
        }
    }

    @Override public void onViewRecycled(RecyclerView.ViewHolder holder) {
        if (holder instanceof ViewHolder) {
            super.onViewRecycled(holder);
        } else {
            mAdapter.onViewRecycled(holder);
        }
    }

    /**
     * 用于包装HeaderView和FooterView的数据类
     */
    private class FixedViewInfo {
        //保存HeaderView或FooterView
        View view;

        //保存HeaderView或FooterView对应的viewType。
        int itemViewType;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}

