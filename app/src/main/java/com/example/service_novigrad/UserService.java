package com.example.service_novigrad;

public interface UserService {

    public User getUser(String username);
    //Search in database or memory for username
    //returns User

    public void enterUser(User user);
    //stores database or memory
    public boolean contains(String username);

    public void delete(User user);
}

