package com.example.service_novigrad;

public class Admin extends User{
    //creates only one admin hard coded
    private String function;
    public Admin(){
        // admin hardcoded information (guidelined):
        // username: admin
        // password: 123admin456
        super("admin", "123admin456");
        function = "Admin";
    }
    // returns String function
    public String getFunction(){
        return function;
    }
    // sets function
    public void setFunction(String s){
        function = s;
    }
}
