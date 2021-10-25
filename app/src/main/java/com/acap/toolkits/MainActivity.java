package com.acap.toolkits;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.acap.toolkit.log.LogUtils;
import com.acap.toolkit.thread.ThreadHelper;


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

        LogUtils.i("MainActivity创建了");
        DM.test();

        findViewById(R.id.view_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.i("点击了按钮..");
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
