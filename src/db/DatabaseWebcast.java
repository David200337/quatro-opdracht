package src.db;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import javafx.collections.*;
import src.domain.*;


public class DatabaseWebcast extends Database{
    private ObservableList<Webcast> webcasts;


    public DatabaseWebcast(String connectionUrl) {
        super(connectionUrl);
        this.webcasts = FXCollections.observableArrayList();
    }
    
    public void loadWebcasts() {
        try {
            connect();

            // String sql = "SELECT Webcast.ContentId, Webcast.PublicationDate, Webcast.Status, Webcast.Theme, Webcast.Title, Webcast.Description, ContentCreator.CreatorId, ContentCreator.Name, ContentCreator.Email, ContentCreator.Organisation FROM ContentCreator INNER JOIN Webcast ON Webcast.CreatorId = ContentCreator.CreatorId";
            String sql = "SELECT Content.ContentId, Content.PublicationDate, Content.Status, Content.Theme, Content.Description, Webcast.Title, Webcast.URL, Webcast.Duration, ContentCreator.CreatorId, ContentCreator.Name, ContentCreator.Email, ContentCreator.Organisation FROM Content INNER JOIN Webcast ON Webcast.ContentId = Content.ContentId INNER JOIN ContentCreator ON Content.CreatorId = ContentCreator.CreatorId;";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            
            while(resultSet.next()){
                Webcast webcast = new Webcast();

                int contentId = resultSet.getInt("ContentId");
                Date publicationDate = resultSet.getDate("PublicationDate");
                String status = resultSet.getString("Status");
                String theme = resultSet.getString("Theme");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String url = resultSet.getString("URL");
                Time duration = resultSet.getTime("Duration");
                
                webcast.setContentId(contentId);
                webcast.setPublicationDate(publicationDate);
                webcast.setStatus(status);
                webcast.setTitle(title);
                webcast.setTheme(theme);
                webcast.setDescription(description);
                webcast.setUrl(url);
                webcast.setDuration(duration);
              
                String creatorName = resultSet.getString("Name");
                

                webcast.setCreatorName(creatorName);

                webcasts.add(webcast); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    public void loadWebcastTop3(){
        try{
            connect();

            String sql = "SELECT TOP 3 Webcast.Title FROM Webcast INNER JOIN ViewStatistics ON Webcast.ContentId = ViewStatistics.ContentId GROUP BY ViewStatistics.ContentId, Webcast.Title ORDER BY COUNT(ViewStatistics.StudentId);";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            
            while(resultSet.next()){
                String webcastTitle = resultSet.getString("Title");

                Webcast webcastTop3 = new Webcast(webcastTitle);
		        webcasts.add(webcastTop3);
            }
        }catch (Exception e) {
            e.printStackTrace();
        } 
    }

    public ObservableList<Webcast> getWebcasts() {
        // Return webcasts array
        return webcasts;
    }

    public void deleteWebcast(Webcast selectedItem) throws SQLException{
        statement.executeUpdate("DELETE FROM Webcast WHERE ContentId = '"+selectedItem.getContentId()+"'");
    }

    public void insertWebcast(int contentId, Date publicationDate, String title, String theme, String description, String status, Time duration, String name, String url) throws SQLException{
        statement.executeUpdate("INSERT INTO Content(ContentId, PublicationDate, Status, Theme, Description, CreatorId) VALUES ('"+contentId+"','"+publicationDate+"','"+status+"','"+theme+"','"+description+"',(SELECT CreatorId FROM ContentCreator WHERE Name = '"+name+"'))");
        statement.executeUpdate("INSERT INTO Webcast(ContentId, Title, URL, Duration) VALUES ('"+contentId+"','"+title+"','"+url+"','"+duration+"')");
    }

    public void updateWebcastString(String column, String newValue, int id){
        try{
            if(column.equals("Theme") || column.equals("Description") || column.equals("Status")){
                statement.executeUpdate("UPDATE Content SET "+column+" = '"+newValue+"' WHERE ContentId ="+id);
                System.out.println(newValue.toString());
            } else {
                statement.executeUpdate("UPDATE Webcast SET "+column+" = '"+newValue+"' WHERE ContentId = "+id);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public void updateWebcastObject(String column, Object newValue, int id){
        try{
            if(column.equals("Theme") || column.equals("Description") || column.equals("Status")){
                statement.executeUpdate("UPDATE Content SET "+column+" = '"+newValue+"' WHERE ContentId ="+id);
            } else {
                statement.executeUpdate("UPDATE Webcast SET "+column+" = '"+newValue+"' WHERE ContentId = "+id);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    

}
