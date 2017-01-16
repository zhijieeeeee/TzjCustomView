package com.tzj.tzjcustomview;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.Method;

/**
 * <p>
 * Description：获取插件apk中的资源
 * </p>
 *
 * @author tangzhijie
 */
public class PluginActivity extends AppCompatActivity {

    private AssetManager mAssetManager;
    private Resources mResourses;
    //插件apk路径
    private String apkPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadResources();
    }

    /**
     * 加载插件apk中的资源
     */
    private void loadResources() {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, apkPath);
            mAssetManager = assetManager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Resources superRes = super.getResources();
        mResourses = new Resources(mAssetManager, superRes.getDisplayMetrics(),
                superRes.getConfiguration());

    }

}
