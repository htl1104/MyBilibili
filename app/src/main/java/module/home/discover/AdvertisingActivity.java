package module.home.discover;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.rxjava.myblibi.R;

import java.util.ArrayList;
import java.util.List;

import adapter.section.RegionRecommendBannerSection;
import adapter.section.RegionRecommendDynamicSection;
import adapter.section.RegionRecommendHotSection;
import adapter.section.RegionRecommendNewSection;
import base.BaseActivity;
import butterknife.BindView;
import entity.region.RegionRecommendInfo;
import network.RetrofitHelper;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import utils.ConstantUtil;
import utils.LogDog;
import utils.ToastUtil;
import widget.banner.BannerEntity;
import widget.sectioned.SectionedRecyclerViewAdapter;

/**
 * @author 小陈
 * @time 2018/1/25  16:23
 * @desc 分区广告界面
 */
public class AdvertisingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean mIsRefreshing = false;//刷新的状态

    private SectionedRecyclerViewAdapter mSectionedAdapter;

    private List<BannerEntity> bannerEntities = new ArrayList<>();
    
    private List<RegionRecommendInfo.DataBean.BannerBean.TopBean> banners = new ArrayList<>();

    private List<RegionRecommendInfo.DataBean.RecommendBean> recommends = new ArrayList<>();

    private List<RegionRecommendInfo.DataBean.NewBean> news = new ArrayList<>();

    private List<RegionRecommendInfo.DataBean.DynamicBean> dynamics = new ArrayList<>();
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_advertising;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        LogDog.i("加载广告页面");
        initRefreshLayout();
        initRecyclerView();
    }


    @Override
    public void initRefreshLayout() {
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
            clearData();
            loadData();
        });
    }

    @Override
    public void initRecyclerView() {
        mSectionedAdapter = new SectionedRecyclerViewAdapter();
      
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {

                switch (mSectionedAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 2;

                    case SectionedRecyclerViewAdapter.VIEW_TYPE_FOOTER:
                        return 2;

                    default:
                        return 1;
                }
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mSectionedAdapter);
        setRecycleNoScroll();

    }

    @Override
    public void loadData() {
        RetrofitHelper.getBiliAppAPI()
                .getRegionRecommends(ConstantUtil.ADVERTISING_RID)
                .compose(bindToLifecycle())
                .map(RegionRecommendInfo::getData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataBean -> {

                    banners.addAll(dataBean.getBanner().getTop());
                    recommends.addAll(dataBean.getRecommend());
                    news.addAll(dataBean.getNewX());
                    dynamics.addAll(dataBean.getDynamic());
                    finishTask();
                },throwable -> {
                    LogDog.e(throwable.getMessage());
                    mSwipeRefreshLayout.setRefreshing(false);
                    ToastUtil.ShortToast("加载失败啦,请重新加载~");
                });                
    }

    @Override
    public void finishTask() {
        mSwipeRefreshLayout.setRefreshing(false);
        mIsRefreshing=false;
        convertBanner();

        //添加广告推荐界面轮播图Section
        mSectionedAdapter.addSection(new RegionRecommendBannerSection(bannerEntities));

        //添加广告推荐热门推荐section
        mSectionedAdapter.addSection(new RegionRecommendHotSection(this, ConstantUtil.ADVERTISING_RID,recommends));

        //添加广告推荐最新视频section
        mSectionedAdapter.addSection(new RegionRecommendNewSection(this, ConstantUtil.ADVERTISING_RID,news));

        //添加广告推荐全区动态section
        mSectionedAdapter.addSection(new RegionRecommendDynamicSection(this, dynamics));
        
        
        mSectionedAdapter.notifyDataSetChanged();
    }


    /**
     * 设置轮播banners
     */
    private void convertBanner() {
        LogDog.i("banners="+banners.size());
        Observable.from(banners)
                .compose(bindToLifecycle())
                .forEach(dataBean -> bannerEntities.add(new BannerEntity(dataBean.getUri(),
                        dataBean.getTitle(),dataBean.getImage())));
    }
    

    @Override
    public void initToolBar() {
        mToolbar.setTitle("广告");
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

    /**
     * 清除数据
     */
    private void clearData() {
        bannerEntities.clear();
        banners.clear();
        recommends.clear();
        news.clear();
        dynamics.clear();
        mIsRefreshing = true;
        mSectionedAdapter.removeAllSections();
    }

    private void setRecycleNoScroll() {

        mRecyclerView.setOnTouchListener((v, event) -> mIsRefreshing);
    }
    
}
