package com.example.cityweather;

import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class FetchWeather extends AsyncTask<String, Void, JSONArray> {

    private RecyclerView weatherRecyclerView;
    private WeatherAdapter weatherAdapter;

    private TextView reasonTextView;
    private String reason;

    private JSONArray itemsArray;


    FetchWeather(RecyclerView RecyclerView, TextView reasonTextView) {
        this.weatherRecyclerView = RecyclerView;
        this.reasonTextView = reasonTextView;

    }


    @Override
    protected JSONArray doInBackground(@NonNull String... strings) {

              //获取jason字符串
        String jasonString = NetworkUtils.getBookInfo(strings[0]);

        return getJasonArray(jasonString);


    }

    @Override
    protected void onPostExecute(JSONArray itemArray) {

        super.onPostExecute(itemArray);


        if (itemArray==null){

            reasonTextView.setText(reason);
            weatherAdapter= new WeatherAdapter(itemArray);

            weatherAdapter.notifyDataSetChanged();

        }

       else if (itemArray.length() > 0) {

            reasonTextView.setText(reason);

            weatherAdapter = new WeatherAdapter(itemArray);

            weatherRecyclerView.setLayoutManager(new LinearLayoutManager(weatherRecyclerView.getContext()));
            weatherRecyclerView.setAdapter(weatherAdapter);

        }


    }

    //解析jasonString，获取jason中的future数组
    protected JSONArray getJasonArray(String s) {

        try {
            //将字符串 s 转换为一个 JSON 对象 jsonObject
            JSONObject jsonObject = new JSONObject(s);
            //获取查询结果提示信息
            reason = jsonObject.getString("reason");

            if((reason != null && reason.equals("查询成功!"))||(reason != null && reason.equals("查询成功"))) {

                JSONObject cityWeatherInfo = jsonObject.getJSONObject("result");

                //从 cityWeatherInfo 对象中获取名为 "future" 的数组
                itemsArray = cityWeatherInfo.getJSONArray("future");
            } else{

                itemsArray=null;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return itemsArray;

    }



}
