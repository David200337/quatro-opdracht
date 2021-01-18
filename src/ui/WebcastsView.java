package src.ui;

import src.db.DatabaseWebcast;
import src.domain.CourseModule;
import src.domain.EditingCell;
import src.domain.Status;
import src.domain.Webcast;
import src.validators.URLValidator;

import java.sql.Date;
import java.util.Optional;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
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
        Callback<TableColumn<Webcast, String>, TableCell<Webcast, String>> stringCellFactory = (
                TableColumn<Webcast, String> param) -> new EditingCell<Webcast>();

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

        TableColumn<Webcast, String> contentIdColumn = new TableColumn<>("ID");
        contentIdColumn.setCellValueFactory(new PropertyValueFactory<>("contentId"));

        TableColumn<Webcast, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setCellFactory(stringCellFactory);
        titleColumn.setOnEditCommit((TableColumn.CellEditEvent<Webcast, String> t) -> {
            ((Webcast) t.getTableView().getItems().get(t.getTablePosition().getRow())).setTitle(t.getNewValue());

            Webcast webcast = t.getRowValue();
            webcast.setTitle(t.getNewValue());
            databaseWebcasts.updateWebcastString("Title", t.getNewValue(), webcast.getContentId());
        });

        TableColumn<Webcast, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setCellFactory(stringCellFactory);
        descriptionColumn.setOnEditCommit((TableColumn.CellEditEvent<Webcast, String> t) -> {
            ((Webcast) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDescription(t.getNewValue());

            Webcast webcast = t.getRowValue();
            webcast.setDescription(t.getNewValue());
            databaseWebcasts.updateWebcastString("Duration", t.getNewValue(), webcast.getContentId());
        });

        TableColumn<Webcast, Date> publicationDateColumn = new TableColumn<>("Publication Date");
        publicationDateColumn.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));

        TableColumn<Webcast, Status> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        ObservableList<Status> listStatus = FXCollections.observableArrayList(Status.values());
        
        statusColumn.setCellValueFactory(new Callback<CellDataFeatures<Webcast, Status>, ObservableValue<Status>>() {
            @Override

            public ObservableValue<Status> call(CellDataFeatures<Webcast, Status> param) {
                Webcast webcast = param.getValue();

                Object statusCode = webcast.getStatus();

                Status status = Status.getByCode(statusCode);

                return new SimpleObjectProperty<Status>(status);
            }

        });

        statusColumn.setCellFactory(ComboBoxTableCell.forTableColumn(listStatus));
        statusColumn.setOnEditCommit((CellEditEvent<Webcast, Status> event) -> {
            TablePosition<Webcast, Status> pos = event.getTablePosition();

            Status newStatus = event.getNewValue();

            int row = pos.getRow();
            Webcast webcast = event.getTableView().getItems().get(row);

            databaseWebcasts.updateWebcastObject("Status", newStatus, webcast.getContentId());

            webcast.setStatus(newStatus.getCode());
        });

        TableColumn<Webcast, String> themeColumn = new TableColumn<>("Theme");
        themeColumn.setCellValueFactory(new PropertyValueFactory<>("theme"));
        themeColumn.setCellFactory(stringCellFactory);
        themeColumn.setOnEditCommit((TableColumn.CellEditEvent<Webcast, String> t) -> {
            ((Webcast) t.getTableView().getItems().get(t.getTablePosition().getRow())).setTheme(t.getNewValue());

            Webcast webcast = t.getRowValue();
            webcast.setTheme(t.getNewValue());
            databaseWebcasts.updateWebcastString("Theme", t.getNewValue(), webcast.getContentId());
        });

        TableColumn<Webcast, String> urlColumn = new TableColumn<>("URL");
        urlColumn.setCellValueFactory(new PropertyValueFactory<>("url"));
        urlColumn.setCellFactory(stringCellFactory);
        urlColumn.setOnEditCommit((TableColumn.CellEditEvent<Webcast, String> t) -> {
            ((Webcast) t.getTableView().getItems().get(t.getTablePosition().getRow())).setUrl(t.getNewValue());


            Webcast webcast = t.getRowValue();
            webcast.setUrl(t.getNewValue());
            if(t.getNewValue().isEmpty() || !URLValidator.isValid(t.getNewValue())){
                Alert URLAlert = new Alert(AlertType.ERROR);
                URLAlert.setTitle("Error");
                URLAlert.setHeaderText("Error in the URL field");
                URLAlert.setContentText("Your URL is invalid");
                URLAlert.showAndWait();
            } else{
                databaseWebcasts.updateWebcastString("URL", t.getNewValue(), webcast.getContentId());
            }
            
        });
        
        TableColumn<Webcast, Integer> durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        TableColumn<Webcast, String> creatorNameColumn = new TableColumn<>("Creator Name");
        creatorNameColumn.setCellValueFactory(new PropertyValueFactory<>("creatorName"));
      

        webcastTable.setItems(databaseWebcasts.getWebcasts());
        webcastTable.getColumns().addAll(contentIdColumn, titleColumn, themeColumn, publicationDateColumn,statusColumn, descriptionColumn, urlColumn, durationColumn, creatorNameColumn);
        
        // Add layout
        HBox.setHgrow(filler, Priority.ALWAYS);
        topLayout.getChildren().addAll(titleLabel, filler, addWebcastButton, removeWebcastButton);
        layout.getChildren().addAll(topLayout, webcastTable);
        layout.setPadding(new Insets(10, 10, 10, 15));
        
        return layout;
    }

    
}
