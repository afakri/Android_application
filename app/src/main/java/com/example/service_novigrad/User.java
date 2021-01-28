package com.example.service_novigrad;

public class User {
    //instance variables
    private String username;
    private String password;
    private String function;
    
    // constructor
    User(String username, String password){
        this.username = username;
        this.password = password;
        function = "";
    }
    // empty constructor required
    User(){
    }
    // returns username
    public String getUsername(){
        //Returns name of user
        return this.username;
    }
    // sets ursername
    public void setUsername(String name){
        this.username = name;
    }
    // returns password
    public String getPassword(){
        return this.password;
    }
    // sets password
    public void setPassword(String newPassword){
        this.password = newPassword;
    }
    // returns function
    public String getFunction(){
        return function;
    }
    // gets function
    public void setFunction(String s){
        function=s;
    }

}
