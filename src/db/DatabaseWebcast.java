package src.db;

import java.sql.Date;
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


    public ObservableList<Webcast> getWebcasts() {
        // Return webcasts array
        return webcasts;
    }

    

}
