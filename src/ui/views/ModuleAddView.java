package src.ui.views;

import java.sql.Date;
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
import src.db.DatabaseCourseModule;
import src.db.FillComboBox;
import src.domain.CourseModule;
import src.domain.DatePickerConverter;
import src.domain.Status;
import src.ui.GUI;

public class ModuleAddView {
    private CourseModule courseModule;
    private DatabaseCourseModule databaseCourseModule;
    private FillComboBox fcb;
    private Label viewTitleLabel;
    private Button backButton;
    private Label contentLabel;
    private TextField contentTextField;
    private Label titleLabel;
    private TextField titleTextField;
    private Label versionLabel;
    private TextField versionTextField;
    private Label themeLabel;
    private TextField themeTextField;
    private Label descriptionLabel;
    private TextField descriptionTextField;
    private Label publicationDateLabel;
    private DatePicker datePicker;
    private DatePickerConverter datePickerConverter;
    private Callback<DatePicker, DateCell> dayCellFactory;
    private Label statusLabel;
    private ComboBox<Status> statusComboBox;
    private Label serialNumberLabel;
    private TextField serialNumberTextField;
    private Label creatorLabel;
    private ComboBox<String> creatorComboBox;
    private ObservableList<String> creatorList;
    private Button insertButton;
    private Alert alert;
    private Region region;
    private HBox topLayout;
    private VBox layout;

    public ModuleAddView(CourseModule courseModule) {
        this.courseModule = courseModule;
        databaseCourseModule = new DatabaseCourseModule("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseCourseModule.loadCourseModules();
        fcb = new FillComboBox("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");

        viewTitleLabel = new Label("Add module");
        backButton = new Button("Back");
        contentLabel = new Label("Content Item");
        contentTextField = new TextField();
        titleLabel = new Label("Title");
        titleTextField = new TextField();
        versionLabel = new Label("Version");
        versionTextField = new TextField();
        themeLabel = new Label("Theme");
        themeTextField = new TextField();
        descriptionLabel = new Label("Description");
        descriptionTextField = new TextField();
        publicationDateLabel = new Label("Publication Date");
        datePicker = new DatePicker();
        datePickerConverter = new DatePickerConverter("yyyy-MM-dd");
        dayCellFactory = new Callback<DatePicker, DateCell>(){
            public DateCell call(final DatePicker datePicker){
                return new DateCell(){
                    @Override
                    public void updateItem(LocalDate item, boolean empty){
                        super.updateItem(item, empty);

                        //Show weekends in blue color
                        DayOfWeek day = DayOfWeek.from(item);
                        if(day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY){
                            this.setTextFill(Color.BLUE);
                        }
                    }
                };
            }
        };
        statusLabel = new Label("Status");
        statusComboBox = new ComboBox<>();
        serialNumberLabel = new Label("Serial number");
        serialNumberTextField = new TextField();
        creatorLabel = new Label("Creator");
        creatorComboBox = new ComboBox<>();
        creatorList = FXCollections.observableArrayList();
        insertButton = new Button("Insert");

        region = new Region();
        topLayout = new HBox(10);
        layout = new VBox(10);
    }

    private void configureNodes() {
        viewTitleLabel.getStyleClass().add("view-title");

        datePicker.setConverter(datePickerConverter);
        datePicker.setPromptText("yyyy-MM-dd".toLowerCase());
        datePicker.setDayCellFactory(dayCellFactory);

        statusComboBox.getItems().setAll(Status.values());

        try {
            fcb.fillListFromDataBaseString(creatorList, "Name","ContentCreator");
        } catch (Exception e) {
            System.out.println(e);
        }

        creatorComboBox.setItems(creatorList);
        
    }

    private void configureLayout() {
        HBox.setHgrow(region, Priority.ALWAYS);
        topLayout.getChildren().addAll(viewTitleLabel, region, backButton);

        layout.setPadding(new Insets(10, 10, 10, 15));
        layout.getChildren().addAll(topLayout, contentLabel, contentTextField, titleLabel, titleTextField, versionLabel, versionTextField, themeLabel, themeTextField, descriptionLabel, descriptionTextField, publicationDateLabel, datePicker, statusLabel, statusComboBox, serialNumberLabel, serialNumberTextField, creatorLabel, creatorComboBox, insertButton);
    }

    private void handleActions() {
        backButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new CourseDetailView(courseModule).getView());
        });

        insertButton.setOnAction(e -> {
            try {
                if (titleTextField.getText().isEmpty() || versionTextField.getText().isEmpty() || statusComboBox.getValue() == null || serialNumberTextField.getText().isEmpty() || creatorComboBox.getValue() == null){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Missing field error");
                    alert.setContentText("You didn't fill in all the necessary fields!");
                    alert.showAndWait();
                } else {
                    databaseCourseModule.insertModule(courseModule.getCourseId(), Integer.parseInt(contentTextField.getText()), Date.valueOf(datePicker.getValue()), titleTextField.getText(), themeTextField.getText(), descriptionTextField.getText(), String.valueOf(statusComboBox.getValue()), Integer.parseInt(serialNumberTextField.getText()), String.valueOf(creatorComboBox.getValue()), Integer.parseInt(versionTextField.getText()));
                    
                    contentTextField.clear();
                    datePicker.setValue(null);
                    titleTextField.clear();
                    themeTextField.clear();
                    descriptionTextField.clear();
                    statusComboBox.setValue(null);
                    serialNumberTextField.clear();
                    creatorComboBox.setValue(null);
                    versionTextField.clear();

                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Registrated");
                    alert.setHeaderText("Success!");
                    alert.setContentText("The module is added!");
                    alert.showAndWait();
                }
            } catch (Exception error) {
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
