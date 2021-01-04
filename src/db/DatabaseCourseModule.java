package src.db;


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

            String sql = "SELECT Course.CourseId, Course.CourseName, Course.Subject, Course.IntroductionText, Course.Level, Content.ContentId, Content.Title, Content.Description, Content.Status, Module.SerialNumber FROM Module INNER JOIN Content ON Module.ContentId = Content.ContentId INNER JOIN Course ON Module.CourseId = Course.CourseId";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            
            while(resultSet.next()){
                int courseId = resultSet.getInt("CourseId");
                String courseName = resultSet.getString("CourseName");
                String subject = resultSet.getString("Subject");
                String introductionText = resultSet.getString("IntroductionText");
                String level = resultSet.getString("Level");
                String moduleTitle = resultSet.getString("Title");
                String moduleDescription = resultSet.getString("Description");
                String status = resultSet.getString("Status");
                int moduleSerialNumber = resultSet.getInt("SerialNumber");

                CourseModule courseModule = new CourseModule(courseId, courseName, subject, introductionText, level, moduleTitle, moduleDescription, status, moduleSerialNumber);
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

    public void updateCourseModuleString(String column, String newValue, int id){
        try{
            if(column.equals("CourseName") || column.equals("Subject") || column.equals("Level")){
                statement.executeUpdate("UPDATE Course SET "+column+" = '"+newValue+"' WHERE CourseId = "+id);
            } else if( column.equals("Title") || column.equals("Description") || column.equals("Status")){
                statement.executeUpdate("UPDATE Content SET "+column+" = '"+newValue+"' WHERE ContentId ="+id);
            } else {
                statement.executeUpdate("UPDATE Module SET "+column+" = '"+newValue+"' WHERE ContentId = "+id);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    
}


