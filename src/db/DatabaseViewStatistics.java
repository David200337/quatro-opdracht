package src.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.domain.ViewStatistics;

public class DatabaseViewStatistics extends Database {
    private ObservableList<ViewStatistics> percentageModuleList;
    private ObservableList<ViewStatistics> percentageAllAccounts;
    private double totalPercentage;
    // private double totalNumber;

    public DatabaseViewStatistics(String connectionUrl) {
        super(connectionUrl);
        this.percentageModuleList = FXCollections.observableArrayList();
    }
    
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

    public ObservableList<ViewStatistics> getPercentageModules(){
        return percentageModuleList;
    }

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

    // public ObservableList<ViewStatistics> getPercentageAllAccounts(){
    //     return percentageAllAccounts;
    // }


    // public double getSumPercentage(int courseId) throws Exception {
    //         connect();

    //         String sql = "SELECT SUM(Percentage) AS TotalPercentage FROM ViewStatistics WHERE ContentId LIKE (SELECT ContentId FROM Module WHERE CourseId = '"+courseId+"');";
    //         statement = connection.createStatement();
    //         resultSet = statement.executeQuery(sql);

            
    //         while(resultSet.next()){
    //             totalPercentage = resultSet.getDouble("TotalPercentage");
    //         }

    //     return totalPercentage;
    // }
    
    // public double getCountPercentage(int courseId) throws Exception {
    //     connect();

    //     String sql = "SELECT COUNT(StudentId) AS TotalNumber FROM ViewStatistics WHERE ContentId LIKE (SELECT ContentId FROM Module WHERE CourseId = '"+courseId+"');";
    //     statement = connection.createStatement();
    //     resultSet = statement.executeQuery(sql);

            
    //     while(resultSet.next()){
    //         totalNumber = resultSet.getDouble("TotalNumber");
    //     }

    //     return totalNumber;
    // }
}
