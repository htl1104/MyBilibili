package module.home.discover;

import android.os.Bundle;

import com.example.rxjava.myblibi.R;

import base.RxLazyFragment;

/**
 * @author 小陈
 * @time 2018/1/16  14:51
 * @desc 首页发现页面
 */
public class HomeDiscoverFragment extends RxLazyFragment {

    public static HomeDiscoverFragment newInstance(){
        return new HomeDiscoverFragment();
    }
    
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_empty;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }
}
