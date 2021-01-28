package com.example.service_novigrad;

public class Appointment {
    private String start,end,day;
    private Boolean taken;

    public Appointment() {
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Appointment(String start, String end, String day) {
        this.start = start;
        this.end = end;
        this.day = day;
        taken=false;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public Boolean getTaken() {
        return taken;
    }

    public void setTaken(Boolean taken) {
        this.taken = taken;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return getStart()+"|"+getEnd();
    }
}
