package com.example.cityweather;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private static final String BASE_URL ="https://apis.juhe.cn/simpleWeather/query?";
    private static final String KEY = "0af448c7d41f6ec2fb12059994cda518";



    static String getBookInfo(String queryString) {


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        try {

            Uri builtURI = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter("city", queryString)
                    .appendQueryParameter("key", KEY).build();


            URL requestURL = new URL(builtURI.toString());


            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            InputStream inputStream = urlConnection.getInputStream();


            reader = new BufferedReader(new InputStreamReader(inputStream));


            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {

                builder.append(line);


                builder.append("\n");
            }

            if (builder.length() == 0) {

                return null;
            }

            bookJSONString = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        Log.d(LOG_TAG, bookJSONString);

        return bookJSONString;
    }

}
