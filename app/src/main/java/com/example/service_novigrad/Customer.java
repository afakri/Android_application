package com.example.service_novigrad;


import java.util.ArrayList;
import java.util.HashMap;

public class Customer extends User{
    // instance methods
    private String Email;
    private String function;
    private HashMap<String, Float> ratedBranches;
    // constructor
    Customer(String username, String password, String email){
        super(username,password);
        Email=email;
        function = "Customer";
        this.ratedBranches = new HashMap<String, Float>();
    }
    // constcturor
    Customer(){
        this.ratedBranches = new HashMap<String, Float>();
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
    // returns average rating given branch
    public float getRating(String branch){
        return this.ratedBranches.get(branch);
    }
    // sets rating
    public void setRating(String branch, float rate){
        this.ratedBranches.put(branch, rate);
    }
    //check if contains rating branch
    public boolean containsRating(String branch){
        return ratedBranches.containsKey(branch);
    }
    // returns hashmap of rated branches
    public HashMap<String, Float> getRatedBranches(){
        return this.ratedBranches;
    }
    // sets rated branches
    public void setRatedBranches(HashMap<String, Float> map){
        this.ratedBranches = map;
    }

}
