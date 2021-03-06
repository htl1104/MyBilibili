package module.home.discover;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.example.rxjava.myblibi.R;

import java.util.ArrayList;
import java.util.List;

import adapter.section.HomeRecommendBannerSection;
import adapter.section.HomeRecommendTopicSection;
import adapter.section.HomeRecommendedSection;
import base.RxLazyFragment;
import butterknife.BindView;
import module.entry.recommend.RecommendBannerInfo;
import module.entry.recommend.RecommendInfo;
import network.RetrofitHelper;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import utils.ConstantUtil;
import utils.SnackbarUtil;
import widget.CustomEmptyView;
import widget.banner.BannerEntity;
import widget.sectioned.SectionedRecyclerViewAdapter;

/**
 * @author 小陈
 * @time 2018/1/16  14:48
 * @desc 主页推荐界面
 */
public class HomeRecommendedFragment extends RxLazyFragment {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;//下拉刷新

    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    @BindView(R.id.empty_layout)
    CustomEmptyView mCustomEmptyView;

    private List<RecommendInfo.ResultBean> results = new ArrayList<>();

    private List<BannerEntity> banners = new ArrayList<>();

    private boolean mIsRefreshing = false;//刷新的状态


    private SectionedRecyclerViewAdapter mSectionedAdapter;


    private List<RecommendBannerInfo.DataBean> recommendBanners = new ArrayList<>();//首页Banner推荐

    public static HomeRecommendedFragment newInstance(){
        return new HomeRecommendedFragment();
    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_recommended;
    }

    @Override
    public void finishCreateView(Bundle state) {
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
    protected void initRecyclerView() {

        mSectionedAdapter = new SectionedRecyclerViewAdapter();
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
                .getRecommendedBannerInfo()
                .compose(bindToLifecycle())
                .map(RecommendBannerInfo::getData)
                .flatMap(new Func1<List<RecommendBannerInfo.DataBean>, Observable<RecommendInfo>>() {
                    @Override
                    public Observable<RecommendInfo> call(List<RecommendBannerInfo.DataBean> dataBeans) {
                       
                        recommendBanners.addAll(dataBeans);
                        return RetrofitHelper.getBiliAppAPI().getRecommendedInfo();
                    }
                })
                .compose(bindToLifecycle())
                .map(RecommendInfo::getResult)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultBeans ->{
                    results.addAll(resultBeans);
                    finishTask();
                },throwable -> {
                    //异常时显示空的View
                    initEmptyView();
                });
    }


    /**
     * 设置轮播banners
     */
    private void convertBanner() {
        Observable.from(recommendBanners)
                .compose(bindToLifecycle())
                .forEach(dataBean -> banners.add(new BannerEntity(dataBean.getValue(),
                        dataBean.getTitle(),dataBean.getImage())));
    }

    @Override
    protected void finishTask() {
        mSwipeRefreshLayout.setRefreshing(false);
        mIsRefreshing = false;
        hideEmptyView();
        convertBanner();
        //轮播图Section
        mSectionedAdapter.addSection(new HomeRecommendBannerSection(banners));

        int size = results.size();
        for (int i = 0; i < size; i++) {
            String type = results.get(i).getType();
            if (!TextUtils.isEmpty(type)) {
                switch (type) {
                    case ConstantUtil.TYPE_TOPIC:
                        //话题
                        mSectionedAdapter.addSection(new HomeRecommendTopicSection(getActivity(),
                                results.get(i).getBody().get(0).getCover(),
                                results.get(i).getBody().get(0).getTitle(),
                                results.get(i).getBody().get(0).getParam()));
                        break;
                    case ConstantUtil.TYPE_ACTIVITY_CENTER:
                        //活动中心
//                        mSectionedAdapter.addSection(new HomeRecommendActivityCenterSection(
//                            getActivity(),
//                            results.get(i).getBody()));
//                        break;
                    default://推荐界面Section
                        mSectionedAdapter.addSection(new HomeRecommendedSection(
                                getActivity(),
                                results.get(i).getHead().getTitle(),
                                results.get(i).getType(),
                                results.get(1).getHead().getCount(),
                                results.get(i).getBody()));
                        break;
                }
            }

//            String style = results.get(i).getHead().getStyle();
//            if (style.equals(ConstantUtil.STYLE_PIC)) {
//                mSectionedAdapter.addSection(new HomeRecommendPicSection(getActivity(),
//                        results.get(i).getBody().get(0).getCover(),
//                        results.get(i).getBody().get(0).getParam()));
//            }
        }
        mSectionedAdapter.notifyDataSetChanged();
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



    public void initEmptyView() {

        mSwipeRefreshLayout.setRefreshing(false);
        mCustomEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mCustomEmptyView.setEmptyImage(R.drawable.img_tips_error_load_error);
        mCustomEmptyView.setEmptyText("加载失败~(≧▽≦)~啦啦啦.");
        SnackbarUtil.showMessage(mRecyclerView, "数据加载失败,请重新加载或者检查网络是否链接");
    }

    public void hideEmptyView() {

        mCustomEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    private void clearData() {

        banners.clear();
        recommendBanners.clear();
        results.clear();
        mIsRefreshing = true;
        mSectionedAdapter.removeAllSections();
    }

    private void setRecycleNoScroll() {

        mRecyclerView.setOnTouchListener((v, event) -> mIsRefreshing);
    }
}
