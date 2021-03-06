package src.db;

import src.domain.*;
import javafx.collections.ObservableList;

import java.sql.SQLException;

import javafx.collections.*;

public class DatabaseContentCreator extends Database {
    private ObservableList<ContentCreator> creators;

    public DatabaseContentCreator(String connectionUrl) {
        super(connectionUrl);
        this.creators = FXCollections.observableArrayList();
    }

    //Load all the creators selected by the query
    public void loadCreators(){
        try{
            connect();

            String sql = "SELECT * FROM ContentCreator";
            statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                int creatorId = resultSet.getInt("CreatorId");
                String name = resultSet.getString("Name");
                String email = resultSet.getString("Email");
                String organisation = resultSet.getString("Organisation");

                creators.add(new ContentCreator(creatorId, name, email, organisation));
            }
        } catch (Exception e){
            System.out.println("ERROR : \n\n" + e);
        }
    }

    //Return the list of creators
    public ObservableList<ContentCreator> getCreators(){
        return creators;
    }

    //Insert a creator
    public void insertCreator(String creatorName, String email, String organisation) throws SQLException{
        statement.executeUpdate("INSERT INTO ContentCreator(Name, Email, Organisation) VALUES ('"+creatorName+"','"+email+"','"+organisation+"')");
    }

    //Update a column from the creator table
    public void updateCreator(String column, String newValue, int id){
        try{
            if(column.equals("Name") || column.equals("Email") || column.equals("Organisation")){
                statement.executeUpdate("UPDATE ContentCreator SET "+column+" = '"+newValue+"' WHERE CreatorId = "+id);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Delete a selected creator
    public void deleteCreator(ContentCreator selectedItem) throws SQLException{
        statement.executeUpdate("DELETE FROM ContentCreator WHERE CreatorId = '"+selectedItem.getCreatorId()+"'");
    }
    
}
