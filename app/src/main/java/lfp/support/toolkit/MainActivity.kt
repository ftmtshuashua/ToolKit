package lfp.support.toolkit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import lfp.support.toolkit.cache.AssetsUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        LogUtils.e(AssetsUtils.getAssetPicPath(this).toString())


//        ActivityStackManagerUtils.exitApp()
    }

}
