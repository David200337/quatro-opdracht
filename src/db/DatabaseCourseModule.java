package src.db;

import java.sql.Date;
import java.sql.SQLException;

import javafx.collections.*;
import src.domain.*;
public class DatabaseCourseModule extends Database{
    private ObservableList<CourseModule> courseModuleList;
    
    public DatabaseCourseModule(String connectionUrl) {
        super(connectionUrl);
        this.courseModuleList = FXCollections.observableArrayList();
	}
    
    public void loadCourseModules() {
        try {
            connect();

            String sql = "SELECT Course.CourseId, Course.CourseName, Course.Subject, Course.IntroductionText, Course.Level, Content.ContentId, Content.PublicationDate,  Content.Theme, Content.Title, Content.Description, Content.Status, Module.SerialNumber, ContentCreator.CreatorId, ContentCreator.Name AS CreatorName, ContentCreator.Email, ContentCreator.Organisation FROM Module  INNER JOIN Content ON Module.ContentId = Content.ContentId  INNER JOIN Course ON Module.CourseId = Course.CourseId INNER JOIN ContentCreator ON Content.CreatorId = ContentCreator.CreatorId;";
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
                String moduleTheme = resultSet.getString("Theme");
                String moduleDescription = resultSet.getString("Description");
                String status = resultSet.getString("Status");
                int moduleSerialNumber = resultSet.getInt("SerialNumber");
                int creatorId = resultSet.getInt("CreatorId");
                String creatorName = resultSet.getString("CreatorName");
                String creatorEmail = resultSet.getString("Email");
                String creatorOrganisation = resultSet.getString("Organisation");


                CourseModule courseModule = new CourseModule(contentId, courseId, courseName, subject, introductionText, level, publicationDate, moduleTitle, moduleTheme, moduleDescription, status, moduleSerialNumber, creatorId, creatorName, creatorEmail, creatorOrganisation);
                courseModuleList.add(courseModule);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

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

    public ObservableList<CourseModule> getCourseModules() {
        // Return courseModule array
        return courseModuleList;
    }

    public void insertCourse(String courseName, String subject, String introductionText, String level) throws SQLException{
        statement.executeUpdate("INSERT INTO Course(CourseName, Subject, IntroductionText, Level) VALUES ('"+courseName+"','"+subject+"','"+introductionText+"','"+level+"')");
     }

    public void updateCourseModuleString(String column, String newValue, int id){
        try{
            if(column.equals("CourseName") || column.equals("Subject") || column.equals("Level")){
                statement.executeUpdate("UPDATE Course SET "+column+" = '"+newValue+"' WHERE CourseId = "+id);
            } else if( column.equals("Title") || column.equals("Theme") || column.equals("Description") || column.equals("Status")){
                statement.executeUpdate("UPDATE Content SET "+column+" = '"+newValue+"' WHERE ContentId ="+id);
            } else {
                statement.executeUpdate("UPDATE Module SET "+column+" = '"+newValue+"' WHERE ContentId = "+id);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteCourse(CourseModule selectedItem) throws SQLException{
        statement.executeUpdate("DELETE FROM Course WHERE CourseId = '"+selectedItem.getCourseId()+"'");
    }
         

    // public int getCreatorIdWithName(String name) throws SQLException {
    //     String sql = "SELECT CreatorId FROM ContentCreator WHERE Name = '"+name+"'";
    //     int creatorId = statement.executeUpdate(sql);
    //     return creatorId;
    // }
    
    public void insertModule(int courseId, int contentId, Date publicationDate, String title, String theme, String description, String status, int serialNumber, String creatorId) throws SQLException{
        statement.executeUpdate("INSERT INTO Content(ContentId, PublicationDate, Status, Theme, Title, Description, CreatorId) VALUES ('"+contentId+"','"+publicationDate+"','"+status+"','"+theme+"','"+title+"','"+description+"','"+creatorId+"')");
        statement.executeUpdate("INSERT INTO Module(ContentId, SerialNumber, CourseId) VALUES ('"+contentId+"','"+serialNumber+"','"+courseId+"')");
    }
}

