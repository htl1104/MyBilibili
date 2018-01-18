package entity.video;

import android.os.Bundle;

import com.example.rxjava.myblibi.R;

import base.RxLazyFragment;
import utils.ConstantUtil;

/**
 * @author 小陈
 * @time 2018/1/18  17:13
 * @desc 视频简介界面
 */
public class VideoIntroductionFragment extends RxLazyFragment {
    private  int av;
    
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_video_introduction;
    }

    @Override
    public void finishCreateView(Bundle state) {
        av = getArguments().getInt(ConstantUtil.EXTRA_AV);
    }
    
    public static VideoIntroductionFragment newInstance(int aid){
        VideoIntroductionFragment fragment = new VideoIntroductionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtil.EXTRA_AV, aid);
        fragment.setArguments(bundle);
        return fragment;
    }
}
