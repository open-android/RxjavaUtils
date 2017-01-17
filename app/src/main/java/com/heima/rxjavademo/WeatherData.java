package com.heima.rxjavademo;

import java.util.List;

/**
 * 天气数据javabean
 */

public class WeatherData {

    /**
     * coord : {"lon":114.07,"lat":22.55}
     * weather : [{"id":800,"main":"Clear","description":"clear sky","icon":"01n"}]
     * base : stations
     * main : {"temp":293.15,"pressure":1018,"humidity":72,"temp_min":293.15,"temp_max":293.15}
     * visibility : 10000
     * wind : {"speed":3.69,"deg":116.004}
     * clouds : {"all":0}
     * dt : 1483538400
     * sys : {"type":1,"id":7420,"message":0.0134,"country":"CN","sunrise":1483484686,"sunset":1483523588}
     * id : 1795565
     * name : Shenzhen
     * cod : 200
     */

    public CoordBean coord;
    public String base;
    public MainBean main;
    public int visibility;
    public WindBean wind;
    public CloudsBean clouds;
    public int dt;
    public SysBean sys;
    public int id;
    public String name;
    public int cod;
    public List<WeatherBean> weather;

    public static class CoordBean {
        /**
         * lon : 114.07
         * lat : 22.55
         */

        public double lon;
        public double lat;

        @Override
        public String toString() {
            return "CoordBean{" +
                    "lon=" + lon +
                    ", lat=" + lat +
                    '}';
        }
    }

    public static class MainBean {
        /**
         * temp : 293.15
         * pressure : 1018
         * humidity : 72
         * temp_min : 293.15
         * temp_max : 293.15
         */

        public double temp;
        public int pressure;
        public int humidity;
        public double temp_min;
        public double temp_max;

        @Override
        public String toString() {
            return "MainBean{" +
                    "temp=" + temp +
                    ", pressure=" + pressure +
                    ", humidity=" + humidity +
                    ", temp_min=" + temp_min +
                    ", temp_max=" + temp_max +
                    '}';
        }
    }

    public static class WindBean {
        /**
         * speed : 3.69
         * deg : 116.004
         */

        public double speed;
        public double deg;

        @Override
        public String toString() {
            return "WindBean{" +
                    "speed=" + speed +
                    ", deg=" + deg +
                    '}';
        }
    }

    public static class CloudsBean {
        /**
         * all : 0
         */

        public int all;

        @Override
        public String toString() {
            return "CloudsBean{" +
                    "all=" + all +
                    '}';
        }
    }

    public static class SysBean {
        /**
         * type : 1
         * id : 7420
         * message : 0.0134
         * country : CN
         * sunrise : 1483484686
         * sunset : 1483523588
         */

        public int type;
        public int id;
        public double message;
        public String country;
        public int sunrise;
        public int sunset;

        @Override
        public String toString() {
            return "SysBean{" +
                    "type=" + type +
                    ", id=" + id +
                    ", message=" + message +
                    ", country='" + country + '\'' +
                    ", sunrise=" + sunrise +
                    ", sunset=" + sunset +
                    '}';
        }
    }

    public static class WeatherBean {
        /**
         * id : 800
         * main : Clear
         * description : clear sky
         * icon : 01n
         */

        public int id;
        public String main;
        public String description;
        public String icon;

        @Override
        public String toString() {
            return "WeatherBean{" +
                    "id=" + id +
                    ", main='" + main + '\'' +
                    ", description='" + description + '\'' +
                    ", icon='" + icon + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "coord=" + coord +
                ", base='" + base + '\'' +
                ", main=" + main +
                ", visibility=" + visibility +
                ", wind=" + wind +
                ", clouds=" + clouds +
                ", dt=" + dt +
                ", sys=" + sys +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", cod=" + cod +
                ", weather=" + weather +
                '}';
    }
}
