package itu.eval3.newapp.client.utils;

public class NumberUtils {
    public static int getSigne(double value){
        if (value < 0 ) {
            return -1;
        }
        return 1;
    }

    public static double abs(double value){
        int signe = getSigne(value);
        return signe * value;
    }
}
