package src.domain;

import java.sql.Date;

public class Registration {
    private int courseId;
    private int studentId;
    private int certificateId;
    private Date registrationDate;
    private String courseName;
    private String staffName;
    private double grade;

    public Registration(Date registrationDate, int courseId, int studentId, int certificateId, String courseName,
            String staffName, double grade) {
        this.setRegistrationDate(registrationDate);
        this.certificateId = certificateId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.setCourseName(courseName);
        this.staffName = staffName;
        this.grade = grade;

    }

    public Registration(Date registrationDate, int courseId, int studentId, int certificateId, String courseName){
        this.setRegistrationDate(registrationDate);
        this.certificateId = certificateId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.setCourseName(courseName);
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(int certificateId) {
        this.certificateId = certificateId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}
