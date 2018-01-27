package module.home.discover;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.rxjava.myblibi.R;

import base.BaseActivity;
import butterknife.BindView;

/**
 * @author 小陈
 * @time 2018/1/27  10:13
 * @desc 话题中心界面
 */
public class TopicCenterActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    public int getLayoutId() {
        return R.layout.activity_top_center;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initRefreshLayout();
        initRecyclerView();
    }

    @Override
    public void initToolBar() {

    }
}
