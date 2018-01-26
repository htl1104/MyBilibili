package module.home.attention;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.rxjava.myblibi.R;

import base.RxLazyFragment;
import butterknife.BindView;
import utils.SnackbarUtil;
import widget.CustomEmptyView;

/**
 * @author 小陈
 * @time 2018/1/16  14:50
 * @desc 首页关注页面
 */
public class HomeAttentionFragment extends RxLazyFragment {


    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    @BindView(R.id.empty_layout)
    CustomEmptyView mCustomEmptyView;

    public static HomeAttentionFragment newInstance(){
        return new HomeAttentionFragment();
    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_attention;
    }

    @Override
    public void finishCreateView(Bundle state) {
        initEmptyView();
    }
    public void initEmptyView() {

        mSwipeRefreshLayout.setRefreshing(false);
        mCustomEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mCustomEmptyView.setEmptyImage(R.drawable.img_tips_error_load_error);
        mCustomEmptyView.setEmptyText("加载失败~(≧▽≦)~啦啦啦.");
        SnackbarUtil.showMessage(mRecyclerView, "数据加载失败,请重新加载或者检查网络是否链接");
    }


}
