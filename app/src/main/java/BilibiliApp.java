import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

/**
 * @author 小陈
 * @time 2018/1/12  17:45
 * @desc ${TODD}
 */
public class BilibiliApp extends Application {
    
    public static BilibiliApp mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        
        init();
    }

    private void init() {

        //初始化Leak内存泄露检测工具
        LeakCanary.install(this);
        //初始化Stetho调试工具
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }

    public static BilibiliApp getInstance() {

        return mInstance;
    }
}
