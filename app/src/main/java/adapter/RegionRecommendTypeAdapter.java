package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rxjava.myblibi.R;

import adapter.helper.AbsRecyclerViewAdapter;

/**
 * @author 小陈
 * @time 2018/1/24  17:20
 * @desc 分区推荐页面类型分类Icons的adapter
 */
public class RegionRecommendTypeAdapter extends AbsRecyclerViewAdapter {
   private int[] bangumiIcons;
   
   private String[]bangumiTitles;
   
   public RegionRecommendTypeAdapter(int[] bangumiIcons ,String[] bangumiTitles,RecyclerView recyclerView){
       super(recyclerView);
       this.bangumiIcons=bangumiIcons;
       this.bangumiTitles=bangumiTitles;
   }
   

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new RegionRecommendTypeAdapter.ItemViewHolder(
                LayoutInflater.from(getContext()).inflate(R.layout.layout_region_recommend_head, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder= (ItemViewHolder) holder;
            itemViewHolder.mItemIcon.setImageResource(bangumiIcons[position]);
            itemViewHolder.mItemText.setText(bangumiTitles[position]);
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return bangumiIcons.length;
    }
    
    private class ItemViewHolder extends  AbsRecyclerViewAdapter.ClickableViewHolder {

        ImageView mItemIcon;

        TextView mItemText;


        public ItemViewHolder(View itemView) {

            super(itemView);
            mItemIcon = $(R.id.iv_icon_centre);
            mItemText = $(R.id.tv_title);
        }
    }
}
