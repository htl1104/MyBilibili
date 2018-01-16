package module.common;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.example.rxjava.myblibi.R;

import base.BaseActivity;
import butterknife.BindView;
import widget.CustomEmptyView;

/**
 * @author 小陈
 * @time 2018/1/16  13:59
 * @desc 关于我
 */
public class HotBitmapGGInfoActivity extends BaseActivity {

    @BindView(R.id.empty_view)
    CustomEmptyView mCustomEmptyView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_empty;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
      

        mCustomEmptyView.setEmptyImage(R.drawable.ic_movie_pay_order_error);
        mCustomEmptyView.setEmptyText("关于我");
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("关于");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
