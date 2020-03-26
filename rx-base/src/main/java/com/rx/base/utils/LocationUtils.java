package com.rx.base.utils;

public class LocationUtils {
    private static double EARTH_RADIUS = 6378.137;

    private static Double rad(Double d) {
        return d * Math.PI / 180.0;

    }
    /**
    *@Description: 通过经纬度获取距离(单位：米)
    *@Param: 
    *@return: 
    */
    public static Double getDistance(Double lat1, Double lng1, Double lat2,	Double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s;
    }

}
