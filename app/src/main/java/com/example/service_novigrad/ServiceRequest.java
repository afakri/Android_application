package com.example.service_novigrad;
//change it to hashmap
import java.util.ArrayList;
import java.util.HashMap;

public class ServiceRequest {
    private HashMap<String, String> filledforms;
    private HashMap<String, Upload> filleddocs;
    private String status;
    private Service servicefilled;
    private Customer customer;
    private BranchEmployee employee;
    private String name;

    public ServiceRequest(BranchEmployee employee, Service service, Customer customer){
        this.name = employee.getUsername()+" ,"+ service.getName()+" ,"+customer.getUsername();
        this.filledforms = new HashMap<String, String>();
        this.filleddocs = new HashMap<String, Upload>();
        this.employee = employee;
        this.servicefilled = service;
        this.customer = customer;
        this.status = "in progress";

        filledforms.put("init"," ");
        filleddocs.put("init", new Upload(" ", " "));
    }
    public ServiceRequest(){
    }
    public HashMap<String,String> getFilledforms(){
        return filledforms;
    }
    public void setFilledforms(HashMap<String,String> map){
        this.filledforms = map;
    }
    public HashMap<String,Upload> getFilleddocs(){
        return filleddocs;
    }
    public void setFilleddocs(HashMap<String,Upload> map){
        this.filleddocs = map;
    }
    public void setForm(String key, String value){
        if(servicefilled.getForms().contains(key)){
            filledforms.put(key, value);
        }
    }
    public void deleteForm(String key){
        if(filledforms.containsKey(key)){
            filledforms.remove(key);
        }
    }
    public void setDocuments(String key, Upload value){
        if(servicefilled.getDocs().contains(key)){
            filleddocs.put(key, value);
        }
    }
    public void deleteDocuments(String key, Upload value){
        if(filleddocs.containsKey(key)){
            filleddocs.remove(key);
        }
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public Customer getCustomer(){return customer;}

    public BranchEmployee getEmployee(){return employee;}
    //returns null if doc isn't present
    public Upload getDoc(String doc){
        return filleddocs.get(doc);
    }
    //returns null if form isn't present
    public String getForm(String form){
        return filledforms.get(form);
    }
    public boolean containsForm(String form){
        return filledforms.containsKey(form);
    }
    public boolean containsDoc(String doc){
        return filleddocs.containsValue(doc);
    }
    public String getStatus(){
        return this.status;
    }
    public void setStatus(String statusNew){
        this.status = statusNew;
    }
    public Service getServicefilled(){
        return this.servicefilled;
    }
    public void setServicefilled(Service service){
        this.servicefilled = service;
    }

}

