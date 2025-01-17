package com.infoman2.model;

public class Student {

    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String address;
    private String phone;
    private String email;
    private String gender;

    /**
     * Student Constructor
     * @param id
     * @param firstName
     * @param middleName
     * @param lastName
     * @param address
     * @param phoneNumber
     * @param email
     * @param gender
     */

    public Student(int id, String firstName, String middleName, String lastName, String address, String phoneNumber, String email, String gender){
        this.id = id;
        this.firstName =firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phoneNumber;
        this.email = email;
        this.gender= gender;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
