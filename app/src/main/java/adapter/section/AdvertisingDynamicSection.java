package adapter.section;

import android.app.Activity;
import android.content.Context;
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
 * @time 2018/1/25  17:56
 * @desc 广告推荐全区动态section
 */
public class AdvertisingDynamicSection extends StatelessSection {
    private Context mContext;
    private List<RegionRecommendInfo.DataBean.DynamicBean> mDynamicBeans;
    public AdvertisingDynamicSection(Context context, List<RegionRecommendInfo.DataBean.DynamicBean>dynamicBeans) {
        super(R.layout.layout_region_recommend_dynamic_head,
                R.layout.layout_region_recommend_card_item);
        this.mContext=context;
        this.mDynamicBeans=dynamicBeans;
    }

    @Override
    public int getContentItemsTotal() {
        return mDynamicBeans.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        RegionRecommendInfo.DataBean.DynamicBean dynamicBean = mDynamicBeans.get(position);

        Glide.with(mContext)
                .load(dynamicBean.getCover())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(itemViewHolder.mImage);
        itemViewHolder.mTitle.setText(dynamicBean.getTitle());
        itemViewHolder.mPlay.setText(NumberUtil.converString(dynamicBean.getPlay()));
        itemViewHolder.mReview.setText(NumberUtil.converString(dynamicBean.getDanmaku()));
        itemViewHolder.mCardView.setOnClickListener(
                v -> VideoDetailsActivity.launch((Activity) mContext,
                        Integer.valueOf(dynamicBean.getParam()), dynamicBean.getCover()));
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeadViewHolder(view);
    }

    
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeadViewHolder headViewHolder= (HeadViewHolder) holder;
    }

    static class HeadViewHolder extends RecyclerView.ViewHolder {

        public HeadViewHolder(View itemView) {

            super(itemView);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

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
            ButterKnife.bind(this, itemView);
        }
    }
}
