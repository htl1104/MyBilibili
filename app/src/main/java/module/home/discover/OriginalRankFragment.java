package module.home.discover;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.rxjava.myblibi.R;

import java.util.ArrayList;
import java.util.List;

import adapter.OriginalRankAdapter;
import base.RxLazyFragment;
import butterknife.BindView;
import module.entry.discover.OriginalRankInfo;
import module.video.VideoDetailsActivity;
import network.RetrofitHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import utils.ConstantUtil;
import utils.ToastUtil;

/**
 * @author 小陈
 * @time 2018/1/26  14:41
 * @desc 原创排行榜界面
 */
public class OriginalRankFragment  extends RxLazyFragment{

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;//下拉刷新

    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    private boolean mIsRefreshing = false;//下拉刷新的状态
    
    private String order;
    
    private List<OriginalRankInfo.RankBean.ListBean>orListBeans=new ArrayList<>();
    private OriginalRankAdapter mAdapter;

    public static OriginalRankFragment newInstance(String order){
        OriginalRankFragment fragment=new OriginalRankFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(ConstantUtil.EXTRA_ORDER, order);
        fragment.setArguments(mBundle);
        return fragment;
    }
    
    @Override
    public int getLayoutResId() {
        return  R.layout.fragment_original_rank;
    }

    @Override
    public void finishCreateView(Bundle state) {
        order=getArguments().getString(ConstantUtil.EXTRA_ORDER);
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
            orListBeans.clear();
            loadData();
        });
    }

    @Override
    protected void loadData() {
        RetrofitHelper.getRankAPI()
                .getOriginalRanks(order)
                .compose(bindToLifecycle())
                .map(originalRankInfo -> originalRankInfo.getRank().getList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listBeans -> {
                    orListBeans.addAll(listBeans.subList(0, 20));
                    finishTask();
                },throwable -> {
                    mSwipeRefreshLayout.setRefreshing(false);
                    ToastUtil.ShortToast("加载失败啦,请重新加载~");
                });
    }

    @Override
    protected void finishTask() {
        mSwipeRefreshLayout.setRefreshing(false);
        //mIsRefreshing = false;
        mAdapter.notifyDataSetChanged();
    }
    
    @Override
    protected void initRecyclerView() {
        mAdapter=new OriginalRankAdapter(mRecyclerView,orListBeans);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        mRecyclerView.setAdapter(mAdapter);

//        //视频详情界面
        mAdapter.setOnItemClickListener((position, holder) -> VideoDetailsActivity.launch(getActivity(),
                orListBeans.get(position).getAid(),
                orListBeans.get(position).getPic()));
    }  
 
}
