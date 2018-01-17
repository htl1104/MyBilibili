package module.common;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.rxjava.myblibi.R;

import adapter.LiveAppIndexAdapter;
import base.BaseActivity;
import butterknife.BindView;
import entity.live.LiveAppIndexInfo;
import network.RetrofitHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import utils.LogDog;

/**
 * @author 小陈
 * @time 2018/1/17  10:17
 * @desc 分区直播页面
 */
public class LiveAppIndexActivity extends BaseActivity {
    
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    private LiveAppIndexAdapter mLiveAppIndexAdapter;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_live_app_index;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initRefreshLayout();
        initRecyclerView();
    }

    @Override
    public void initRecyclerView() {

        mLiveAppIndexAdapter = new LiveAppIndexAdapter(LiveAppIndexActivity.this);
        mRecyclerView.setAdapter(mLiveAppIndexAdapter);
        GridLayoutManager layout = new GridLayoutManager(LiveAppIndexActivity.this, 12);
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        layout.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {

                return mLiveAppIndexAdapter.getSpanSize(position);
            }
        });

        mRecyclerView.setLayoutManager(layout);
    }

    @Override
    public void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this::loadData);
        mSwipeRefreshLayout.post(() -> {

            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
        });
    }

    @Override
    public void loadData() {
        RetrofitHelper.getLiveAPI()
                .getLiveAppIndex()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<LiveAppIndexInfo>() {
                    @Override
                    public void call(LiveAppIndexInfo liveAppIndexInfo) {
                        mLiveAppIndexAdapter.setLiveInfo(liveAppIndexInfo);
                        finishTask();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogDog.i(throwable.toString());      
                    }
                });
                
    }

    @Override
    public void finishTask() {
        mSwipeRefreshLayout.setRefreshing(false);
        mLiveAppIndexAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("直播");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(v -> finish());
    }
}
