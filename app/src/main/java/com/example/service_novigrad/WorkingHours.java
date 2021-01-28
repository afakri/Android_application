package com.example.service_novigrad;
import java.util.*;
import java.lang.Math;

public class WorkingHours {
    //Start and end time has to be between 0.0 and 24.0
    //Use 24 hour clock with mins as decimals
    //If mins greater than 60 round up
    
    //instance variables
    private int starthour;
    private int endhour;
    private int startmin;
    private int endmin;
    // constructor
    WorkingHours(){
    }
    // sets start time with both hours and minutes parameter
    public void setStartTime(int startHours, int startMins){
        if(startMins >= 60){
            float mod = startMins/ 60;
            int mod2 =(int)mod;
            startHours = startHours + mod2;
            startMins = startMins - 60 * mod2;

        }
        starthour =startHours;
        startmin =startMins;
    }
    // returns string format of start time
    public String  getStartTimeTime(){
        return starthour+":"+startmin;
    }
    // returns int format of start hour
    public int getStarthour() {
        return starthour;
    }
    // sets start hour
    public void setStarthour(int starthour) {
        this.starthour = starthour;
    }
    // returns int format of start min
    public int getStartmin() {
        return startmin;
    }
    
    // sets end time with both hours and mins
    public void setEndTime(int endHours, int endMins){
        if(endMins >= 60){
            float mod = endMins/ 60;
            int mod2 =(int)mod;
            endHours = endHours + mod2;
            endMins = endMins - 60 * mod2;

        }
        endhour= endHours;
        endmin=endMins;
    }
    // returns string format of end time hours:minutes
    public String getEndTime(){
        return endhour+":"+endmin;
    }
    // returns int end hour
    public int getEndhour() {
        return endhour;
    }
    // sets end hour
    public void setEndhour(int endhour) {
        this.endhour = endhour;
    }
    // sets start min
    public void setStartmin(int startmin) {
        this.startmin = startmin;
    }
    // returns int end min
    public int getEndmin() {
        return endmin;
    }
    // sets end min
    public void setEndmin(int endmin) {
        this.endmin = endmin;
    }

}
