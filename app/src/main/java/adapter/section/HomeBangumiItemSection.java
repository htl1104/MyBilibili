package adapter.section;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.rxjava.myblibi.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import entity.bangumi.BangumiIndexActivity;
import entity.bangumi.BangumiScheduleActivity;
import entity.bangumi.ChaseBangumiActivity;
import widget.sectioned.StatelessSection;

/**
 * @author 小陈
 * @time 2018/1/19  15:51
 * @desc 首页番剧顶部追番，放送表，索引条目Section
 */
public class HomeBangumiItemSection extends StatelessSection {
    
    private Context mContext;
    
    public HomeBangumiItemSection(Context context) {
        super(R.layout.layout_home_bangumi_top_item, R.layout.layout_home_recommend_empty);
        this.mContext=context;
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new HomeBangumiItemSection.EmptyViewHolder(view);
    }
    

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {

        return new HomeBangumiItemSection.TopItemViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HomeBangumiItemSection.TopItemViewHolder topItemViewHolder
                = (HomeBangumiItemSection.TopItemViewHolder) holder;
        //前往追番
        topItemViewHolder.mChaseBangumi.setOnClickListener(v -> mContext.startActivity(
                new Intent(mContext, ChaseBangumiActivity.class)));
        //前往番剧放送表
        topItemViewHolder.mBangumiSchedule.setOnClickListener(v -> mContext.startActivity(
                new Intent(mContext, BangumiScheduleActivity.class)));
        //前往番剧索引
        topItemViewHolder.mBangumiIndex.setOnClickListener(v -> mContext.startActivity(
                new Intent(mContext, BangumiIndexActivity.class)));
    }

    static class EmptyViewHolder extends RecyclerView.ViewHolder{

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
    
    static class  TopItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_chase_bangumi)
        TextView mChaseBangumi;

        @BindView(R.id.layout_bangumi_schedule)
        TextView mBangumiSchedule;

        @BindView(R.id.layout_bangumi_index)
        TextView mBangumiIndex;


        TopItemViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
