package module.home.region;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.example.rxjava.myblibi.R;

import base.RxLazyFragment;
import butterknife.BindView;
import utils.ConstantUtil;

/**
 * @author 小陈
 * @time 2018/1/24  15:20
 * @desc 分区对应类型列表详情界面
 */
public class RegionTypeDetailsFragment extends RxLazyFragment {
 
    @BindView(R.id.tv_title)
    TextView titleTextView;



    private int rid;
    public static Fragment newInstance(int tid) {
        RegionTypeDetailsFragment fragment = new RegionTypeDetailsFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(ConstantUtil.EXTRA_RID,tid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_region_details;
    }

    @Override
    public void finishCreateView(Bundle state) {
      
    }
}
