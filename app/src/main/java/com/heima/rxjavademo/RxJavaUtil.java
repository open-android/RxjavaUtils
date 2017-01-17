package com.heima.rxjavademo;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;


public class RxJavaUtil {

    private static final String ENDPOINT = "http://api.openweathermap.org/data/2.5";
    //http://api.openweathermap.org/data/2.5/weather?q=shenzhen&mode=json&APPID=6c113432fd84a6e28268af291821db16
    private interface AppApi {
        @GET("/weather")
        WeatherData getWeather(@Query("q") String city, @Query("mode") String mode, @Query("APPID") String APPID);
    }

    private static final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).setLogLevel(RestAdapter.LogLevel.FULL).build();
    private static final AppApi appService = restAdapter.create(AppApi.class);

    /**
     * 将服务接口返回的数据，封装成{@link rx.Observable}
     * @param city
     * @return
     */
    public static Observable<WeatherData> getWeatherData(final String city) {
        return Observable.create(new Observable.OnSubscribe<WeatherData>() {
            @Override
            public void call(Subscriber<? super WeatherData> subscriber) {
                subscriber.onNext(appService.getWeather(city,"json", "6c113432fd84a6e28268af291821db16"));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

}
