package src.ui.views;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import src.db.DatabaseContentCreator;
import src.domain.ContentCreator;
import src.domain.EditingCell;
import src.ui.GUI;

public class ContentCreatorView {
    private DatabaseContentCreator databaseCreators;
    private Label viewTitleLabel;
    private Button addCreatorButton;
    private Button deleteCreatorButton;
    private TableView<ContentCreator> creatorsTableView;
    private Alert alert;
    private Region region;
    private HBox topLayout;
    private VBox layout;

    //Initialize the variables
    public ContentCreatorView() {
        databaseCreators = new DatabaseContentCreator("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseCreators.loadCreators();
        
        viewTitleLabel = new Label("Content Creators");
        addCreatorButton = new Button("Add");
        deleteCreatorButton = new Button("Delete");
        creatorsTableView = new TableView<>();

        
        region = new Region();
        topLayout = new HBox(10);
        layout = new VBox(10);
    }

    //Configure the nodes
    private void configureNodes() {
        viewTitleLabel.getStyleClass().add("view-title");

        creatorsTableView.setEditable(true);

        Callback<TableColumn<ContentCreator, String>, TableCell<ContentCreator, String>> stringCellFactory = (TableColumn<ContentCreator, String> param) -> new EditingCell<ContentCreator>();

        TableColumn<ContentCreator, String> creatorNameCol = new TableColumn<>("Name");
        creatorNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        creatorNameCol.setCellFactory(stringCellFactory);
        creatorNameCol.setOnEditCommit((TableColumn.CellEditEvent<ContentCreator, String> t) -> {
            ((ContentCreator) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());

            ContentCreator creator = t.getRowValue();
            creator.setName(t.getNewValue());
            databaseCreators.updateCreator("Name", t.getNewValue(), creator.getCreatorId());
        });

        TableColumn<ContentCreator, String> emailCol = new TableColumn<>("E-Mail");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setCellFactory(stringCellFactory);
        emailCol.setOnEditCommit((TableColumn.CellEditEvent<ContentCreator, String> t) -> {
            ((ContentCreator) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEmail(t.getNewValue());

            ContentCreator creator = t.getRowValue();
            creator.setEmail(t.getNewValue());
            databaseCreators.updateCreator("Email", t.getNewValue(), creator.getCreatorId());
        });

        TableColumn<ContentCreator, String> organisationCol = new TableColumn<>("Organisation");
        organisationCol.setCellValueFactory(new PropertyValueFactory<>("organisation"));
        organisationCol.setCellFactory(stringCellFactory);
        organisationCol.setOnEditCommit((TableColumn.CellEditEvent<ContentCreator, String> t) -> {
            ((ContentCreator) t.getTableView().getItems().get(t.getTablePosition().getRow())).setOrganisation(t.getNewValue());

            ContentCreator creator = t.getRowValue();
            creator.setOrganisation(t.getNewValue());
            databaseCreators.updateCreator("Organisation", t.getNewValue(), creator.getCreatorId());
        });


        creatorsTableView.setItems(databaseCreators.getCreators());
        creatorsTableView.getColumns().addAll(creatorNameCol, emailCol, organisationCol);

    }

    //Configure the layout
    private void configureLayout() {
        HBox.setHgrow(region, Priority.ALWAYS);
        topLayout.getChildren().addAll(viewTitleLabel, region, addCreatorButton, deleteCreatorButton);
        layout.setPadding(new Insets(10, 10, 10, 15));
        layout.getChildren().addAll(topLayout, creatorsTableView);
    }

    //Initialize the actions of the buttons
    private void handleActions() {
        addCreatorButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new CreatorAddView().getView());
        });


        deleteCreatorButton.setOnAction(e -> {
            try {
                ContentCreator creator = creatorsTableView.getSelectionModel().getSelectedItem();

                if (creator == null){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Missing creator");
                    alert.setContentText("You didn't select a creator!");
                    alert.showAndWait();
                } else {
                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Delete");
                    alert.setHeaderText("Are you sure you want to delete this creator?");
                    
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        creatorsTableView.getItems().remove(creator);
                        databaseCreators.deleteCreator(creator);
                    }
                }
            }catch(Exception error) {
                System.out.println(error);
            } 
        });
    }

    //Put everything together and show it
    public Pane getView() {
        configureNodes();
        configureLayout();
        handleActions();
        
        return layout;
    }
}
