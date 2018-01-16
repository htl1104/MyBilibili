package module.home;

import android.os.Bundle;

import com.example.rxjava.myblibi.R;

import base.RxLazyFragment;

/**
 * @author 小陈
 * @time 2018/1/16  14:47
 * @desc 首页直播界面
 */
public class HomeLiveFragment extends RxLazyFragment {
    
    public static HomeLiveFragment newInstance(){
        return new HomeLiveFragment();
    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_empty;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }
}
