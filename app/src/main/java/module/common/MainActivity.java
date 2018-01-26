package module.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rxjava.myblibi.R;

import base.BaseActivity;
import butterknife.BindView;
import module.entry.AttentionPeopleFragment;
import module.entry.ConsumeHistoryFragment;
import module.entry.HistoryFragment;
import module.entry.IFavoritesFragment;
import module.entry.OffLineDownloadActivity;
import module.entry.SettingFragment;
import module.entry.VipActivity;
import module.home.discover.HomePageFragment;
import utils.ConstantUtil;
import utils.PreferenceUtil;
import utils.ToastUtil;
import widget.CircleImageView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    private Fragment[] fragments;

    private int currentTabIndex;

    private int index;

    private long exitTime;

    private HomePageFragment mHomePageFragment;



    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        //初始化Fragment
        initFragments();
        //初始化侧滑菜单
        initNavigationView();
    }

    /**
     * 初始化NavigationView
     */
    private void initNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(this);
        View headerView = mNavigationView.getHeaderView(0);//获得头部控件
        CircleImageView mUserAvatarView = (CircleImageView) headerView.findViewById(
                R.id.user_avatar_view);
        TextView mUserName = (TextView) headerView.findViewById(R.id.user_name);
        TextView mUserSign = (TextView) headerView.findViewById(R.id.user_other_info);
        ImageView mSwitchMode = (ImageView) headerView.findViewById(R.id.iv_head_switch_mode);
        //设置头像
        mUserAvatarView.setImageResource(R.drawable.ic_hotbitmapgg_avatar);
        //设置用户名 签名
        mUserName.setText(getResources().getText(R.string.hotbitmapgg));
        mUserSign.setText(getResources().getText(R.string.about_user_head_layout));
        //设置日夜间模式切换
        //mSwitchMode.setOnClickListener(v -> switchNightMode());

        boolean flag = PreferenceUtil.getBoolean(ConstantUtil.SWITCH_MODE_KEY, false);
        if (flag) {
            mSwitchMode.setImageResource(R.drawable.ic_switch_daily);
        } else {
            mSwitchMode.setImageResource(R.drawable.ic_switch_night);
        }
    }

    /**
     * 初始化Fragments
     */
    private void initFragments() {

        mHomePageFragment = HomePageFragment.newInstance();
        IFavoritesFragment mFavoritesFragment = IFavoritesFragment.newInstance();
        HistoryFragment mHistoryFragment = HistoryFragment.newInstance();
        AttentionPeopleFragment mAttentionPeopleFragment = AttentionPeopleFragment.newInstance();
        ConsumeHistoryFragment mConsumeHistoryFragment = ConsumeHistoryFragment.newInstance();
        SettingFragment mSettingFragment = SettingFragment.newInstance();

        fragments = new Fragment[] {
                mHomePageFragment,
               mFavoritesFragment,
                 mHistoryFragment,
                mAttentionPeopleFragment,
                mConsumeHistoryFragment,
                mSettingFragment
        };

        // 添加显示第一个fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, mHomePageFragment)
                .show(mHomePageFragment).commit();
    }


    
    
    @Override
    public void initToolBar() {

    }

    /**
     * DrawerLayout侧滑菜单开关
     */
    public void toggleDrawer() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }
    
    
    /**
     * 监听back键处理DrawerLayout和SearchView
     */    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(mDrawerLayout.getChildAt(1))) {
                mDrawerLayout.closeDrawers();
            } else {
                if (mHomePageFragment != null) {
                    if (mHomePageFragment.isOpenSearchView()) {
                        mHomePageFragment.closeSearchView();
                    } else {
                        exitApp();
                    }
                } else {
                    exitApp();
                }
            }
        }

        return true;
    }

    /**
     * 双击退出App
     */
    private void exitApp() {

        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.ShortToast("再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            PreferenceUtil.remove(ConstantUtil.SWITCH_MODE_KEY);
            finish();
        }
    }
    
    

    /**
     * item点击事件
     * @param item
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(Gravity.START);
        switch (item.getItemId()) {
            case  R.id.item_home:
                //主页
                changeFragmentIndex(item, 0);
                return true;
            case R.id.item_download:
                // 离线缓存
                startActivity(new Intent(MainActivity.this, OffLineDownloadActivity.class));
                return true;
            case R.id.item_vip:
                //大会员
                startActivity(new Intent(MainActivity.this, VipActivity.class));
                return true;
            case R.id.item_favourite:
                //我的收藏
                changeFragmentIndex(item, 1);
                return true;

            case R.id.item_history:
                // 历史记录
                changeFragmentIndex(item, 2);
                return true;

            case R.id.item_group:
                // 关注的人
                changeFragmentIndex(item, 3);
                return true;

            case R.id.item_tracker:
                // 我的钱包
                changeFragmentIndex(item, 4);
                return true;

            case R.id.item_app:
                // 应用推荐

                return true;

            case R.id.item_theme:
                // 主题选择
                // CardPickerDialog dialog = new CardPickerDialog();
                // dialog.setClickListener(this);
                // dialog.show(getSupportFragmentManager(), CardPickerDialog.TAG);
                return true;


            case R.id.item_settings:
                // 设置中心
                changeFragmentIndex(item, 5);
                return true;





        }
        return false;
    }

    /**
     * 切换Fragment的下标
     */
    private void changeFragmentIndex(MenuItem item, int currentIndex) {
        index=currentIndex;
        switchFragment();
        item.setChecked(true);
    }
    
    /**
     * Fragment切换
     */
    private void switchFragment() {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.hide(fragments[currentTabIndex]);
        if (!fragments[index].isAdded()) {
            trx.add(R.id.container, fragments[index]);
        }
        trx.show(fragments[index]).commit();
        currentTabIndex = index; 
    }
}
