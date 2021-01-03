package src.ui;

import java.sql.Date;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.db.*;
import src.domain.*;

public class GUIWebcasts extends Application {
    private DatabaseWebcast databaseWebcast;
    private Stage window;
    TableView<Webcast> webcastTable;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
        window.setTitle("Webcasts");
        databaseWebcast = new DatabaseWebcast(
                "jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseWebcast.loadWebcasts();

        webcastTable = new TableView<>();
        webcastTable.setEditable(true);

        TableColumn<Webcast, Integer> webcastIdColumn = new TableColumn<>("ID");
        webcastIdColumn.setCellValueFactory(new PropertyValueFactory<>("contentId"));

        TableColumn<Webcast, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Webcast, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Webcast, Date> publicationDateColumn = new TableColumn<>("Publication Date");
        publicationDateColumn.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));

        TableColumn<Webcast, Status> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Webcast, String> themeColumn = new TableColumn<>("Theme");
        themeColumn.setCellValueFactory(new PropertyValueFactory<>("theme"));

        TableColumn<Webcast, String> urlColumn = new TableColumn<>("URL");
        urlColumn.setCellValueFactory(new PropertyValueFactory<>("url"));
        
        TableColumn<Webcast, Integer> durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        
        TableColumn<Webcast, Integer> creatorIdColumn = new TableColumn<>("CreatorId");
        creatorIdColumn.setCellValueFactory(new PropertyValueFactory<>("creatorId"));

        
        TableColumn<Webcast, String> creatorNameColumn = new TableColumn<>("Creator Name");
        creatorNameColumn.setCellValueFactory(new PropertyValueFactory<>("creatorName"));
        
        TableColumn<Webcast, String> creatorEmailColumn = new TableColumn<>("Creator E-mail");
        creatorEmailColumn.setCellValueFactory(new PropertyValueFactory<>("creatorEmail"));

        
        TableColumn<Webcast, String> creatorOrganisationColumn = new TableColumn<>("Creator Organisation");
        creatorOrganisationColumn.setCellValueFactory(new PropertyValueFactory<>("creatorOrganisation"));

        webcastTable.setItems(databaseWebcast.getWebcasts());
        webcastTable.getColumns().addAll(webcastIdColumn, publicationDateColumn,statusColumn, themeColumn,titleColumn, descriptionColumn, urlColumn, durationColumn, creatorIdColumn, creatorNameColumn, creatorEmailColumn, creatorOrganisationColumn);
        VBox vBoxTable = new VBox();

        vBoxTable.getChildren().addAll(webcastTable);
        Scene tableScene = new Scene(vBoxTable);

        window.setScene(tableScene);
        window.show();
    
    }
}
