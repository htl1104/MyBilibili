package module.home.region;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.rxjava.myblibi.R;

import java.util.ArrayList;
import java.util.List;

import adapter.section.RegionDetailsHotVideoSection;
import adapter.section.RegionDetailsNewsVideoSection;
import base.RxLazyFragment;
import butterknife.BindView;
import entity.region.RegionDetailsInfo;
import network.RetrofitHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import utils.ConstantUtil;
import utils.LogDog;
import utils.ToastUtil;
import widget.CircleProgressView;
import widget.sectioned.SectionedRecyclerViewAdapter;

/**
 * @author 小陈
 * @time 2018/1/24  15:20
 * @desc 分区对应类型列表详情界面
 */
public class RegionTypeDetailsFragment extends RxLazyFragment {

    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    @BindView(R.id.circle_progress)
    CircleProgressView mCircleProgressView;

    private SectionedRecyclerViewAdapter mSectionedRecyclerViewAdapter;

    private List<RegionDetailsInfo.DataBean.NewBean> newsVideos = new ArrayList<>();

    private List<RegionDetailsInfo.DataBean.RecommendBean> recommendVideos = new ArrayList<>();

    private int rid;
    public static Fragment newInstance(int tid) {
        RegionTypeDetailsFragment fragment = new RegionTypeDetailsFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(ConstantUtil.EXTRA_RID,tid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_region_details;
    }

    @Override
    public void finishCreateView(Bundle state) {
        rid = getArguments().getInt(ConstantUtil.EXTRA_RID);

        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared||!isVisible) {
            return;
        }
        
        showProgressBar();

        initRecyclerView();
        loadData();
        isPrepared = false;
    }

 
    @Override
    protected void initRecyclerView() {
        mSectionedRecyclerViewAdapter = new SectionedRecyclerViewAdapter();
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 1);

        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {

                switch (mSectionedRecyclerViewAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 1;

                    default:
                        return 1;
                }
            }
        });

        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mSectionedRecyclerViewAdapter);
    }

    @Override
    protected void loadData() {
        RetrofitHelper.getBiliAppAPI()
                .getRegionDetails(rid)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RegionDetailsInfo>() {
                    @Override
                    public void call(RegionDetailsInfo regionDetailsInfo) {

                        recommendVideos.addAll(regionDetailsInfo.getData().getRecommend());
                        newsVideos.addAll(regionDetailsInfo.getData().getNewX());
                        finishTask();
                    }
                },throwable -> {
                    LogDog.e(throwable.getMessage());
                    hideProgressBar();
                    ToastUtil.ShortToast("加载失败啦,请重新加载~");
                });
    }


    @Override
    protected void finishTask() {
        hideProgressBar();
        
        //分区详情最热视频section
        mSectionedRecyclerViewAdapter.addSection(
                new RegionDetailsHotVideoSection(getActivity(), recommendVideos));
        //分区详情最新视频section
        mSectionedRecyclerViewAdapter.addSection(
                new RegionDetailsNewsVideoSection(getActivity(), newsVideos));
       
        mSectionedRecyclerViewAdapter.notifyDataSetChanged();
        
    }

    @Override
    protected void showProgressBar() {

        mCircleProgressView.setVisibility(View.VISIBLE);
        mCircleProgressView.spin();
    }


    @Override
    protected void hideProgressBar() {

        mCircleProgressView.setVisibility(View.GONE);
        mCircleProgressView.stopSpinning();
    }
}
