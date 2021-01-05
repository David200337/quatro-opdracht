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

            String sql = "SELECT Course.CourseId, Course.CourseName, Course.Subject, Course.IntroductionText, Course.Level, Content.ContentId, Content.PublicationDate, Content.Title, Content.Theme, Content.Description, Content.Status, Module.SerialNumber FROM Module INNER JOIN Content ON Module.ContentId = Content.ContentId INNER JOIN Course ON Module.CourseId = Course.CourseId";
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

                CourseModule courseModule = new CourseModule(contentId, courseId, courseName, subject, introductionText, level, publicationDate, moduleTitle, moduleTheme, moduleDescription, status, moduleSerialNumber);
                courseModuleList.add(courseModule);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }


    public ObservableList<CourseModule> getCourseModules() {
        // Return courseModule array
        return courseModuleList;
    }

    public void insertCourseModule(String courseName, String subject, String introductionText, String level, Date publicationDate, String moduleTitle, String moduleTheme, String moduleDescription, String status, int moduleSerialNumber) throws SQLException{
        statement.executeUpdate("INSERT INTO Course(CourseName, Subject, Introduction, Level) VALUES ('"+courseName+"','"+subject+"','"+introductionText+"','"+level+"'");
        statement.executeUpdate("INSERT INTO Content(PublicationDate, Title, Theme, Description, Status) VALUES ('"+publicationDate+"','"+moduleTitle+"','"+moduleTheme+"','"+moduleDescription+"','"+status+"'");
        statement.executeUpdate("INSERT INTO Module (SerialNumber) VALUES ('"+moduleSerialNumber+"'");
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

    public void deleteCourseModule(CourseModule selectedItem) throws SQLException{
        statement.executeUpdate("DELETE Course.CourseId, Course.CourseName, Course.Subject, Course.IntroductionText, Course.Level FROM Course WHERE CourseId = '"+selectedItem.getCourseId()+"'");
        statement.executeUpdate("DELETE Content.ContentId, Content.Title, Content.Description, Content.Status FROM Content WHERE ContentId = '"+selectedItem.getContentId()+"'");
        statement.executeUpdate("DELETE Module.SerialNumber FROM Module WHERE ContentId = '"+selectedItem.getModuleSerialNumber()+"'");
    }
          
    
}


