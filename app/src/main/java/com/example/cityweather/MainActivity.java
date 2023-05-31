package com.example.cityweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private EditText mCityInput;

    private  TextView mReasonText;

    private RecyclerView weatherRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCityInput = findViewById(R.id.cityInput);
        // 初始化RecyclerView
        weatherRecyclerView = findViewById(R.id.weatherRecyclerView);
        mReasonText=findViewById(R.id.reason);

    }


    public void searchWeather(View view) {

        //获取输入框中的查询字符串
        String queryString = mCityInput.getText().toString();

        //通过调用 getSystemService(Context.INPUT_METHOD_SERVICE) 获取输入法管理器 InputMethodManager 的实例，
        // 并将其用于隐藏软键盘。这样做是为了在查询开始之前隐藏软键盘，提供更好的用户体验。
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        // 获取连接管理器 ConnectivityManager 的实例
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            // 获取当前活动的网络信息对象 NetworkInfo
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {

            //获取FetchBook 的实例，调用execute(queryString) 方法启动了异步任务（执行网络请求并获取书籍信息）
            new FetchWeather(weatherRecyclerView,mReasonText).execute(queryString);
            //在请求发送过程中通过设置文本内容为加载中的提示，以提醒用户正在进行网络请求。

            mReasonText.setText(R.string.loading);

        }

        else {
            if (queryString.length() == 0) {

                mReasonText.setText(R.string.no_search_term);
            } else {

                mReasonText.setText(R.string.no_network);
            }
        }


    }


}