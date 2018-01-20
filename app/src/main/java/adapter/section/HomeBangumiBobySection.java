package adapter.section;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rxjava.myblibi.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import entity.bangumi.BangumiAppIndexInfo;
import utils.LogDog;
import widget.sectioned.StatelessSection;

/**
 * @author 小陈
 * @time 2018/1/20  10:00
 * @desc 首页番剧界面内容Section
 */
public class HomeBangumiBobySection extends StatelessSection{
    private Context mContext;
    private List<BangumiAppIndexInfo.ResultBean.AdBean.BodyBean> bodyBeanList;
    
    public HomeBangumiBobySection(Context context, List<BangumiAppIndexInfo.ResultBean.AdBean.BodyBean> bodyBeanList) {
        super(R.layout.layout_home_bangumi_boby,
                R.layout.layout_home_recommend_empty);
        this.mContext=context;
        this.bodyBeanList=bodyBeanList;
        LogDog.i("首页番剧界面内容Section");
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new EmptyViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new BodyViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
       BodyViewHolder bodyViewHolder= (BodyViewHolder) holder;
        Glide.with(mContext)
                .load(bodyBeanList.get(0).getImg())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(bodyViewHolder.mImageView);
    }
    
    static class BodyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.card_view)
        CardView mCardView;
        
        @BindView(R.id.home_bangumi_boby_image)
        ImageView mImageView;
        
        public BodyViewHolder(View itemView) {
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
