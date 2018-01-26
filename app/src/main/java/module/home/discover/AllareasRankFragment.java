package module.home.discover;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.rxjava.myblibi.R;

import java.util.ArrayList;
import java.util.List;

import adapter.AllareasRankAdapter;
import base.RxLazyFragment;
import butterknife.BindView;
import module.entry.discover.AllareasRankInfo;
import module.video.VideoDetailsActivity;
import network.RetrofitHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import utils.ConstantUtil;
import utils.ToastUtil;

/**
 * @author 小陈
 * @time 2018/1/26  10:36
 * @desc 全区排行榜界面
 */
public class AllareasRankFragment extends RxLazyFragment{


    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;//下拉刷新

    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    private String type;
    
    private boolean mIsRefreshing = false;//刷新的状态

    private AllareasRankAdapter mAdapter;

    private List<AllareasRankInfo.RankBean.ListBean> allRanks = new ArrayList<>();
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_all_areas_rank;
    }

    @Override
    public void finishCreateView(Bundle state) {
        type = getArguments().getString(ConstantUtil.EXTRA_KEY);
        isPrepared=true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared||!isVisible) {
            return;
        }

        initRefreshLayout();
        initRecyclerView();

        isPrepared = false;
    }

    @Override
    protected void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);//设置下拉进度条的颜色主题
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);//设置刷新状态，true表示正在刷新，false表示取消刷新
                mIsRefreshing = true;
                loadData();
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(() ->{//设置监听，需要重写onRefresh()方法，顶部下拉时会调用这个方法，在里面实现请求数据的逻辑
            mIsRefreshing = true;
            allRanks.clear();
            loadData();
        });
    }

    @Override
    protected void loadData() {
        RetrofitHelper.getRankAPI()
                .getAllareasRanks(type)
                .compose(bindToLifecycle())
                .map(allareasRankInfo -> allareasRankInfo.getRank().getList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listBeans -> {
                    allRanks.addAll(listBeans.subList(0, 20));
                    finishTask();
                },throwable -> {
                    mSwipeRefreshLayout.setRefreshing(false);
                    ToastUtil.ShortToast("加载失败啦,请重新加载~");
                });
    }

    @Override
    protected void finishTask() {
        mSwipeRefreshLayout.setRefreshing(false);
        mIsRefreshing = false;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initRecyclerView() {
         mAdapter=new AllareasRankAdapter(mRecyclerView,allRanks);
         mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
         mRecyclerView.setAdapter(mAdapter);
         
         //视频详情界面
         mAdapter.setOnItemClickListener((position, holder) -> VideoDetailsActivity.launch(getActivity(),
                 allRanks.get(position).getAid(),
                 allRanks.get(position).getPic()));
    }

    public static AllareasRankFragment newInstance(String type) {
        AllareasRankFragment fragment = new AllareasRankFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(ConstantUtil.EXTRA_KEY, type);
        fragment.setArguments(mBundle);
        return fragment;
    }

  
    
    
}
