package module.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.rxjava.myblibi.R;

import adapter.HomeRegionItemAdapter;
import adapter.helper.AbsRecyclerViewAdapter;
import base.RxLazyFragment;
import butterknife.BindView;
import module.common.LiveAppIndexActivity;
import module.entry.GameCentreActivity;

/**
 * @author 小陈
 * @time 2018/1/16  14:49
 * @desc 首页分区界面
 */
public class HomeRegionFragment extends RxLazyFragment {


    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
    
    public static HomeRegionFragment newInstance(){
        return new HomeRegionFragment();
    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_regio;
    }

    @Override
    public void finishCreateView(Bundle state) {

        loadData();
        initRecyclerView();
    }

    @Override
    protected void loadData() {
        super.loadData();
    }

    @Override
    protected void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        HomeRegionItemAdapter mAdapter = new HomeRegionItemAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        
        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                switch (position) {
                    case 0:
                        //直播
                        startActivity(new Intent(getActivity(), LiveAppIndexActivity.class));
                        break;

                 /*   case 1:
                        //番剧
                        RegionTypesInfo.DataBean mBangumi = regionTypes.get(1);
                        RegionTypeDetailsActivity.launch(getActivity(), mBangumi);
                        break;

                    case 2:
                        //动画
                        RegionTypesInfo.DataBean mAnimation = regionTypes.get(2);
                        RegionTypeDetailsActivity.launch(getActivity(), mAnimation);
                        break;

                    case 3:
                        //音乐
                        RegionTypesInfo.DataBean mMuise = regionTypes.get(3);
                        RegionTypeDetailsActivity.launch(getActivity(), mMuise);
                        break;

                    case 4:
                        //舞蹈
                        RegionTypesInfo.DataBean mDence = regionTypes.get(4);
                        RegionTypeDetailsActivity.launch(getActivity(), mDence);
                        break;

                    case 5:
                        //游戏
                        RegionTypesInfo.DataBean mGame = regionTypes.get(5);
                        RegionTypeDetailsActivity.launch(getActivity(), mGame);
                        break;

                    case 6:
                        //科技
                        RegionTypesInfo.DataBean mScience = regionTypes.get(6);
                        RegionTypeDetailsActivity.launch(getActivity(), mScience);
                        break;

                    case 7:
                        //生活
                        RegionTypesInfo.DataBean mLife = regionTypes.get(7);
                        RegionTypeDetailsActivity.launch(getActivity(), mLife);
                        break;

                    case 8:
                        //鬼畜
                        RegionTypesInfo.DataBean mKichiku = regionTypes.get(8);
                        RegionTypeDetailsActivity.launch(getActivity(), mKichiku);
                        break;

                    case 9:
                        //时尚
                        RegionTypesInfo.DataBean mFashion = regionTypes.get(9);
                        RegionTypeDetailsActivity.launch(getActivity(), mFashion);
                        break;

                    case 10:
                        //广告
                        startActivity(new Intent(getActivity(), AdvertisingActivity.class));
                        break;

                    case 11:
                        //娱乐
                        RegionTypesInfo.DataBean mRecreation = regionTypes.get(10);
                        RegionTypeDetailsActivity.launch(getActivity(), mRecreation);
                        break;

                    case 12:
                        //电影
                        RegionTypesInfo.DataBean mMovei = regionTypes.get(11);
                        RegionTypeDetailsActivity.launch(getActivity(), mMovei);
                        break;

                    case 13:
                        //电视剧
                        RegionTypesInfo.DataBean mTv = regionTypes.get(12);
                        RegionTypeDetailsActivity.launch(getActivity(), mTv);
                        break;*/

                    case 14:
                        // 游戏中心
                        startActivity(new Intent(getActivity(), GameCentreActivity.class));
                        break;

                    default:
                        break;
                }
            }
        });
    }
}
