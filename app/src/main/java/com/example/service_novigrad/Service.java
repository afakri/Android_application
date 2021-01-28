package com.example.service_novigrad;
import java.util.*;
public class Service {
    private String name;
    //instance ArrayLists that store required information for a service
    private ArrayList<String> forms;
    private ArrayList<String> docs;
    public Service(){
    }
    //Service name is required to make a service
    public Service(String name){
        this.forms = new ArrayList<String>();
        this.docs = new ArrayList<String>();
        this.name = name;
        //generally used form information
        forms.add("First Name");
        forms.add("Last Name");
        forms.add("Date Of Birth");
        forms.add("Address");
        //generally used document information
        docs.add("Proof of Residence");
    }
    //returns the Array of form
    public ArrayList<String> getForms(){
        return forms;
    }
    //returns the ArrayList of the document
    public void setForms(ArrayList<String> list){
        this.forms = list;
    }
    public ArrayList<String> getDocs(){
        return docs;
    }
    //adds required information to form
    public void setDocs(ArrayList<String> list){
        this.docs = list;
    }
    public void addFormVar(String var){
        forms.add(var);
    }
    //deletes required information to form
    public void deleteFormVar(String var){
        forms.remove(var);
    }
    //adds required information to document
    public void addDocuments(String var){
        docs.add(var);
    }
    //deletes required information to document
    public void deleteDocuments(String var){
        docs.remove(var);
    }
    //returns the name of the service
    public String getName(){
        return name;
    }
    public void setName(String newName){
        this.name = newName;
    }
}
