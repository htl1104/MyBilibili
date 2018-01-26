package module.home.discover;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.rxjava.myblibi.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import adapter.pager.HomePagerAdapter;
import base.RxLazyFragment;
import butterknife.BindView;
import butterknife.OnClick;
import module.common.MainActivity;
import module.entry.GameCentreActivity;
import module.entry.OffLineDownloadActivity;
import widget.CircleImageView;

//import android.support.v4.view.ViewPager;

/**
 * @author 小陈
 * @time 2018/1/15  11:00
 * @desc 首页模块主界面
 */
public class HomePageFragment extends RxLazyFragment {


    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTab;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.search_view)
    MaterialSearchView mSearchView;

    @BindView(R.id.toolbar_user_avatar)
    CircleImageView mCircleImageView;

    public static HomePageFragment newInstance() {

        return new HomePageFragment();
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    public void finishCreateView(Bundle state) {
        setHasOptionsMenu(true);
        initToolBar();
        initSearchView();
        initViewPager();
    }

  
    private void initToolBar() {
        mToolbar.setTitle("");
        ((MainActivity) getActivity()).setSupportActionBar(mToolbar);
        mCircleImageView.setImageResource(R.drawable.ic_hotbitmapgg_avatar);
    }
    
    private void initSearchView() {

        //初始化SearchBar
        mSearchView.setVoiceSearch(false);
        mSearchView.setCursorDrawable(R.drawable.custom_cursor);
        mSearchView.setEllipsize(true);
        mSearchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
    }

    private void initViewPager() {
        HomePagerAdapter mHomeAdapter=new HomePagerAdapter(getChildFragmentManager(),getApplicationContext());
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(mHomeAdapter);
        mSlidingTab.setViewPager(mViewPager);
        mViewPager.setCurrentItem(1);//设置默认的显示item
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main,menu);


        // 设置SearchViewItemMenu
        MenuItem item = menu.findItem(R.id.id_action_search);//搜索
        mSearchView.setMenuItem(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.id_action_game:
                //游戏中心
                startActivity(new Intent(getActivity(), GameCentreActivity.class));
                break;
            case R.id.id_action_download:
                //离线缓存
                startActivity(new Intent(getActivity(), OffLineDownloadActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 侧滑菜单开关
     */
    @OnClick(R.id.navigation_layout)
    void toggleDrawer() {

        Activity activity = getActivity();
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).toggleDrawer();
        }
    }

    public boolean isOpenSearchView() {

        return mSearchView.isSearchOpen();
    }


    public void closeSearchView() {

        mSearchView.closeSearch();
    }
}
