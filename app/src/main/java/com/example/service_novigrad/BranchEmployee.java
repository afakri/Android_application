package com.example.service_novigrad;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BranchEmployee extends User{
    // instance variables
    private String Email;
    private String function;
    private String branchname;
    private String adresse;
    private ArrayList<WorkingHours> workingHours;
    private ArrayList<Service> services = new ArrayList<>();
    private float rating;
    private int numRatings;
    private ArrayList<Appointment>appointments;
    
    // returns appoinetsments
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }
    // sets appointment
    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }
    // constructor
    BranchEmployee(String username, String password, String email, String branch_name, String adress){
        super(username, password);
        Email = email;
        function = "Employee";
        branchname=branch_name;
        adresse=adress;
        this.services=new ArrayList<>();
        this.workingHours=new ArrayList<>();
        WorkingHours wh= new WorkingHours();
        wh.setStartTime(0,0);
        wh.setEndTime(0,0);
        for(int i=0;i<7;i++){
            workingHours.add(i,wh);
        }
        Service service = new Service("empty");
        services.add(service);
        this.rating = 0;
        this.numRatings = 0;
    }   
    // empty constructor required
    BranchEmployee(){

    }
    // returns boolean true if branch name exists
    public boolean hasBranchname(){
        return this.branchname != null;
    }
    // sets branch name
    public void setBranchname(String branch){
        branchname = branch;
    }
    // returns branchname
    public String getBranchname(){
        return branchname;
    }
    // returns function
    public String getFunction(){
        return function;
    }
    // returns email
    public String getEmail(){
        return Email;
    }
    // sets function
    public void setFunction(String s){
        function=s;
    }
    // sets address
    public void setAdresse(String s){
        adresse=s;
    }
    // sets email
    public void setEmail(String email) {
        Email = email;
    }
    // returns address
    public String getAdresse(){
        return adresse;
    }
    // returns arraylist of working hours
    public ArrayList<WorkingHours> getWorkingHours() {
        return workingHours;
    }
    // sets working hours
    public void setWorkingHours(ArrayList<WorkingHours> workingHours) {
        this.workingHours = workingHours;
    }
    // returns arraylist of services
    public ArrayList<Service> getServices() {
        return services;
    }
    // sets services
    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }
    // adds service to arralyist
    public void addService(Service service){
        this.services.add(service);
    }
    // returns true if parameter contains service
    public boolean containsService(Service service){
        return this.services.contains(service);
    }
    // returns rating
    public float getRating(){
        return rating;
    }
    // sets rathing
    public void setRathing(float rate){
        this.rating = rate;
    }
    // returns number of ratings
    public int getNumRatings() {
        return numRatings;
    }
    // sets rating
    public void setRating(float ratingNew){
        this.rating = (this.rating + ratingNew)/(this.numRatings + 1);
        this.numRatings ++;
    }
    // sets rating given new and old
    public void setRating(float oldRating, float newRating){
        this.rating = ((this.rating * numRatings) - oldRating + newRating)/this.numRatings;
    }
}


