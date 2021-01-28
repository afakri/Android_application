package com.example.service_novigrad;

import com.google.firebase.database.DatabaseReference;

import org.junit.Test;
import static org.junit.Assert.*;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UnitTest {
    @Test
    public void WorkingHours_set() {
        BranchEmployee TestBREmployee = new BranchEmployee("","","","","");
        assertEquals("The working hours have not been set correctly",7, TestBREmployee.getWorkingHours().size());
    }
    @Test
    public void Services_associated() {
        BranchEmployee TestBREmployee = new BranchEmployee("","","","","");
        assertEquals(" services were associated to the employee's branch",1, TestBREmployee.getServices().size());
    }
    @Test
    public void Docs_added() {
        Service srv = new Service("Test");
        assertNotEquals("no docs were added",null,srv.getDocs().size());
    }
    @Test
    public void Forms_added() {
        Service srv = new Service("Test");
        assertNotEquals("no docs were added",null,srv.getForms().size());
    }
    @Test
    public void Name_set() {
        Service srv = new Service("Test");
        assertEquals("name has not been set correctly","Test",srv.getName());
        srv.setName("new Test");
        assertEquals("name has not been set correctly","new Test",srv.getName());
    }
    @Test
    public void User_set() {
        User testUser = new User("user","password");
        assertEquals("username has not been gotten","user", testUser.getUsername());
        assertEquals("password has not been gotten","password", testUser.getPassword());
    }
    @Test
    public void WorkingHours_validtime() {
        WorkingHours wh = new WorkingHours();
        wh.setStartTime(5, 100);
        assertEquals("The time is not valid",6, wh.getStarthour());
        assertEquals("The time is not valid",40, wh.getStartmin());
    }
    @Test
    public void Appointment_add() {
        ArrayList<Appointment> array = new ArrayList<Appointment>();
        Appointment rendez1 = new Appointment();
        Appointment rendez2 = new Appointment();
        array.add(rendez1);
        array.add(rendez2);
        Branch br = new Branch();
        br.addAppointment(rendez1);
        br.addAppointment(rendez2);
        assertEquals("This appointment is not valid",array, br.getAppointments());
    }
    @Test
    public void Appointment_delete() {
        ArrayList<Appointment> array = new ArrayList<>();
        Appointment rendez1 = new Appointment();
        array.add(rendez1);
        Branch br = new Branch();
        br.addAppointment(rendez1);
        br.removeAppointment(rendez1);
        assertEquals("[]",br.getAppointments().toString());
    }
    @Test
    public void Appointment_set() {
        ArrayList<Appointment> array = new ArrayList<>();
        Appointment rendez1 = new Appointment();
        Appointment rendez2 = new Appointment();
        array.add(rendez1);
        array.add(rendez2);
        Branch br = new Branch();
        br.setAppointments(array);
        assertEquals("Appointmnet has't been set",br.getAppointments(),array);
    }
    @Test
    public void ServiceRequest_init() {

        Service sv = new Service("one");
        BranchEmployee testBREmployee = new BranchEmployee("two","","","","");
        Customer testCustomer = new Customer("three","","");
        ServiceRequest sr = new ServiceRequest(testBREmployee,sv,testCustomer);
        assertEquals("There has been error while requesting this service","two,one,three",sr.getName());
    }
    @Test
    public void Service_requested() {

        BranchEmployee testBREmployee = new BranchEmployee("","","","TestBranch","");
        Customer testCustomer = new Customer();
        testCustomer.setRating("TestBranch",3);
        assertEquals("Rating has not been set correctly",3,(int)testCustomer.getRating(testBREmployee.getBranchname()));
    }}
