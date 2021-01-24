package src.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.domain.ViewStatistics;

public class DatabaseViewStatistics extends Database {
    private ObservableList<ViewStatistics> percentageModuleList;
    private double totalPercentage;

    public DatabaseViewStatistics(String connectionUrl) {
        super(connectionUrl);
        this.percentageModuleList = FXCollections.observableArrayList();
    }
    
    //Load all attributes from the Registration table
    public void loadPercentageModule(int studentId){
        try{
            connect();

            String sql = "SELECT Module.Title, Module.VersionNr, ViewStatistics.Percentage FROM ViewStatistics INNER JOIN Module ON ViewStatistics.ContentId = Module.ContentId WHERE ViewStatistics.StudentId = '"+studentId+"';";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            
            while(resultSet.next()){
                String moduleTitle = resultSet.getString("Title");
                int versionNr = resultSet.getInt("VersionNr");
                double percentage = resultSet.getDouble("Percentage");

                ViewStatistics moduleStatistics = new ViewStatistics(moduleTitle, versionNr, percentage);
                percentageModuleList.add(moduleStatistics);
            }
        }catch (Exception e) {
            e.printStackTrace();
        } 
    }

    //Return the list with percentages
    public ObservableList<ViewStatistics> getPercentageModules(){
        return percentageModuleList;
    }

    //Return a percentage from all accounts per module
    public double getPercentageAllAccounts(int contentId) throws Exception {
            connect();

            String sql = "SELECT (SELECT SUM(ViewStatistics.Percentage) FROM ViewStatistics INNER JOIN Module ON ViewStatistics.ContentId = Module.ContentId WHERE Module.ContentId = '"+contentId+"')/(SELECT COUNT(ViewStatistics.StudentId) FROM ViewStatistics INNER JOIN Module ON ViewStatistics.ContentId = Module.ContentId WHERE Module.ContentId = '"+contentId+"') AS TotalPercentage";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

                
            while(resultSet.next()){
                totalPercentage = resultSet.getDouble("TotalPercentage");
            }

            return totalPercentage;  
    }

}
