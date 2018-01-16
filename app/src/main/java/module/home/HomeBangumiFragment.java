package module.home;

import android.os.Bundle;

import com.example.rxjava.myblibi.R;

import base.RxLazyFragment;

/**
 * @author 小陈
 * @time 2018/1/16  14:48
 * @desc 首页番剧界面
 */
public class HomeBangumiFragment extends RxLazyFragment {

    public static HomeBangumiFragment newInstance(){
        return new HomeBangumiFragment();
    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_empty;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }
}
