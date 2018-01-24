package entity.bangumi;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.rxjava.myblibi.R;

import java.util.ArrayList;
import java.util.List;

import adapter.BangumiIndexAdapter;
import adapter.helper.HeaderViewRecyclerAdapter;
import base.BaseActivity;
import butterknife.BindView;
import network.RetrofitHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import utils.LogDog;
import widget.CircleProgressView;

/**
 * @author 小陈
 * @time 2018/1/19  16:24
 * @desc 番剧索引界面
 */
public class BangumiIndexActivity extends BaseActivity {

    @BindView(R.id.recycle)
    RecyclerView mRecycle;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.circle_progress)
    CircleProgressView mCircleProgressView;//进度条

    private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;

    private GridLayoutManager mGridLayoutManager;
    
    private List<BangumiIndexInfo.ResultBean.CategoryBean> mCategoryBeans=new ArrayList<>();
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_bangumi_index;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        loadData();
    }

    @Override
    public void loadData() {
        RetrofitHelper.getBangumiAPI()
                .getBangumiIndex()
                .compose(bindToLifecycle())
                .doOnSubscribe(this::showProgressBar)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BangumiIndexInfo>() {
                    @Override
                    public void call(BangumiIndexInfo bangumiIndexInfo) {
                        mCategoryBeans.addAll(bangumiIndexInfo.getResult().getCategory());
                        finishTask();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogDog.e(throwable.toString());
                        hideProgressBar();
                    }
                });
                
    }

    @Override
    public void finishTask() {
       initRecyclerView();
       hideProgressBar();
    }

    @Override
    public void initRecyclerView() {
        mRecycle.setHasFixedSize(true);
        mGridLayoutManager=new GridLayoutManager(BangumiIndexActivity.this,3);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (0==position?mGridLayoutManager.getSpanCount():1);
            }
        });
        mRecycle.setLayoutManager(mGridLayoutManager);
        BangumiIndexAdapter bangumiIndexAdapter=new BangumiIndexAdapter(mRecycle,mCategoryBeans);
        mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(bangumiIndexAdapter);
        createHeadLayout();
        mRecycle.setAdapter(mHeaderViewRecyclerAdapter);
    }

    
    

    @Override
    public void showProgressBar() {

        mCircleProgressView.setVisibility(View.VISIBLE);
        mCircleProgressView.spin();
        mRecycle.setVisibility(View.GONE);
    }


    @Override
    public void hideProgressBar() {

        mCircleProgressView.setVisibility(View.GONE);
        mCircleProgressView.stopSpinning();
        mRecycle.setVisibility(View.VISIBLE);
    }



    @Override
    public void initToolBar() {
        mToolbar.setTitle("番剧索引");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //mToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * 添加頭部
     */
    private void createHeadLayout() {

        View headView = LayoutInflater.from(this)
                .inflate(R.layout.layout_bangumi_index_head, mRecycle, false);
        mHeaderViewRecyclerAdapter.addHeaderView(headView);
    }

}
