package support.lfp.toolkit;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import support.lfp.toolkit.cache.AssetsUtils;

/**
 * <pre>
 * Tip:
 *
 * Function:
 *
 * Created by LiFuPing on 2018/11/28 17:19
 * </pre>
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtils.e(AssetsUtils.getAssetPicPath(this).toString());
    }
}
