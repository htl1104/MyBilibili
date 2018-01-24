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
import entity.bangumi.BangumiIndexInfo;

/**
 * @author 小陈
 * @time 2018/1/24  10:58
 * @desc 番剧索引的Adapter
 */
public class BangumiIndexAdapter extends AbsRecyclerViewAdapter {
    private List<BangumiIndexInfo.ResultBean.CategoryBean> mCategoryBeans;
    
    public BangumiIndexAdapter(RecyclerView recyclerView, List<BangumiIndexInfo.ResultBean.CategoryBean> categoryBeans) {
        super(recyclerView);
        this.mCategoryBeans=categoryBeans;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext())
                .inflate(R.layout.item_bangumi_index, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder= (ItemViewHolder) holder;
            BangumiIndexInfo.ResultBean.CategoryBean categoryBean = mCategoryBeans.get(position);
            
            Glide.with(getContext())
                    .load(categoryBean.getCover())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//表示既缓存原始图片，也缓存转换过后的图片。
                    .placeholder(R.drawable.bili_default_image_tv)//佔位圖
                    .dontAnimate()
                    .into(itemViewHolder.mImageView);
            
            itemViewHolder.mTextView.setText(categoryBean.getTag_name());
            
            
            
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mCategoryBeans.size();
    }
    
    public class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder{

        ImageView mImageView;

        TextView mTextView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            mImageView = $(R.id.item_img);
            mTextView = $(R.id.item_title);
        }
    }
    
}
