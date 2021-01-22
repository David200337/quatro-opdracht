package src.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.domain.ViewStatistics;

public class DatabaseViewStatistics extends Database {
    private ObservableList<ViewStatistics> percentageModuleList;

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

    
}
