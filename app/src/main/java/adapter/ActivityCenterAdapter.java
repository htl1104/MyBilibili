package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rxjava.myblibi.R;

import java.util.List;

import adapter.helper.AbsRecyclerViewAdapter;
import entity.discover.ActivityCenterInfo;

/**
 * @author 小陈
 * @time 2018/1/27  10:49
 * @desc 话题中心的Adapter
 */
public class ActivityCenterAdapter extends AbsRecyclerViewAdapter{
    private List<ActivityCenterInfo.ListBean> activityCenters; 
    
    public ActivityCenterAdapter(RecyclerView recyclerView,List<ActivityCenterInfo.ListBean> activityCenters) {
        super(recyclerView);
        this.activityCenters=activityCenters;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext())
                .inflate(R.layout.item_activity_center, parent, false));
    }

    @Override
    public int getItemCount() {
        //LogDog.i("activityCenters="+activityCenters.size());
        return activityCenters.size();
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder) {
            ItemViewHolder viewHolder= (ItemViewHolder) holder;
            ActivityCenterInfo.ListBean listBean = activityCenters.get(position);

            Glide.with(getContext())
                    .load(listBean.getCover())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//表示既缓存原始图片，也缓存转换过后的图片。
                    .placeholder(R.drawable.bili_default_image_tv)//佔位圖
                    .dontAnimate()
                    .into(viewHolder.mImage);

            viewHolder.mTitle.setText(listBean.getTitle());
            
        }
    }

    public class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        ImageView mImage;

        TextView mTitle;

        ImageView mState;


        public ItemViewHolder(View itemView) {

            super(itemView);
            mImage = $(R.id.item_image);
            mTitle = $(R.id.item_title);
            mState = $(R.id.item_state);
        }
    }
}
