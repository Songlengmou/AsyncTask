package com.example.syp.loaddate;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * AsyncTask用法2  连接网络
 */
public class MainActivity extends AppCompatActivity {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDate("http://baidu.com");
    }

    private void loadDate(String url) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {//在字线程中执行，不再主线程了
                try {
                    //实体流InputStream  装饰流BufferedReader
                    InputStream in = new URL(params[0]).openStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String line = null;
                    /**
                     *  为什么用StringBuilder,不用字符串？
                     *  不用频繁的分配空间,比较省内存
                     */
                    StringBuilder content = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        content.append(line);
                    }
                    in.close();  //装饰流在实体流里，真正执行的只有一个流

                    return content.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {//执行这个方法就又返回到主线程了
                super.onPostExecute(s);
                if (s != null) {
                    tv.setText(s);
                } else {
                    tv.setText("加载数据失败");
                }
            }
        }.execute(url);
    }
}
