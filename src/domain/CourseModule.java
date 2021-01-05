package src.domain;

public class CourseModule {
    private Module module;
    private Course course;

    private int contentId;
    private int courseId;
    private String courseName;
    private String subject;
    private String introductionText;
    private String level;
    private String moduleTitle;
    private String moduleDescription;
    private String status;
    private int moduleSerialNumber;

    public CourseModule(int contentId, int courseId, String courseName, String subject, String introductionText,
            String level, String moduleTitle, String moduleDescription, String status, int moduleSerialNumber) {
        this.contentId = contentId;
        this.setCourseId(courseId);
        this.setCourseName(courseName);
        this.setSubject(subject);
        this.setIntroductionText(introductionText);
        this.setLevel(level);
        this.setModuleTitle(moduleTitle);
        this.setModuleDescription(moduleDescription);
        this.setStatus(status);
        this.setModuleSerialNumber(moduleSerialNumber);
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
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
