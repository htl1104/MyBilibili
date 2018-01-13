package utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

/**
 * @author 小陈
 * @time 2018/1/12  17:45
 * @desc ${TODD}
 */
public class BilibiliApp extends Application {
    
    public static BilibiliApp mInstance;
    public static boolean DEBUG = true;


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
        DEBUG = isApkDebugable(this);
        LogDog.setDebug(DEBUG);
    }

    public static BilibiliApp getInstance() {

        return mInstance;
    }

    public static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info= context.getApplicationInfo();
            return (info.flags&ApplicationInfo.FLAG_DEBUGGABLE)!=0;
        } catch (Exception e) {

        }
        return false;
    }
}
