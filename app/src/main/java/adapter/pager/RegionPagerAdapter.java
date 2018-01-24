package adapter.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import entity.region.RegionTypesInfo;
import module.home.region.RegionTypeDetailsFragment;
import module.home.region.RegionTypeRecommendFragment;
import rx.Observable;
import utils.LogDog;

/**
 * @author 小陈
 * @time 2018/1/24  14:50
 * @desc 分区界面PagerAdapter
 */
public class RegionPagerAdapter extends FragmentStatePagerAdapter{
    private int rid;

    private List<String> titles;

    private List<RegionTypesInfo.DataBean.ChildrenBean> childrens;

    private List<Fragment> fragments = new ArrayList<>();

    public RegionPagerAdapter(FragmentManager fm, int rid, List<String> titles,
                              List<RegionTypesInfo.DataBean.ChildrenBean> childrens) {
        super(fm);
        this.rid = rid;
        this.titles = titles;
        this.childrens = childrens;
        initFragments();
    }

    private void initFragments() {
        /**
         * 分区推荐页面
         */
        fragments.add(RegionTypeRecommendFragment.newInstance(rid));
        
        LogDog.i("childrens="+childrens.size());
        LogDog.i("titles="+titles.size());
        
        Observable.from(childrens)
                .subscribe(childrenBean -> {
                    //分区对应类型列表详情界面
                    fragments.add(RegionTypeDetailsFragment.
                            newInstance(childrenBean.getTid()));
                });
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
