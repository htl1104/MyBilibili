package module.home;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.rxjava.myblibi.R;

import adapter.HomeRegionItemAdapter;
import adapter.helper.AbsRecyclerViewAdapter;
import base.RxLazyFragment;
import butterknife.BindView;

/**
 * @author 小陈
 * @time 2018/1/16  14:49
 * @desc 首页分区界面
 */
public class HomeRegionFragment extends RxLazyFragment {


    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
    
    public static HomeRegionFragment newInstance(){
        return new HomeRegionFragment();
    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_regio;
    }

    @Override
    public void finishCreateView(Bundle state) {

        loadData();
        initRecyclerView();
    }

    @Override
    protected void loadData() {
        super.loadData();
    }

    @Override
    protected void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        HomeRegionItemAdapter mAdapter = new HomeRegionItemAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        
        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                
            }
        });
    }
}
