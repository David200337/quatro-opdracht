package src.domain;

import java.sql.Date;

public class Registration {
    private int courseId;
    private int studentId;
    private int certificateId;
    private Date registrationDate;

    public Registration(Date registrationDate, int courseId, int studentId, int certificateId) {
        this.setRegistrationDate(registrationDate);
        this.certificateId = certificateId;
        this.studentId = studentId;
        this.courseId = courseId;
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
