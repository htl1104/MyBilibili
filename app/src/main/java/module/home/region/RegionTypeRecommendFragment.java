package module.home.region;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.rxjava.myblibi.R;

import java.util.ArrayList;
import java.util.List;

import adapter.section.RegionRecommendBannerSection;
import adapter.section.RegionRecommendTypesSection;
import base.RxLazyFragment;
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
 * @time 2018/1/24  15:09
 * @desc 分区推荐页面
 */
public class RegionTypeRecommendFragment extends RxLazyFragment {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;//下拉刷新

    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    private boolean mIsRefreshing = false;//刷新的状态
    private int rid;

    private SectionedRecyclerViewAdapter mSectionedAdapter;


    private List<BannerEntity> bannerEntities = new ArrayList<>();

    private List<RegionRecommendInfo.DataBean.BannerBean.TopBean> banners = new ArrayList<>();

    private List<RegionRecommendInfo.DataBean.RecommendBean> recommends = new ArrayList<>();

    private List<RegionRecommendInfo.DataBean.NewBean> news = new ArrayList<>();

    private List<RegionRecommendInfo.DataBean.DynamicBean> dynamics = new ArrayList<>();


    public static Fragment newInstance(int rid) {
        RegionTypeRecommendFragment fragment = new RegionTypeRecommendFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(ConstantUtil.EXTRA_RID,rid);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_region_recommend;
    }

    @Override
    public void finishCreateView(Bundle state) {
        rid=getArguments().getInt(ConstantUtil.EXTRA_RID);
        initRefreshLayout();
        initRecyclerView();
    }
    
    @Override
    protected void initRecyclerView() {
      mSectionedAdapter=new SectionedRecyclerViewAdapter();

        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);

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
    protected void loadData() {
        RetrofitHelper.getBiliAppAPI()
                .getRegionRecommends(rid)
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
    protected void finishTask() {
       mSwipeRefreshLayout.setRefreshing(false);
       mIsRefreshing=false;
       convertBanner();
       
       //添加分区推荐界面轮播图Section
       mSectionedAdapter.addSection(new RegionRecommendBannerSection(bannerEntities));
       //添加分区
       mSectionedAdapter.addSection(new RegionRecommendTypesSection(getActivity(),rid));
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
            clearData();
            loadData();
        });
    }

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
