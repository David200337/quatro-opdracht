package src.db;

import java.sql.Date;
import java.sql.SQLException;

import javafx.collections.*;
import src.domain.*;
public class DatabaseCourseModule extends Database{
    private ObservableList<CourseModule> courseModuleList;
    private ObservableList<CourseModule> recommendedCourseList;
    public Object courseComboBox;
    private int studentsPassed;
    
    public DatabaseCourseModule(String connectionUrl) {
        super(connectionUrl);
        this.courseModuleList = FXCollections.observableArrayList();
        this.recommendedCourseList = FXCollections.observableArrayList();
	}
    
    //Load all the attributes in the tables Course, Module and Content
    public void loadCourseModules() {
        try {
            connect();

            String sql = "SELECT Course.CourseId, Course.CourseName, Course.Subject, Course.IntroductionText, Course.Level, Content.ContentId, Content.PublicationDate,  Content.Theme, Content.Description, Content.Status, Module.SerialNumber, Module.Title, Module.VersionNr, ContentCreator.CreatorId, ContentCreator.Name AS CreatorName, ContentCreator.Email, ContentCreator.Organisation FROM Module  INNER JOIN Content ON Module.ContentId = Content.ContentId  INNER JOIN Course ON Module.CourseId = Course.CourseId INNER JOIN ContentCreator ON Content.CreatorId = ContentCreator.CreatorId;";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            
            while(resultSet.next()){
                int contentId = resultSet.getInt("ContentId");
                int courseId = resultSet.getInt("CourseId");
                String courseName = resultSet.getString("CourseName");
                String subject = resultSet.getString("Subject");
                String introductionText = resultSet.getString("IntroductionText");
                String level = resultSet.getString("Level");
                Date publicationDate = resultSet.getDate("PublicationDate");
                String moduleTitle = resultSet.getString("Title");
                int moduleVersion = resultSet.getInt("VersionNr");
                String moduleTheme = resultSet.getString("Theme");
                String moduleDescription = resultSet.getString("Description");
                String status = resultSet.getString("Status");
                int moduleSerialNumber = resultSet.getInt("SerialNumber");
                int creatorId = resultSet.getInt("CreatorId");
                String creatorName = resultSet.getString("CreatorName");
                String creatorEmail = resultSet.getString("Email");
                String creatorOrganisation = resultSet.getString("Organisation");


                CourseModule courseModule = new CourseModule(contentId, courseId, courseName, subject, introductionText, level, publicationDate, moduleTitle, moduleTheme, moduleDescription, status, moduleVersion, moduleSerialNumber, creatorId, creatorName, creatorEmail, creatorOrganisation);
                courseModuleList.add(courseModule);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    //Load all the attributes from the table Course
    public void loadCourse(){
        try {
            connect();
            String sql = "SELECT Course.CourseId, Course.CourseName, Course.Subject, Course.IntroductionText, Course.Level FROM Course";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            
            while(resultSet.next()){
                int courseId = resultSet.getInt("CourseId");
                String courseName = resultSet.getString("CourseName");
                String subject = resultSet.getString("Subject");
                String introductionText = resultSet.getString("IntroductionText");
                String level = resultSet.getString("Level");
            
                CourseModule course = new CourseModule(courseId, courseName, subject, introductionText, level);
                courseModuleList.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    //Load all the attributes from the table Module
    public void loadModules(int courseId) {
            try {
                connect();
    
                String sql = "SELECT Content.ContentId, Content.PublicationDate, Content.Theme, Content.Description, Content.Status, Module.SerialNumber, Module.Title, Module.VersionNr, ContentCreator.CreatorId, ContentCreator.Name AS CreatorName, ContentCreator.Email, ContentCreator.Organisation FROM Module  INNER JOIN Content ON Module.ContentId = Content.ContentId  INNER JOIN ContentCreator ON Content.CreatorId = ContentCreator.CreatorId WHERE Module.CourseId = '"+courseId+"';";
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);
    
                
                while(resultSet.next()){
                    int contentId = resultSet.getInt("ContentId");
                    String moduleTitle = resultSet.getString("Title");
                    int moduleVersion = resultSet.getInt("VersionNr");
                    String moduleTheme = resultSet.getString("Theme");
                    String moduleDescription = resultSet.getString("Description");
                    String status = resultSet.getString("Status");
                    Date publicationDate = resultSet.getDate("PublicationDate");
                    int moduleSerialNumber = resultSet.getInt("SerialNumber");
                    int creatorId = resultSet.getInt("CreatorId");
                    String creatorName = resultSet.getString("CreatorName");
                    String creatorEmail = resultSet.getString("Email");
                    String creatorOrganisation = resultSet.getString("Organisation");
    
    
                    CourseModule courseModule = new CourseModule(contentId, moduleTitle, moduleVersion, moduleTheme, moduleDescription, status, publicationDate, moduleSerialNumber, creatorId, creatorName, creatorEmail, creatorOrganisation);
                    courseModuleList.add(courseModule);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } 
        
    }

    //Return the number of students that passed a selected course
    public int getCourseStudentsPassed(int courseId) throws Exception {
            connect();

            String sql = "SELECT COUNT(StudentId) AS StudentsPassed FROM Registration WHERE CourseId = "+courseId+" AND CertificateId IS NOT NULL;";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            
            while(resultSet.next()){
                studentsPassed = resultSet.getInt("StudentsPassed");
            }

            return studentsPassed;
    } 	

    //Return the list with attributes from various tables
    public ObservableList<CourseModule> getCourseModules() {
        // Return courseModule array
        return courseModuleList;
    }

    //Insert a course
    public void insertCourse(String courseName, String subject, String introductionText, String level) throws SQLException{
        statement.executeUpdate("INSERT INTO Course(CourseName, Subject, IntroductionText, Level) VALUES ('"+courseName+"','"+subject+"','"+introductionText+"','"+level+"')");
     }

    //Update a selected column from the Course, Module or Content table in a form of a String
    public void updateCourseModuleString(String column, String newValue, int id){
        try{
            if(column.equals("CourseName") || column.equals("Subject") || column.equals("Level")){
                statement.executeUpdate("UPDATE Course SET "+column+" = '"+newValue+"' WHERE CourseId = "+id);
            } else if(column.equals("Theme") || column.equals("Description") || column.equals("Status")){
                statement.executeUpdate("UPDATE Content SET "+column+" = '"+newValue+"' WHERE ContentId ="+id);
                System.out.println(newValue.toString());
            } else {
                statement.executeUpdate("UPDATE Module SET "+column+" = '"+newValue+"' WHERE ContentId = "+id);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Update a selected column from the Course, Module or Content table in a form of a Object
    public void updateCourseModuleObject(String column, Object newValue, int id){
        try{
            if(column.equals("CourseName") || column.equals("Subject") || column.equals("Level")){
                statement.executeUpdate("UPDATE Course SET "+column+" = '"+newValue+"' WHERE CourseId = "+id);
            } else if(column.equals("Theme") || column.equals("Description") || column.equals("Status")){
                statement.executeUpdate("UPDATE Content SET "+column+" = '"+newValue+"' WHERE ContentId ="+id);
            } else {
                statement.executeUpdate("UPDATE Module SET "+column+" = '"+newValue+"' WHERE ContentId = "+id);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Delete a selected course
    public void deleteCourse(CourseModule selectedItem) throws SQLException{
        statement.executeUpdate("DELETE FROM Course WHERE CourseId = '"+selectedItem.getCourseId()+"'");
    }

    //Delete a selected module
    public void deleteModule(CourseModule selectedItem) throws SQLException{
        statement.executeUpdate("DELETE FROM Module WHERE ContentId = '"+selectedItem.getContentId()+"'");
        statement.executeUpdate("DELETE FROM Content WHERE ContentId = '"+selectedItem.getContentId()+"'");
    }

    //Load all recommended courses for a selected course
    public void loadRecommendedCourses(CourseModule selectedItem){
        try {
            connect();
            String sql = "SELECT * FROM RecommendedCourse WHERE CourseId = '"+selectedItem.getCourseId()+"'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            
            while(resultSet.next()){
                int courseId = resultSet.getInt("CourseId");
                String courseName = resultSet.getString("RecommendedCourseName");
                
                CourseModule recommendedCourse = new CourseModule(courseId, courseName);
                recommendedCourseList.add(recommendedCourse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        
    }

    //Return the list of recommended courses
    public ObservableList<CourseModule> getRecommendedCourses(){
        return recommendedCourseList;
    }

    //Insert a recommendation for a certain course
    public void insertRecommendation(int courseId, String courseName) throws SQLException {
        statement.executeUpdate("INSERT INTO RecommendedCourse(CourseId, RecommendedCourseName) VALUES ('"+courseId+"','"+courseName+"')");
    }

    //Delete a recommendation
    public void deleteRecommendation(CourseModule selectedItem) throws SQLException {
        statement.executeUpdate("DELETE FROM RecommendedCourse WHERE CourseId = '"+selectedItem.getCourseId()+"'");
    }
         
    //Insert a module in the tables Module and Content
    public void insertModule(int courseId, int contentId, Date publicationDate, String title, String theme, String description, Object status, int serialNumber, String name, int moduleVersion) throws SQLException{
        statement.executeUpdate("INSERT INTO Content(ContentId, PublicationDate, Status, Theme, Description, CreatorId) VALUES ('"+contentId+"','"+publicationDate+"','"+status+"','"+theme+"','"+description+"',(SELECT CreatorId FROM ContentCreator WHERE Name = '"+name+"'))");
        statement.executeUpdate("INSERT INTO Module(ContentId, Title, VersionNr, SerialNumber, CourseId) VALUES ('"+contentId+"','"+title+"','"+moduleVersion+"','"+serialNumber+"','"+courseId+"')");
    }
}


