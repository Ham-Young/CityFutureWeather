package com.example.cityweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private JSONArray itemsArray;


    public WeatherAdapter(JSONArray itemsArray) {

        this.itemsArray = itemsArray;

    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //通过LayoutInflater加载器将XML布局文件实例化为相应的视图对象
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        return new WeatherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        //从数据源中获取指定位置的数据

        try {
            JSONObject weatherInfo = itemsArray.getJSONObject(position);

            //使用viewholder对象将数据显示到子项视图的各个组件上
            holder.mDateTextView.setText(weatherInfo.getString("date"));

            holder.mTemperatureTextView.setText(weatherInfo.getString("temperature"));

            holder.mWeatherTextView.setText(weatherInfo.getString("weather"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public int getItemCount() {

        return itemsArray.length();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView mDateTextView;
        TextView mTemperatureTextView;
        TextView mWeatherTextView;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            mDateTextView = itemView.findViewById(R.id.dateText);
            mTemperatureTextView = itemView.findViewById(R.id.temperatureText);
            mWeatherTextView = itemView.findViewById(R.id.weatherText);

        }



    }

}
