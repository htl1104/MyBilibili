package module.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.rxjava.myblibi.R;
import com.trello.rxlifecycle.components.RxActivity;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import utils.ConstantUtil;
import utils.PreferenceUtil;

/**
 * @author 小陈
 * @time 2018/1/12  17:27
 * @desc 启动页面
 */
public class SplashActivity extends RxActivity {
    private Unbinder bind;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bind = ButterKnife.bind(this);
        //LogDog.i("加载SplashActivity页面");
        setUpSplash();
    }

    private void setUpSplash() {

        Observable.timer(200, TimeUnit.MILLISECONDS)
                .compose(this.<Long>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        isLogin();
                    }
                });
        
    }

    /**
     * 判断是否登录过
     */
    private void isLogin(){
        boolean isLogin = PreferenceUtil.getBoolean(ConstantUtil.KEY, false);
        if(isLogin) {
            //已登录
            startActivity( new Intent(SplashActivity.this, MainActivity.class));
            
        }else {
            //未登录
            startActivity( new Intent(SplashActivity.this, LoginActivity.class));
        }
        
        SplashActivity.this.finish();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        bind.unbind();
    }
}
