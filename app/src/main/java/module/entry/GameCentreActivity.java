package module.entry;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rxjava.myblibi.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import adapter.GameCentreAdapter;
import adapter.helper.HeaderViewRecyclerAdapter;
import base.BaseActivity;
import butterknife.BindView;
import entity.discover.GameCenterInfo;
import entity.discover.VipGameInfo;
import module.common.BrowserActivity;
import network.RetrofitHelper;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import utils.LogDog;
import widget.CircleProgressView;

/**
 * @author 小陈
 * @time 2018/1/15  14:06
 * @desc 游戏中心界面
 */
public class GameCentreActivity extends BaseActivity {

    @BindView(R.id.recycle)
    RecyclerView mRecycle;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.circle_progress)
    CircleProgressView mCircleProgressView;

    private List<GameCenterInfo.ItemsBean> items = new ArrayList<>();

    private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;

    private ImageView mVipGameImage;

    private VipGameInfo.DataBean mVipGameInfoData;

    @Override
    public int getLayoutId() {
        return R.layout.activity_game_center;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        loadData();
    }

    @Override
    public void loadData() {
        RetrofitHelper.getVipAPI()
                .getVipGame()
                .compose(bindToLifecycle())
                .doOnSubscribe(this::showProgressBar)
                .delay(2, TimeUnit.SECONDS)
                .flatMap(new Func1<VipGameInfo, Observable<String>>() {
                    @Override
                    public Observable<String> call(VipGameInfo vipGameInfo) {
                        mVipGameInfoData = vipGameInfo.getData();
                        return Observable.just(readAssetsJson());
                    }
                })
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        GameCenterInfo gameCenterInfo = new Gson().fromJson(s, GameCenterInfo.class);
                        items.addAll(gameCenterInfo.getItems());
                        finishTask();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogDog.e(throwable.toString());
                        hideProgressBar();
                    }
                })
        ;
    }

    /**
     * 读取assets下的json数据
     */
    private String readAssetsJson() {
        AssetManager assetManager = getAssets();
        try {
            InputStream is = assetManager.open("gamecenter.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();
            String str;
            while ((str = br.readLine()) != null) {
                stringBuilder.append(str);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("游戏中心");
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
    public void finishTask() {
        initRecyclerView();
        hideProgressBar();
    }

    @Override
    public void initRecyclerView() {
        mRecycle.setHasFixedSize(true);
        mRecycle.setLayoutManager(new LinearLayoutManager(GameCentreActivity.this));
        GameCentreAdapter mAdapter = new GameCentreAdapter(mRecycle, items);
        mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        createHeadView();
        mRecycle.setAdapter(mHeaderViewRecyclerAdapter);
    }

    private void createHeadView() {

        View headView = LayoutInflater.from(this)
                .inflate(R.layout.layout_vip_game_head_view, mRecycle, false);
        mVipGameImage = (ImageView) headView.findViewById(R.id.vip_game_image);
        Glide.with(GameCentreActivity.this).load(mVipGameInfoData.getImgPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(mVipGameImage);

        /**
         * 点击图片进入游戏页面的详情页面
         */
         mVipGameImage.setOnClickListener(v -> 
                 
                 BrowserActivity.launch(GameCentreActivity.this,
         mVipGameInfoData.getLink(), "年度大会员游戏礼包专区"));
        
        mHeaderViewRecyclerAdapter.addHeaderView(headView);
    }
}
