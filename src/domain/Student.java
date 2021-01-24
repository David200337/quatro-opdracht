package src.domain;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javafx.beans.value.ObservableValue;


public class Student {
    private int studentId;
    private String name;
    private String email;
    private Date dateOfBirth;
    private String gender;
    private String address;
    private String postalCode;
    private String city;
    private String country;
    ObservableValue<String> genderValue;
    ArrayList<Registration> registrations;

    public Student(int studentId, String name, String email, Date dateOfBirth, String gender, String address,
            String postalCode, String city, String country) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.registrations = new ArrayList<>();
    }

    public Student(){

    }

    public void putRegistrationInList(Registration registration){
        registrations.add(registration);
    }


    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int id) {
        this.studentId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfBirthAsString(String date) throws ParseException {
        this.dateOfBirth = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(date);
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
