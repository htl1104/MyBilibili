package module.home;

import android.os.Bundle;

import com.example.rxjava.myblibi.R;

import base.RxLazyFragment;

/**
 * @author 小陈
 * @time 2018/1/16  14:50
 * @desc 首页关注页面
 */
public class HomeAttentionFragment extends RxLazyFragment {

    public static HomeAttentionFragment newInstance(){
        return new HomeAttentionFragment();
    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_empty;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }
}
