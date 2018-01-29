package module.home.discover;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.rxjava.myblibi.R;

import java.util.ArrayList;
import java.util.List;

import adapter.TopicCenterAdapter;
import adapter.helper.HeaderViewRecyclerAdapter;
import base.BaseActivity;
import butterknife.BindView;
import entity.discover.TopicCenterInfo;
import module.common.BrowserActivity;
import network.RetrofitHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import utils.LogDog;
import utils.ToastUtil;

/**
 * @author 小陈
 * @time 2018/1/27  10:13
 * @desc 话题中心界面
 */
public class TopicCenterActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private List<TopicCenterInfo.ListBean> topicCenters = new ArrayList<>();

    private TopicCenterAdapter mAdapter;

    private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;

    private boolean mIsRefreshing = false;//刷新的状态
    @Override
    public int getLayoutId() {
        return R.layout.activity_top_center;
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
    public void loadData() {
        RetrofitHelper.getBiliAPI()
                .getTopicCenterList()
                .compose(bindToLifecycle())
                .map(TopicCenterInfo::getList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicCenterInfos ->{
                    topicCenters.addAll(topicCenterInfos);
                    finishTask();
                },throwable -> {
                    LogDog.e(throwable.getMessage());
                    ToastUtil.ShortToast("加载失败啦,请重新加载~");
                } );
                
    }

    @Override
    public void finishTask() {

        mIsRefreshing = false;
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new TopicCenterAdapter(mRecyclerView, topicCenters);
        //mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((position, holder) -> BrowserActivity.launch(
                TopicCenterActivity.this, topicCenters.get(position).getLink(),
                topicCenters.get(position).getTitle()));

        setRecycleNoScroll();
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("话题中心");
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
        topicCenters.clear();
        mIsRefreshing =true;
    }

    private void setRecycleNoScroll() {

        mRecyclerView.setOnTouchListener((v, event) -> mIsRefreshing);
    }

}
