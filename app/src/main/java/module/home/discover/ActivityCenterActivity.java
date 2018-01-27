package module.home.discover;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.rxjava.myblibi.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import adapter.ActivityCenterAdapter;
import adapter.helper.EndlessRecyclerOnScrollListener;
import adapter.helper.HeaderViewRecyclerAdapter;
import base.BaseActivity;
import butterknife.BindView;
import entity.discover.ActivityCenterInfo;
import module.common.BrowserActivity;
import network.RetrofitHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import utils.LogDog;
import utils.ToastUtil;

/**
 * @author 小陈
 * @time 2018/1/27  10:14
 * @desc 活动中心界面
 */
public class ActivityCenterActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private View loadMoreView;

    private int pageNum = 1;

    private int pageSize = 20;

    private boolean mIsRefreshing = false;//刷新的状态

    private ActivityCenterAdapter mAdapter;
    
    private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;
    
    private List<ActivityCenterInfo.ListBean> activityCenters=new ArrayList<>();

    private EndlessRecyclerOnScrollListener mEndlessRecyclerOnScrollListener;
    @Override
    public int getLayoutId() {
        return R.layout.activity_activity_center;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initRefreshLayout();
        initRecyclerView();
    }

    @Override
    public void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);//设置刷新状态，true表示正在刷新，false表示取消刷新
                mIsRefreshing = true;
                loadData();
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(() ->{//设置监听，需要重写onRefresh()方法，顶部下拉时会调用这个方法，在里面实现请求数据的逻辑
            clearData();
            loadData();
        });
    }

    @Override
    public void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ActivityCenterAdapter(mRecyclerView, activityCenters);
        mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        mRecyclerView.setAdapter(mHeaderViewRecyclerAdapter);
        createLoadMoreView();
        mEndlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {

            @Override
            public void onLoadMore(int currentPage) {

                pageNum++;
                loadData();
                loadMoreView.setVisibility(View.VISIBLE);
            }
        };
        mRecyclerView.addOnScrollListener(mEndlessRecyclerOnScrollListener);
        mAdapter.setOnItemClickListener((position, holder) -> BrowserActivity.launch(
                ActivityCenterActivity.this, activityCenters.get(position).getLink(),
                activityCenters.get(position).getTitle()));
        
        setRecycleNoScroll();
    }

    @Override
    public void loadData() {
        RetrofitHelper.getBiliAPI()
                .getActivityCenterList(pageNum,pageSize)
                .compose(bindToLifecycle())
                .delay(1000, TimeUnit.MILLISECONDS)
                .map(ActivityCenterInfo ::getList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(listBeans -> {
                    if (listBeans.size() < pageSize) {
                        loadMoreView.setVisibility(View.GONE);
                        mHeaderViewRecyclerAdapter.removeFootView();
                    }
                })
                .subscribe(listBeans -> {
                    activityCenters.addAll(listBeans);
                    finishTask();
                },throwable -> {
                    LogDog.e(throwable.getMessage());
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    loadMoreView.setVisibility(View.GONE);
                    ToastUtil.ShortToast("加载失败啦,请重新加载~");
                });
    }

    @Override
    public void finishTask() {
        mIsRefreshing = false;
        loadMoreView.setVisibility(View.GONE);
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

        if (pageNum * pageSize - pageSize - 1 > 0) {
            mAdapter.notifyItemRangeChanged(pageNum * pageSize - pageSize - 1, pageSize);
        } else {
            mAdapter.notifyDataSetChanged();
        }
     
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("活动中心");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void clearData() {
        pageNum = 1;
        mIsRefreshing = true;
        activityCenters.clear();
        mEndlessRecyclerOnScrollListener.refresh();
     
    }

    private void createLoadMoreView() {

        loadMoreView = LayoutInflater.from(ActivityCenterActivity.this)
                .inflate(R.layout.layout_load_more, mRecyclerView, false);
        mHeaderViewRecyclerAdapter.addFooterView(loadMoreView);
        loadMoreView.setVisibility(View.GONE);
    }


    private void setRecycleNoScroll() {

        mRecyclerView.setOnTouchListener((v, event) -> mIsRefreshing);
    }
}
