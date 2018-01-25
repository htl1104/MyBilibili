package adapter.section;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rxjava.myblibi.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import entity.region.RegionRecommendInfo;
import module.video.VideoDetailsActivity;
import utils.NumberUtil;
import widget.sectioned.StatelessSection;

/**
 * @author 小陈
 * @time 2018/1/25  17:30
 * @desc 广告推荐热门推荐section
 */
public class AdvertisingHotSection extends StatelessSection {
    private Context mContext;
    private List<RegionRecommendInfo.DataBean.RecommendBean> mRecommendBeans;
    public AdvertisingHotSection(Context context, List<RegionRecommendInfo.DataBean.RecommendBean> recommends) {
        super(R.layout.layout_region_recommend_hot_head,R.layout.layout_region_recommend_card_item);
        this.mContext=context;
        this.mRecommendBeans=recommends;
    }

    @Override
    public int getContentItemsTotal() {
        return mRecommendBeans.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder= (ItemViewHolder) holder;
        RegionRecommendInfo.DataBean.RecommendBean recommendBean = mRecommendBeans.get(position);


        Glide.with(mContext)
                .load(Uri.parse(recommendBean.getCover()))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(itemViewHolder.mImage);
        itemViewHolder.mTitle.setText(recommendBean.getTitle());
        itemViewHolder.mPlay.setText(NumberUtil.converString(recommendBean.getPlay()));
        itemViewHolder.mReview.setText(NumberUtil.converString(recommendBean.getDanmaku()));
        itemViewHolder.mCardView.setOnClickListener(view -> {
            VideoDetailsActivity.launch((Activity) mContext,
                    Integer.valueOf(recommendBean.getParam()), recommendBean.getCover());
        });

    }
    
    

    static class HeadViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_type_rank_btn)
        TextView mRankBtn;


        public HeadViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class  ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view)
        CardView mCardView;

        @BindView(R.id.item_img)
        ImageView mImage;

        @BindView(R.id.item_title)
        TextView mTitle;

        @BindView(R.id.item_play)
        TextView mPlay;

        @BindView(R.id.item_review)
        TextView mReview;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
