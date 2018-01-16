package module.entry;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.rxjava.myblibi.R;

import base.RxLazyFragment;
import butterknife.BindView;
import module.common.MainActivity;
import widget.CustomEmptyView;

/**
 * @author 小陈
 * @time 2018/1/16  11:10
 * @desc 历史记录
 */
public class HistoryFragment extends RxLazyFragment {

    @BindView(R.id.empty_view)
    CustomEmptyView mCustomEmptyView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static HistoryFragment newInstance() {

        return new HistoryFragment();
    }
    
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_empty;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mToolbar.setTitle("历史记录");
        mToolbar.setNavigationIcon(R.drawable.ic_navigation_drawer);
        mToolbar.setNavigationOnClickListener(v -> {

            Activity activity1 = getActivity();
            if (activity1 instanceof MainActivity) {
                ((MainActivity) activity1).toggleDrawer();
            }
        });

        mCustomEmptyView.setEmptyImage(R.drawable.ic_movie_pay_order_error);
        mCustomEmptyView.setEmptyText("没有找到你的记录哟");
    }
}
