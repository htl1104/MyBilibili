package module.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.rxjava.myblibi.R;

import base.BaseActivity;
import utils.ConstantUtil;

/**
 * @author 小陈
 * @time 2018/1/17  16:48
 * @desc 直播播放界面
 */
public class LivePlayerActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return  R.layout.activity_live_details;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }

    public static void launch(Activity activity, int cid, String title, int online, String face, String name, int mid) {

        Intent mIntent = new Intent(activity, LivePlayerActivity.class);
        mIntent.putExtra(ConstantUtil.EXTRA_CID, cid);
        mIntent.putExtra(ConstantUtil.EXTRA_TITLE, title);
        mIntent.putExtra(ConstantUtil.EXTRA_ONLINE, online);
        mIntent.putExtra(ConstantUtil.EXTRA_FACE, face);
        mIntent.putExtra(ConstantUtil.EXTRA_NAME, name);
        mIntent.putExtra(ConstantUtil.EXTRA_MID, mid);
        activity.startActivity(mIntent);
    }

}
