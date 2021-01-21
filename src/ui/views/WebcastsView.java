package src.ui.views;

import java.sql.Date;
import java.util.Optional;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import src.db.DatabaseWebcast;
import src.domain.EditingCell;
import src.domain.Status;
import src.domain.Webcast;
import src.ui.GUI;
import src.validators.URLValidator;

public class WebcastsView {
    private DatabaseWebcast databaseWebcast;
    private Label viewTitleLabel;
    private Button addWebcastButton;
    private Button deleteWebcastButton;
    private TableView<Webcast> webcastTable;
    private Callback<TableColumn<Webcast, String>, TableCell<Webcast, String>> stringCellFactory;
    private TableColumn<Webcast, String> idColumn;
    private TableColumn<Webcast, String> titleColumn;
    private TableColumn<Webcast, String> descriptionColumn;
    private TableColumn<Webcast, Date> publicationDateColumn;
    private TableColumn<Webcast, Status> statusColumn;
    private ObservableList<Status> statusList;
    private TableColumn<Webcast, String> themeColumn;
    private TableColumn<Webcast, String> urlColumn;
    private TableColumn<Webcast, Integer> durationColumn;
    private TableColumn<Webcast, String> creatorNameColumn;
    private Alert alert;
    private Region region;
    private HBox topLayout;
    private VBox layout;

    public WebcastsView() {
        databaseWebcast = new DatabaseWebcast("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseWebcast.loadWebcasts();

        viewTitleLabel = new Label("Webcasts");
        addWebcastButton = new Button("Add");
        deleteWebcastButton = new Button("Delete");
        webcastTable = new TableView<>();
        stringCellFactory = (TableColumn<Webcast, String> param) -> new EditingCell<Webcast>();

        idColumn = new TableColumn<>("ID");
        titleColumn = new TableColumn<>("Title");
        descriptionColumn = new TableColumn<>("Description");
        publicationDateColumn = new TableColumn<>("Publication Date");
        statusColumn = new TableColumn<>("Status");
        statusList = FXCollections.observableArrayList(Status.values());
        themeColumn = new TableColumn<>("Theme");
        urlColumn = new TableColumn<>("URL");
        durationColumn = new TableColumn<>("Duration");
        creatorNameColumn = new TableColumn<>("Creator Name");

        region = new Region();
        topLayout = new HBox(10);
        layout = new VBox(10);
    }

    private void configureNodes() {
        viewTitleLabel.getStyleClass().add("view-title");

        webcastTable.setEditable(true);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("contentId"));
        
        titleColumn.setCellFactory(stringCellFactory);
        titleColumn.setOnEditCommit((TableColumn.CellEditEvent<Webcast, String> t) -> {
            ((Webcast) t.getTableView().getItems().get(t.getTablePosition().getRow())).setTitle(t.getNewValue());

            Webcast webcast = t.getRowValue();
            webcast.setTitle(t.getNewValue());
            databaseWebcast.updateWebcastString("Title", t.getNewValue(), webcast.getContentId());
        });

        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setCellFactory(stringCellFactory);
        descriptionColumn.setOnEditCommit((TableColumn.CellEditEvent<Webcast, String> t) -> {
            ((Webcast) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDescription(t.getNewValue());

            Webcast webcast = t.getRowValue();
            webcast.setDescription(t.getNewValue());
            databaseWebcast.updateWebcastString("Duration", t.getNewValue(), webcast.getContentId());
        });

        publicationDateColumn.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));

        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellValueFactory(new Callback<CellDataFeatures<Webcast, Status>, ObservableValue<Status>>() {
            @Override
            public ObservableValue<Status> call(CellDataFeatures<Webcast, Status> param) {
                Webcast webcast = param.getValue();

                Object statusCode = webcast.getStatus();

                Status status = Status.getByCode(statusCode);

                return new SimpleObjectProperty<Status>(status);
            }
        });
        statusColumn.setCellFactory(ComboBoxTableCell.forTableColumn(statusList));
        statusColumn.setOnEditCommit((CellEditEvent<Webcast, Status> event) -> {
            TablePosition<Webcast, Status> pos = event.getTablePosition();

            Status newStatus = event.getNewValue();

            int row = pos.getRow();
            Webcast webcast = event.getTableView().getItems().get(row);

            databaseWebcast.updateWebcastObject("Status", newStatus, webcast.getContentId());

            webcast.setStatus(newStatus.getCode());
        });

        themeColumn.setCellValueFactory(new PropertyValueFactory<>("theme"));
        themeColumn.setCellFactory(stringCellFactory);
        themeColumn.setOnEditCommit((TableColumn.CellEditEvent<Webcast, String> t) -> {
            ((Webcast) t.getTableView().getItems().get(t.getTablePosition().getRow())).setTheme(t.getNewValue());

            Webcast webcast = t.getRowValue();
            webcast.setTheme(t.getNewValue());
            databaseWebcast.updateWebcastString("Theme", t.getNewValue(), webcast.getContentId());
        });

        urlColumn.setCellValueFactory(new PropertyValueFactory<>("url"));
        urlColumn.setCellFactory(stringCellFactory);
        urlColumn.setOnEditCommit((TableColumn.CellEditEvent<Webcast, String> t) -> {
            ((Webcast) t.getTableView().getItems().get(t.getTablePosition().getRow())).setUrl(t.getNewValue());


            Webcast webcast = t.getRowValue();
            webcast.setUrl(t.getNewValue());
            if(t.getNewValue().isEmpty() || !URLValidator.isValid(t.getNewValue())){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error in the URL field");
                alert.setContentText("Your URL is invalid");
                alert.showAndWait();
            } else{
                databaseWebcast.updateWebcastString("URL", t.getNewValue(), webcast.getContentId());
            }
        });

        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        creatorNameColumn.setCellValueFactory(new PropertyValueFactory<>("creatorName"));

        webcastTable.setItems(databaseWebcast.getWebcasts());
        webcastTable.getColumns().addAll(idColumn, titleColumn, themeColumn, publicationDateColumn,statusColumn, descriptionColumn, urlColumn, durationColumn, creatorNameColumn);
    }

    private void configureLayout() {
        HBox.setHgrow(region, Priority.ALWAYS);
        topLayout.getChildren().addAll(viewTitleLabel, region, addWebcastButton, deleteWebcastButton);

        layout.setPadding(new Insets(10, 10, 10, 15));
        layout.getChildren().addAll(topLayout, webcastTable);
    }

    private void handleActions() {
        addWebcastButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new WebcastAddView().getView());
        });

        deleteWebcastButton.setOnAction(e -> {
            try {
                Webcast selectedItem = webcastTable.getSelectionModel().getSelectedItem();
                if (selectedItem == null){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Missing webcast");
                    alert.setContentText("You didn't select a webcast!");
                    alert.showAndWait();
                } else {
                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Delete");
                    alert.setHeaderText("Are you sure you want to delete this webcast?");

                    Optional<ButtonType> result = alert.showAndWait();
                    
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        webcastTable.getItems().remove(selectedItem);
                        databaseWebcast.deleteWebcast(selectedItem);
                    }
                }
            } catch(Exception error) {
                System.out.println(error);
            } 
        });
    }

    public VBox getView() {
        configureNodes();
        configureLayout();
        handleActions();

        return layout;
    }
}