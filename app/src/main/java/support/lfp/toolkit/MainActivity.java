package support.lfp.toolkit;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.acap.toolkit.log.LogUtils;
import com.acap.toolkit.thread.ThreadHelper;

import org.jetbrains.annotations.Nullable;

import support.lfp.toolkit.demo.R;

/**
 * <pre>
 * Tip:
 *
 * Function:
 *
 * Created by ACap on 2018/11/28 17:19
 * </pre>
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.view_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreadHelper.io(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.i("抛出异常");
                        if (((int) (Math.random() * 100)) % 2 == 0) throw new NullPointerException("这个异常会被全局拦截器拦截! - 来自于子线程");
                        LogUtils.i("线程结束");
                    }
                });

                throw new NullPointerException("这个异常会被全局拦截器拦截!");
            }
        });

    }
}
