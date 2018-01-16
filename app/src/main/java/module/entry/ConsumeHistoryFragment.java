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
 * @desc 我的钱包
 */
public class ConsumeHistoryFragment extends RxLazyFragment {

    @BindView(R.id.empty_view)
    CustomEmptyView mCustomEmptyView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static ConsumeHistoryFragment newInstance() {

        return new ConsumeHistoryFragment();
    }
    
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_empty;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mToolbar.setTitle("我的钱包");
        mToolbar.setNavigationIcon(R.drawable.ic_navigation_drawer);
        mToolbar.setNavigationOnClickListener(v -> {

            Activity activity1 = getActivity();
            if (activity1 instanceof MainActivity) {
                ((MainActivity) activity1).toggleDrawer();
            }
        });

        mCustomEmptyView.setEmptyImage(R.drawable.ic_movie_pay_area_limit);
        mCustomEmptyView.setEmptyText("你还没有消费记录哟");
    }
}
