package src.ui;

import src.db.DatabaseWebcast;
import src.domain.Status;
import src.domain.Webcast;

import java.sql.Date;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.control.ButtonType;

public class WebcastsView {
    DatabaseWebcast databaseWebcasts;
    WebcastAddView webcastsAddView;

    public WebcastsView() {
        databaseWebcasts = new DatabaseWebcast(
                "jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseWebcasts.loadWebcasts();
        webcastsAddView = new WebcastAddView();
    }

    public Parent getView() {
        VBox layout = new VBox();
        HBox topLayout = new HBox(10);
        Region filler = new Region();

        Label titleLabel = new Label("Webcasts");
        titleLabel.getStyleClass().add("view-title");
        titleLabel.setPadding(new Insets(0, 0, 10, 0));

        TableView<Webcast> webcastTable = new TableView<>();
        webcastTable.setEditable(true);

        Button removeWebcastButton = new Button("Remove");
        Button addWebcastButton = new Button("Add");

        addWebcastButton.setOnAction(e -> {
            layout.getChildren().clear();
            try {
                layout.getChildren().add(webcastsAddView.getView());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        removeWebcastButton.setOnAction((event) -> {
            try{
                Alert removeAlert = new Alert(AlertType.CONFIRMATION);
                removeAlert.setTitle("Delete");
                removeAlert.setHeaderText("Are you sure you want to delete this webcast?");
                removeAlert.setContentText("");
                // removeAlert.showAndWait();

                Optional<ButtonType> result = removeAlert.showAndWait();
	            if(!result.isPresent() || result.get() != ButtonType.OK) {
		            
	            } else {
		            Webcast selectedItem = webcastTable.getSelectionModel().getSelectedItem();
                    webcastTable.getItems().remove(selectedItem);
                    databaseWebcasts.deleteWebcast(selectedItem);
	            }
            }catch(Exception e) {
                e.printStackTrace();
            } 
        });


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

        TableColumn<Webcast, String> creatorNameColumn = new TableColumn<>("Creator Name");
        creatorNameColumn.setCellValueFactory(new PropertyValueFactory<>("creatorName"));
      

        webcastTable.setItems(databaseWebcasts.getWebcasts());
        webcastTable.getColumns().addAll(titleColumn, themeColumn, publicationDateColumn,statusColumn, descriptionColumn, urlColumn, durationColumn, creatorNameColumn);
        
        // Add layout
        HBox.setHgrow(filler, Priority.ALWAYS);
        topLayout.getChildren().addAll(titleLabel, filler, addWebcastButton, removeWebcastButton);
        layout.getChildren().addAll(topLayout, webcastTable);
        layout.setPadding(new Insets(10, 10, 10, 15));
        
        return layout;
    }

    
}
