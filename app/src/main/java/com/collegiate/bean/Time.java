package com.collegiate.bean;

import java.io.Serializable;

/**
 * Created by gauravarora on 20/09/15.
 */
public class Time implements Serializable{

    private int hourOfDay;
    private int minute;
    private int second;

    public Time(){
    }

    public Time(int hourOfDay, int minute, int second) {
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        this.second = second;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}
