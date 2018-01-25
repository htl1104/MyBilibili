package module.home.region;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.rxjava.myblibi.R;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import adapter.pager.RegionPagerAdapter;
import base.BaseActivity;
import butterknife.BindView;
import entity.region.RegionTypesInfo;
import rx.Observable;
import rx.RxBus;
import rx.android.schedulers.AndroidSchedulers;
import utils.ConstantUtil;
import widget.NoScrollViewPager;

/**
 * @author 小陈
 * @time 2018/1/24  11:55
 * @desc 分区类型详情界面
 */
public class RegionTypeDetailsActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;

    @BindView(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTab;
    
    private RegionTypesInfo.DataBean mDataBean;

    private List<String> titles = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_region_type;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            mDataBean=bundle.getParcelable(ConstantUtil.EXTRA_PARTITION);
        }
        
        initViewPager();
        initRxBus();
    }

    private void initRxBus() {
        RxBus.getInstance().toObserverable(Integer.class)
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::switchPager);
    }

    private void initViewPager() {
        titles.add("推荐");

        Observable.from(mDataBean.getChildren())
                .subscribe(childrenBean -> {
                    titles.add(childrenBean.getName());
                });
        
        RegionPagerAdapter regionPagerAdapter=new RegionPagerAdapter(
                getSupportFragmentManager(),mDataBean.getTid(),titles,mDataBean.getChildren());
        mViewPager.setOffscreenPageLimit(titles.size());
        mViewPager.setAdapter(regionPagerAdapter);
        mSlidingTab.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);//设置默认的显示item

        //动态设置tabView的下划线宽度
        measureTabLayoutTextWidth(0);
    }

    @Override
    public void initToolBar() {

        mToolbar.setTitle(mDataBean.getName());
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


    public void measureTabLayoutTextWidth(int position) {

        String titleName = titles.get(position);
        TextView titleView = mSlidingTab.getTitleView(position);
        TextPaint paint = titleView.getPaint();
        float v = paint.measureText(titleName);
        mSlidingTab.setIndicatorWidth(v / 3);
    }



    public static void launch(Activity activity, RegionTypesInfo.DataBean dataBean) {

        Intent mIntent = new Intent(activity, RegionTypeDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantUtil.EXTRA_PARTITION, dataBean);
        mIntent.putExtras(bundle);
        activity.startActivity(mIntent);
    }

    private void switchPager(int position) {

        switch (position) {
            case 0:
                mViewPager.setCurrentItem(1);
                break;

            case 1:
                mViewPager.setCurrentItem(2);
                break;

            case 2:
                mViewPager.setCurrentItem(3);
                break;

            case 3:
                mViewPager.setCurrentItem(4);
                break;

            case 4:
                mViewPager.setCurrentItem(5);
                break;

            case 5:
                mViewPager.setCurrentItem(6);
                break;

            case 6:
                mViewPager.setCurrentItem(7);
                break;
        }
    }
}
