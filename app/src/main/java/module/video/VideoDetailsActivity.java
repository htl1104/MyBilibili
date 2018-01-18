package module.video;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rxjava.myblibi.R;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;
import butterknife.BindView;
import entity.video.VideoDetailsInfo;
import entity.video.VideoIntroductionFragment;
import network.RetrofitHelper;
import network.auxiliary.UrlHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import utils.ConstantUtil;

/**
 * @author 小陈
 * @time 2018/1/18  15:27
 * @desc 视频详情界面
 */
public class VideoDetailsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.video_preview)
    ImageView mVideoPreview;

    @BindView(R.id.tab_layout)
    SlidingTabLayout mSlidingTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.fab)
    FloatingActionButton mFAB;

    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.tv_player)
    TextView mTvPlayer;

    @BindView(R.id.tv_av)
    TextView mAvText;

    private List<Fragment> fragments = new ArrayList<>();

    private List<String> titles = new ArrayList<>();

    private int av;

    private String imgUrl;

    private VideoDetailsInfo.DataBean mVideoDetailsInfo;


    @Override
    public int getLayoutId() {
        return R.layout.activity_video_details;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            av = intent.getIntExtra(ConstantUtil.EXTRA_AV, -1);
            imgUrl = intent.getStringExtra(ConstantUtil.EXTRA_IMG_URL);
        }

    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mAvText.setText("av" + av);

        Glide.with(VideoDetailsActivity.this)
                .load(UrlHelper.getClearVideoPreviewUrl(imgUrl))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(mVideoPreview);
        
        loadData();
        
        mFAB.setClickable(false);
        mFAB.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray_20)));
        mFAB.setTranslationY(-getResources().getDimension(R.dimen.floating_action_button_size_half));
        
        //视频详情页面
//        mFAB.setOnClickListener(v -> VideoPlayerActivity.launch(VideoDetailsActivity.this,
//                mVideoDetailsInfo.getPages().get(0).getCid(), mVideoDetailsInfo.getTitle()));

    }

    @Override
    public void loadData() {
        RetrofitHelper.getBiliAppAPI()
                .getVideoDetails(av)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<VideoDetailsInfo>() {
                    @Override
                    public void call(VideoDetailsInfo videoDetailsInfo) {
                        mVideoDetailsInfo = videoDetailsInfo.getData();
                        finishTask();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        mFAB.setClickable(false);
                        mFAB.setBackgroundTintList(ColorStateList.valueOf(
                                getResources().getColor(R.color.gray_20)));
                    }
                });
    }


    @Override
    public void finishTask() {

        mFAB.setClickable(true);
        mFAB.setBackgroundTintList(
                ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        mCollapsingToolbarLayout.setTitle("");

        if (TextUtils.isEmpty(imgUrl)) {
            Glide.with(VideoDetailsActivity.this)
                    .load(mVideoDetailsInfo)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.bili_default_image_tv)
                    .dontAnimate()
                    .into(mVideoPreview);
        }

        VideoIntroductionFragment mVideoIntroductionFragment = VideoIntroductionFragment.newInstance(
                av);
        VideoCommentFragment mVideoCommentFragment = VideoCommentFragment.newInstance(av);

        fragments.add(mVideoIntroductionFragment);
        fragments.add(mVideoCommentFragment);

     setPagerTitle(String.valueOf(mVideoDetailsInfo.getStat().getReply()));
    }

    private void setPagerTitle(String num) {

        titles.add("简介");
        titles.add("评论" + "(" + num + ")");

        VideoDetailsPagerAdapter mAdapter = new VideoDetailsPagerAdapter(getSupportFragmentManager(),
                fragments, titles);

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mSlidingTabLayout.setViewPager(mViewPager);
        measureTabLayoutTextWidth(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }


            @Override
            public void onPageSelected(int position) {

                measureTabLayoutTextWidth(position);
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void measureTabLayoutTextWidth(int position) {

        String title = titles.get(position);
        TextView titleView = mSlidingTabLayout.getTitleView(position);
        TextPaint paint = titleView.getPaint();
        float textWidth = paint.measureText(title);
        mSlidingTabLayout.setIndicatorWidth(textWidth / 3);
    }



    public static void launch(Activity activity, int aid, String imgUrl) {

        Intent intent = new Intent(activity, VideoDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ConstantUtil.EXTRA_AV, aid);
        intent.putExtra(ConstantUtil.EXTRA_IMG_URL, imgUrl);
        activity.startActivity(intent);
    }


    public static class VideoDetailsPagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> fragments;

        private List<String> titles;


        VideoDetailsPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {

            super(fm);
            this.fragments = fragments;
            this.titles = titles;
        }


        @Override
        public Fragment getItem(int position) {

            return fragments.get(position);
        }


        @Override
        public int getCount() {

            return fragments.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {

            return titles.get(position);
        }
    }
}
