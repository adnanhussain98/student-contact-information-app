package adnanhussain.advprog.mmu.ac.uk.myapplication;

import java.io.Serializable;

/**
 * Created by Mohammed Adnan Hussain, 26/03/2018.
 */

public class Student implements Serializable {

    private String name;
    private String gender;
    private String dob;
    private String address;
    private String postcode;
    private String studentNumber;
    private String courseTitle;
    private String startDate;
    private String bursary;
    private String email;

    //all strings will be stored in student
    public Student(String name, String gender, String dob, String address, String postcode, String studentNumber,
                   String courseTitle, String startDate, String bursary, String email){

        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        this.postcode = postcode;
        this.studentNumber = studentNumber;
        this.courseTitle = courseTitle;
        this.startDate = startDate;
        this.bursary = bursary;
        this.email = email;
    }

    //getters and setters generated
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getBursary() {
        return bursary;
    }

    public void setBursary(String bursary) {
        this.bursary = bursary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
