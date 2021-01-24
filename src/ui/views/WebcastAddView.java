package src.ui.views;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import src.db.DatabaseWebcast;
import src.db.FillComboBox;
import src.domain.DatePickerConverter;
import src.domain.Status;
import src.ui.GUI;
import src.validators.URLValidator;

public class WebcastAddView {
    private DatabaseWebcast databaseWebcast;
    private FillComboBox fcb;
    private Label viewTitleLabel;
    private Button backButton;
    private Label contentItemLabel;
    private TextField contentItemTextField;
    private Label titleLabel;
    private TextField titleTextField;
    private Label themeLabel;
    private TextField themeTextField;
    private Label descriptionLabel;
    private TextField descriptionTextField;
    private Label publicationDateLabel;
    private DatePicker publicationDateDatePicker;
    private DatePickerConverter datePickerConverter;
    private Callback<DatePicker, DateCell> dayCellFactory;
    private Label statusLabel;
    private ComboBox<Status> statusComboBox;
    private Label urlLabel;
    private TextField urlTextField;
    private Label durationLabel;
    private TextField durationTextField;
    private Label creatorLabel;
    private ComboBox<String> creatorComboBox;
    private ObservableList<String> creatorsList;
    private Button insertButton;
    private Alert alert;
    private Region region;
    private HBox topLayout;
    private VBox layout;

    // Initialize the variables
    public WebcastAddView() {
        databaseWebcast = new DatabaseWebcast(
                "jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseWebcast.loadWebcasts();
        fcb = new FillComboBox("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");

        viewTitleLabel = new Label("Add webcast");
        backButton = new Button("Back");
        contentItemLabel = new Label("Content Item");
        contentItemTextField = new TextField();
        titleLabel = new Label("Title");
        titleTextField = new TextField();
        themeLabel = new Label("Theme");
        themeTextField = new TextField();
        descriptionLabel = new Label("Description");
        descriptionTextField = new TextField();
        publicationDateLabel = new Label("Publication Date");
        publicationDateDatePicker = new DatePicker();
        datePickerConverter = new DatePickerConverter("yyyy-MM-dd");
        dayCellFactory = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        // Show weekends in blue color
                        DayOfWeek day = DayOfWeek.from(item);
                        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                            this.setTextFill(Color.BLUE);
                        }
                    }
                };
            }
        };
        statusLabel = new Label("Status");
        statusComboBox = new ComboBox<>();
        urlLabel = new Label("URL");
        urlTextField = new TextField();
        durationLabel = new Label("Duration");
        durationTextField = new TextField();
        durationTextField.setPromptText("hh:mm:ss");
        creatorLabel = new Label("Creator");
        creatorComboBox = new ComboBox<>();
        creatorsList = FXCollections.observableArrayList();
        insertButton = new Button("Insert");

        region = new Region();
        topLayout = new HBox(10);
        layout = new VBox(10);
    }

    // Configure the nodes
    private void configureNodes() {
        viewTitleLabel.getStyleClass().add("view-title");

        publicationDateDatePicker.setConverter(datePickerConverter);
        publicationDateDatePicker.setPromptText("yyyy-MM-dd".toLowerCase());
        publicationDateDatePicker.setDayCellFactory(dayCellFactory);

        statusComboBox.getItems().setAll(Status.values());

        try {
            fcb.fillListFromDataBaseString(creatorsList, "Name", "ContentCreator");
        } catch (Exception e) {
            System.out.println(e);
        }

        creatorComboBox.setItems(creatorsList);
    }

    // Configure the layout
    private void configureLayout() {
        HBox.setHgrow(region, Priority.ALWAYS);
        topLayout.getChildren().addAll(viewTitleLabel, region, backButton);

        layout.setPadding(new Insets(10, 10, 10, 15));
        layout.getChildren().addAll(topLayout, contentItemLabel, contentItemTextField, titleLabel, titleTextField,
                themeLabel, themeTextField, descriptionLabel, descriptionTextField, publicationDateLabel,
                publicationDateDatePicker, statusLabel, statusComboBox, urlLabel, urlTextField, durationLabel,
                durationTextField, creatorLabel, creatorComboBox, insertButton);
    }

    // Initialize the actions of the buttons
    private void handleActions() {
        backButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new WebcastsView().getView());
        });

        insertButton.setOnAction(e -> {
            try {
                if (contentItemTextField.getText().isEmpty() || titleTextField.getText().isEmpty()
                        || durationTextField.getText().isEmpty() || statusComboBox.getValue() == null
                        || creatorComboBox.getValue() == null) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Missing field error");
                    alert.setContentText("You didn't fill in all the necessary fields!");
                    alert.showAndWait();
                } else if (urlTextField.getText().isEmpty() || !URLValidator.isValid(urlTextField.getText())) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error in the URL field");
                    alert.setContentText("Your URL is invalid");
                    alert.showAndWait();
                } else {
                    databaseWebcast.insertWebcast(Integer.parseInt(contentItemTextField.getText()),
                            Date.valueOf(publicationDateDatePicker.getValue()), titleTextField.getText(),
                            themeTextField.getText(), descriptionTextField.getText(),
                            String.valueOf(statusComboBox.getValue()), Time.valueOf(durationTextField.getText()),
                            String.valueOf(creatorComboBox.getValue()), urlTextField.getText());

                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Registrated");
                    alert.setHeaderText("Success!");
                    alert.setContentText("The webcast is added!");
                    alert.showAndWait();

                    contentItemTextField.clear();
                    publicationDateDatePicker.setValue(null);
                    titleTextField.clear();
                    themeTextField.clear();
                    descriptionTextField.clear();
                    statusComboBox.setValue(null);
                    durationTextField.clear();
                    creatorComboBox.setValue(null);
                    urlTextField.clear();
                }
            } catch (SQLException e1) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error in the ContentId field \n This content ID is already is use.");
                try {
                    alert.setContentText("Use a number after this number: "
                            + databaseWebcast.getLastIdInContentTable());
                } catch (Exception e2) {
                    e1.printStackTrace();
                }
                alert.showAndWait();
            } catch(Exception error) {
                System.out.println(error);
            }
        });
    }

    //Put everything together and show it
    public VBox getView() {
        configureNodes();
        configureLayout();
        handleActions();

        return layout;
    }
}
