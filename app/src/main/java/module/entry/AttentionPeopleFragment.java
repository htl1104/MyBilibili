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
 * @desc 关注的人
 */
public class AttentionPeopleFragment extends RxLazyFragment {

    @BindView(R.id.empty_view)
    CustomEmptyView mCustomEmptyView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static AttentionPeopleFragment newInstance() {

        return new AttentionPeopleFragment();
    }
    
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_empty;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mToolbar.setTitle("关注的人");
        mToolbar.setNavigationIcon(R.drawable.ic_navigation_drawer);
        mToolbar.setNavigationOnClickListener(v -> {

            Activity activity1 = getActivity();
            if (activity1 instanceof MainActivity) {
                ((MainActivity) activity1).toggleDrawer();
            }
        });

        mCustomEmptyView.setEmptyImage(R.drawable.img_tips_error_no_following_person);
        mCustomEmptyView.setEmptyText("你还没有关注的人哟");
    }
}
