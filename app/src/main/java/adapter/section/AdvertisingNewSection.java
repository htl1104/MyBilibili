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
import rx.RxBus;
import utils.NumberUtil;
import widget.sectioned.StatelessSection;

/**
 * @author 小陈
 * @time 2018/1/25  17:51
 * @desc 广告推荐最新视频section
 */
public class AdvertisingNewSection extends StatelessSection {
    private Context mContext;
  
    private List<RegionRecommendInfo.DataBean.NewBean> mNewBeans;
    public AdvertisingNewSection(Context context,List<RegionRecommendInfo.DataBean.NewBean> news) {
        super(R.layout.layout_region_recommend_new_head,R.layout.layout_region_recommend_card_item);
        this.mContext=context;
        this.mNewBeans=news;
    }

    @Override
    public int getContentItemsTotal() {
        return mNewBeans.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder= (ItemViewHolder) holder;
        RegionRecommendInfo.DataBean.NewBean newBean = mNewBeans.get(position);
        Glide.with(mContext)
                .load(Uri.parse(newBean.getCover()))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(itemViewHolder.mImage);

        itemViewHolder.mTitle.setText(newBean.getTitle());
        itemViewHolder.mPlay.setText(NumberUtil.converString(newBean.getPlay()));
        itemViewHolder.mReview.setText(NumberUtil.converString(newBean.getDanmaku()));
        itemViewHolder.mCardView.setOnClickListener(view -> {
            VideoDetailsActivity.launch((Activity) mContext,
                    Integer.valueOf(newBean.getParam()), newBean.getCover());
        });
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeadViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeadViewHolder headViewHolder= (HeadViewHolder) holder;
//        if (mRid == ConstantUtil.ADVERTISING_RID) {
//            headViewHolder.mMore.setVisibility(View.GONE);
//            headViewHolder.mTypeIcon.setImageResource(R.drawable.ic_header_movie_relate);
//            headViewHolder.mTypeTv.setText("最新投稿");
//        } else {
            headViewHolder.mMore.setVisibility(View.VISIBLE);
            headViewHolder.mTypeIcon.setImageResource(R.drawable.ic_header_new);
            headViewHolder.mTypeTv.setText("最新视频");
            headViewHolder.mMore.setOnClickListener(v -> RxBus.getInstance().post(0));
//        }
    }

    static class HeadViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_type_img)
        ImageView mTypeIcon;

        @BindView(R.id.item_type_tv)
        TextView mTypeTv;

        @BindView(R.id.item_type_more)
        TextView mMore;


        public HeadViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
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
