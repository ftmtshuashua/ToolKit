package support.lfp.toolkit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import support.lfp.toolkit.cache.AssetsUtils
import support.lfp.toolkit.demo.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        LogUtils.e(AssetsUtils.getAssetPicPath(this).toString())


//        ActivityStackManagerUtils.exitApp()
    }

}
