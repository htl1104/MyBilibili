package module.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.rxjava.myblibi.R;
import com.flyco.tablayout.SlidingTabLayout;

import base.BaseActivity;
import butterknife.BindView;
import module.home.discover.OriginalRankFragment;
import widget.NoScrollViewPager;

/**
 * @author 小陈
 * @time 2018/1/26  14:23
 * @desc 原创排行榜界面
 */
public class OriginalRankActivity extends BaseActivity {

    @BindView(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTabLayout;

    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    private String[] titles = new String[] { "原创", "全站", "番剧" };

    private String[] orders = new String[] { "origin-03.json", "all-03.json", "all-3-33.json" };

    @Override
    public int getLayoutId() {
        return R.layout.activity_orginal_rank;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        OriginalRankPagerAdapter adapter=new OriginalRankPagerAdapter(getSupportFragmentManager(),titles,orders);
        mViewPager.setAdapter(adapter);
        mSlidingTabLayout.setViewPager(mViewPager);      
    }

   

    @Override
    public void initToolBar() {
        mToolbar.setTitle("排行榜");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rank, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    
    private static class OriginalRankPagerAdapter extends FragmentStatePagerAdapter{
        private String[] titles;

        private String[] types;
        public OriginalRankPagerAdapter(FragmentManager fm,String[] titles, String[] types) {
            super(fm);
            this.titles = titles;
            this.types = types;
        }

        @Override
        public Fragment getItem(int position) {
            return OriginalRankFragment.newInstance(types[position]);
        }

        @Override
        public int getCount() {
            return types.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
    
}
