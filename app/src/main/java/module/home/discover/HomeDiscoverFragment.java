package module.home.discover;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rxjava.myblibi.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import base.RxLazyFragment;
import butterknife.BindView;
import entity.discover.HotSearchTag;
import network.RetrofitHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 小陈
 * @time 2018/1/16  14:51
 * @desc 首页发现页面
 */
public class HomeDiscoverFragment extends RxLazyFragment {

    @BindView(R.id.tags_layout)
    TagFlowLayout mTagFlowLayout;

    @BindView(R.id.hide_scroll_view)
    NestedScrollView mScrollView;

    @BindView(R.id.hide_tags_layout)
    TagFlowLayout mHideTagLayout;

    @BindView(R.id.more_layout)
    LinearLayout mMoreLayout;

    @BindView(R.id.tv_more)
    TextView mMoreText;

    private boolean isShowMore = true;

   private List<HotSearchTag.ListBean> hotSearchTags = new ArrayList<>();

    public static HomeDiscoverFragment newInstance(){
        return new HomeDiscoverFragment();
    }
    
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_discover;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mScrollView.setNestedScrollingEnabled(true);
        getTags();
    }

    public void getTags() {
        RetrofitHelper.getSearchAPI()
                .getHotSearchTags()
                .compose(bindToLifecycle())
                .map(HotSearchTag::getList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listBeans -> {

                    hotSearchTags.addAll(listBeans);
                    initTagLayout();
                }, throwable -> {

                });
    }
    
    private void initTagLayout(){
        //获取热搜标签集合前9个默认显示
        List<HotSearchTag.ListBean> frontTags = hotSearchTags.subList(0, 8);

        mTagFlowLayout.setAdapter(new TagAdapter<HotSearchTag.ListBean>(frontTags) {

            @Override
            public View getView(FlowLayout parent, int position, HotSearchTag.ListBean listBean) {

                TextView mTags = (TextView) LayoutInflater.from(getActivity())
                        .inflate(R.layout.layout_tags_item, parent, false);
                mTags.setText(listBean.getKeyword());
               /* mTags.setOnClickListener(
                        //全站搜索界面
                        v -> TotalStationSearchActivity.launch(getActivity(), listBean.getKeyword()));*/

                return mTags;
            }
        });
        
    }
}
