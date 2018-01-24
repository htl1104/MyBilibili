package adapter.section;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.rxjava.myblibi.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import widget.banner.BannerEntity;
import widget.banner.BannerView;
import widget.sectioned.StatelessSection;

/**
 * @author 小陈
 * @time 2018/1/24  16:38
 * @desc 分区推荐界面轮播图Section
 */
public class RegionRecommendBannerSection extends StatelessSection {

    private List<BannerEntity> banners = new ArrayList<>();
    
    public RegionRecommendBannerSection(List<BannerEntity>banners) {
        super(R.layout.layout_banner, R.layout.layout_home_recommend_empty);
        this.banners = banners;
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
        bannerViewHolder.mBannerView.delayTime(5).build(banners);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        public ItemViewHolder(View itemView) {

            super(itemView);
        }
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.home_recommended_banner)
        BannerView mBannerView;


        BannerViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
