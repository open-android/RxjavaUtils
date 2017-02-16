# RxjavaUtil

### Rxjava快速封装实现的示例工程，包括变换的使用以及与Retrofit联用

* [配套视频](https://www.boxuegu.com/web/html/video.html?courseId=172&sectionId=8a2c9bed5a3a4c7e015a3add4703037f&chapterId=8a2c9bed5a3a4c7e015a3add6c480380&vId=8a2c9bed5a3a4c7e015a3adda8f30381&videoId=0208419D0BB20CAB9C33DC5901307461)

* 开始在build.gradle添加依赖

		 compile 'io.reactivex:rxjava:1.0.9'
		 compile 'io.reactivex:rxandroid:0.24.0'
		 compile 'com.squareup.retrofit:retrofit:1.9.0'
 
* 需要的权限

      
          <uses-permission android:name="android.permission.INTERNET" />

* 使用RxjavaUtil请求数据时需要和Retrofit连用，第一步就是使用Retrofit定义所有功能接口的API服务类，以天气接口为例。，

> 接口地址：http://api.openweathermap.org/data/2.5/weather?q=shenzhen&mode=json&APPID=6c113432fd84a6e28268af291821db16

* 参数声明，Url都通过Annotation指定，接口定义如下：

	    private interface AppApi {
	        @GET("/weather")
	        WeatherData getWeather(@Query("q") String city, @Query("mode") String mode, @Query("APPID") String APPID);
	    }

* 返回数据如下，可以使用GsonFormat自动生成javabean（即WeatherData)：

    
    
        {"coord":{"lon":114.07,"lat":22.55},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01n"}],"base":"stations","main":{"temp":293.15,"pressure":1018,"humidity":72,"temp_min":293.15,"temp_max":293.15},"visibility":10000,"wind":{"speed":3.69,"deg":116.004},"clouds":{"all":0},"dt":1483538400,"sys":{"type":1,"id":7420,"message":0.0134,"country":"CN","sunrise":1483484686,"sunset":1483523588},"id":1795565,"name":"Shenzhen","cod":200}

* 第二步是通过RestAdapter生成API接口服务的实现类(动态代理)
 
    
    
        private static final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).setLogLevel(RestAdapter.LogLevel.FULL).build();
        private static final AppApi appService = restAdapter.create(AppApi.class);
 
* 第三步是关键的一步，将返回的数据类型包装到一个数据源（Observable）中。
 onNext是RxJava发送事件，即开始请求数据，onCompleted为结束任务的事件，subscribeOn为指定获取数据的线程为耗时线程。


    
        public static Observable<WeatherData> getWeatherData(final String city) {
        return Observable.create(new Observable.OnSubscribe<WeatherData>() {
            @Override
            public void call(Subscriber<? super WeatherData> subscriber) {
                subscriber.onNext(appService.getWeather(city,"json", "6c113432fd84a6e28268af291821db16"));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

* 最后调用封装好的方法就可以了，拿到数据后，在主线程中回调（subscribeOn(Schedulers.io()），获取单个天气示例如下：

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

* 当获取多个天气时候，要使用到Rxjava的变换，简单点理解，就是一个数据源变成多个数据源，一对多的映射关系。

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
                });}
