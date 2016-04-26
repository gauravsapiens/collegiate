package com.collegiate.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by gauravarora on 20/09/15.
 */
public class Days implements Serializable {

    private boolean[] week;

    public Days(){
        week = new boolean[7];
    }

    public boolean[] getWeek() {
        return week;
    }

    public void setWeek(boolean[] week) {
        this.week = week;
    }

    public boolean isSelected(int dayNumber){
        if(week == null){
            return false;
        }
        return week[dayNumber];
    }

    public boolean updateSelection(int dayNumber, boolean selection){
        if(week == null){
            week = new boolean[7];
        }
        week[dayNumber] = selection;
        return selection;
    }

    public List<Integer> getSelectedDays(){
        if (week == null){
            return null;
        }

        List<Integer> selectedDays = new ArrayList<>();
        int index = 0;
        for (boolean day : week){
            if(day){
                selectedDays.add(index);
            }
            index++;
        }
        return selectedDays;
    }

}