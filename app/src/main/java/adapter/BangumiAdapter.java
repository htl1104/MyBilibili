package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * @author 小陈
 * @time 2018/1/19  15:09
 * @desc 首页番剧adapter
 */
public class BangumiAdapter extends RecyclerView.Adapter{
    
    private Context mContext;
    public BangumiAdapter(Context context){
        this.mContext=context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
