package src.domain;

import java.util.ArrayList;

public class Course {
    private Module module;
    private ArrayList<Module> modules;
    private int courseId;
    private String courseName;
    private String subject;
    private String introductionText;
    private Level level;

    private ArrayList<Course> interestingOtherCourses;

    public Course(int courseId, String courseName, String subject, String introductionText,
            Level level) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.subject = subject;
        this.introductionText = introductionText;
        this.level = level;
        this.modules = new ArrayList<>();
        this.interestingOtherCourses = new ArrayList<>();
    }

    public void putModuleInList(Module module){
        modules.add(module);
    }
    


    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void addInterestingOtherCourse(Course course) {
        this.interestingOtherCourses.add(course);
    }

    public String getName(){
        return courseName;
    }

    public void setName(String name){
        this.courseName = name;
    }

    public String getSubject(){
        return subject;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    public String getIntroductionText(){
        return introductionText;
    }

    public void setIntroductionText(String text){
        this.introductionText = text;
    }

    public Level getLevel(){
        return level;
    }

    public void setLevel(Level level){
        this.level = level;
    }
}
