package com.heima.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "MainActivity";
    private String[] cities = new String[]{"beijing", "shenzhen", "shanghai"};
    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;
    private Button mMBtn;
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTv1 = (TextView) findViewById(R.id.text);
        mTv2 = (TextView) findViewById(R.id.text2);
        mTv3 = (TextView) findViewById(R.id.text3);
        mTv1.setOnClickListener(this);
        mTv2.setOnClickListener(this);
        mTv3.setOnClickListener(this);

        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text:
                getCityWeather(cities[0]);
                break;
            case R.id.text2:
                getCityWeather(cities[1]);
                break;
            case R.id.text3:
                getCityWeather(cities[2]);
                break;
            case R.id.btn:
                getAllWeather();
                break;
        }
    }

    private void getAllWeather() {
        /**
         * 多个 city 请求
         * map，flatMap 对 Observable进行变换
         */
        Observable.from(cities).flatMap(new Func1<String, Observable<WeatherData>>() {
            @Override
            public Observable<WeatherData> call(String city) {
                return RxJavaUtil.getWeatherData(city);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(/*onNext*/new Action1<WeatherData>() {
                    @Override
                    public void call(WeatherData weatherData) {
                        String  cityName = weatherData.name.toLowerCase();
                        switch (cityName){
                            case "beijing":
                                mTv1.setText(weatherData.toString());
                                break;
                            case "shenzhen":
                                mTv2.setText(weatherData.toString());
                                break;
                            case "shanghai":
                                mTv3.setText(weatherData.toString());
                                break;
                        }
                    }
                }, /*onError*/new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    //        /**
    //         * 单个 city 请求
    //         */
    public void getCityWeather(final String cityName){
        RxJavaUtil.getWeatherData(cityName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WeatherData>() {
                    @Override
                    public void call(WeatherData weatherData) {
                        Log.d(LOG_TAG, weatherData.toString());
                        switch (cityName){
                            case "beijing":
                                mTv1.setText(weatherData.toString());
                                break;
                            case "shenzhen":
                                mTv2.setText(weatherData.toString());
                                break;
                            case "shanghai":
                                mTv3.setText(weatherData.toString());
                                break;
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(LOG_TAG, throwable.getMessage(), throwable);
                    }
                });
    }


}
