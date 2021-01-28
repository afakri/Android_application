package com.example.service_novigrad;

import java.util.ArrayList;

public class Branch {
    // instance variables
    private String name;
    private String address;
    private String employeename;
    private ArrayList<Appointment> appointments = new ArrayList<>();

    // empty constructor required
    public Branch() {
    }
    // constructor
    public Branch(String name, String address) {
        this.name = name;
        this.address = address;

    }

    // returns emplyoee name
    public String getEmployeename() {
        return employeename;
    }
    // sets employee name
    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }

    // returns name
    public String getName() {
        return name;
    }
    // sets name
    public void setName(String name) {
        this.name = name;
    }

    // returns address
    public String getAddress() {
        return address;
    }

    // returns arraylist of appopinemtns
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }
    // sets appointments
    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }
    // adds an appointment to arraylist
    public void addAppointment(Appointment appointment){

        appointments.add(appointment);
    }
    // removes appopinement from arraylist
    public void removeAppointment(Appointment appointment){
        appointments.remove(appointment);
    }
    // sets address
    public void setAddress(String address) {
        this.address = address;
    }
}
