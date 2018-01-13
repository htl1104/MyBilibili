package module.common;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.example.rxjava.myblibi.R;
import com.trello.rxlifecycle.components.RxActivity;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @author 小陈
 * @time 2018/1/12  17:27
 * @desc 启动页面
 */
public class SplashActivity extends RxActivity {
    private Unbinder bind;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_splash);
        bind = ButterKnife.bind(this);

        setUpSplash();
    }

    private void setUpSplash() {

        Observable.timer(2, TimeUnit.SECONDS)
                .compose(this.<Long>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        
                    }
                });
        
    }

    /**
     * 判断是否登陆过
     */
    private void isLogin(){
        
    }
}
