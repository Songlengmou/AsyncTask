package com.example.syp.contennumber1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * AsyncTask用法
 * 三种泛型类型分别代表“启动任务执行的输入参数”、“后台任务执行的进度”、“后台计算结果的类型”。在特定场合下，并不是所有类型都被使用，如果没有被使用，可以用Java.lang.Void类型代替。
 * <p>
 * 一个异步任务的执行一般包括以下几个步骤：
 * <p>
 * 1.execute(Params... params)，执行一个异步任务，需要我们在代码中调用此方法，触发异步任务的执行。
 * <p>
 * 2.onPreExecute()，在execute(Params... params)被调用后立即执行，一般用来在执行后台任务前对UI做一些标记。
 * <p>
 * 3.doInBackground(Params... params)，在onPreExecute()完成后立即执行，用于执行较为费时的操作，此方法将接收输入参数和返回计算结果。在执行过程中可以调用publishProgress(Progress... values)来更新进度信息。
 * <p>
 * 4.onProgressUpdate(Progress... values)，在调用publishProgress(Progress... values)时，此方法被执行，直接将进度信息更新到UI组件上。
 * <p>
 * 5.onPostExecute(Result result)，当后台操作结束时，此方法将会被调用，计算结果将做为参数传递到此方法中，直接将结果显示到UI组件上。
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

        //Void通过外界向里面传的;Integer向外界传出的参数(可以是任意类型,这里是int)
        new AsyncTask<Void, Integer, Void>() {

            @Override
            protected Void doInBackground(Void... params) {//接收输入参数和返回计算结果
                int count = 0;
                while (count < 10) {
                    count++;

                    //向外界传出一个数字
                    publishProgress(count); //与下方onProgressUpdate()方法相对应
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                tv.setText(values[0].toString());
            }
        }.execute(); //执行异步处理
    }
}
