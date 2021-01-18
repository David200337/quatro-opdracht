package src.domain;

import java.sql.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CourseModule {
    private Module module;
    private Course course;

    private int contentId;
    private int courseId;
    private String courseName;
    private String subject;
    private String introductionText;
    private String level;
    private Date publicationDate;
    private String moduleTitle;
    private String moduleTheme;
    private String moduleDescription;
    private Object status;
    private int moduleSerialNumber;
    private int moduleVersion;
    private int creatorId;
    private String creatorName;
    private String creatorEmail;
    private String creatorOrganisation;

    public CourseModule(int contentId, int courseId, String courseName, String subject, String introductionText,
            String level, Date publicationDate, String moduleTitle, String moduleTheme, String moduleDescription,
            Object status, int moduleVersion, int moduleSerialNumber, int creatorId, String creatorName,
            String creatorEmail, String creatorOrganisation) {
        this.setContentId(contentId);
        this.setCourseId(courseId);
        this.setCourseName(courseName);
        this.setSubject(subject);
        this.setIntroductionText(introductionText);
        this.setLevel(level);
        this.setModuleTitle(moduleTitle);
        this.setModuleDescription(moduleDescription);
        this.setStatus(status);
        this.setModuleSerialNumber(moduleSerialNumber);
        this.setModuleTheme(moduleTheme);
        this.setPublicationDate(publicationDate);
        this.setCreatorId(creatorId);
        this.setCreatorName(creatorName);
        this.setCreatorEmail(creatorEmail);
        this.setCreatorOrganisation(creatorOrganisation);
        this.setModuleVersion(moduleVersion);
    }

    public CourseModule(int courseId, String courseName){
        this.courseName = courseName;
        this.courseId = courseId;
    }

    public int getModuleVersion() {
        return moduleVersion;
    }

    public void setModuleVersion(int moduleVersion) {
        this.moduleVersion = moduleVersion;
    }

    public CourseModule(int courseId, String courseName, String subject, String introductionText,
            String level) {
        this.setCourseId(courseId);
        this.setCourseName(courseName);
        this.setSubject(subject);
        this.setIntroductionText(introductionText);
        this.setLevel(level);
    }

    public String getCreatorOrganisation() {
        return creatorOrganisation;
    }

    public void setCreatorOrganisation(String creatorOrganisation) {
        this.creatorOrganisation = creatorOrganisation;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    
    


    // public Course createCourse(int courseId, String courseName, String subject, String introductionText, Level level){
    //     Course course = new Course(courseId, courseName, subject, introductionText, level);
    //     return course;
    // }



    public String getModuleTheme() {
        return moduleTheme;
    }

    public void setModuleTheme(String moduleTheme) {
        this.moduleTheme = moduleTheme;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getModuleSerialNumber() {
        return moduleSerialNumber;
    }

    public void setModuleSerialNumber(int moduleSerialNumber) {
        this.moduleSerialNumber = moduleSerialNumber;
    }

    public String getStatus() {
        return String.valueOf(status);
    }

    public void setStatus(Object statusObj) {
        String.valueOf(statusObj);
        this.status = statusObj;
    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public void setModuleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIntroductionText() {
        return introductionText;
    }

    public void setIntroductionText(String introductionText) {
        this.introductionText = introductionText;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

}
