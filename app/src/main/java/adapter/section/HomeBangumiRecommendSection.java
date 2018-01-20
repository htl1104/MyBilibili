package adapter.section;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.rxjava.myblibi.R;

import java.util.List;

import adapter.HomeBangumiRecommendAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import entity.bangumi.BangumiRecommendInfo;
import utils.LogDog;
import widget.sectioned.StatelessSection;

/**
 * @author 小陈
 * @time 2018/1/20  11:44
 * @desc  首页番剧推荐Section
 */
public class HomeBangumiRecommendSection extends StatelessSection {

    private Context mContext;

    private List<BangumiRecommendInfo.ResultBean> bangumiRecommends;
    
    public HomeBangumiRecommendSection(Context context, List<BangumiRecommendInfo.ResultBean> bangumiRecommends) {

        super(R.layout.layout_home_bangumi_recommend_head, R.layout.layout_home_recommend_empty);
        this.mContext = context;
        this.bangumiRecommends = bangumiRecommends;
        LogDog.i("首页番剧推荐Section"+bangumiRecommends.size());
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new HomeBangumiRecommendSection.EmptyViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HomeBangumiRecommendSection.RecyclerViewHolder(view);
    }
    
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HomeBangumiRecommendSection.RecyclerViewHolder recyclerViewHolder
                = (HomeBangumiRecommendSection.RecyclerViewHolder) holder;

        recyclerViewHolder.mRecyclerView.setHasFixedSize(false);
        recyclerViewHolder.mRecyclerView.setNestedScrollingEnabled(false);
        recyclerViewHolder.mRecyclerView.setLayoutManager(
                new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        HomeBangumiRecommendAdapter mAdapter = new HomeBangumiRecommendAdapter(
                recyclerViewHolder.mRecyclerView, bangumiRecommends);
        recyclerViewHolder.mRecyclerView.setAdapter(mAdapter);
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.home_bangumi_recommend_recycler)
        RecyclerView mRecyclerView;


        RecyclerViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private static class EmptyViewHolder extends RecyclerView.ViewHolder {

        EmptyViewHolder(View itemView) {

            super(itemView);
        }
    }
}
