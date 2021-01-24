package src.ui.views;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import src.db.DatabaseRegistration;
import src.db.FillComboBox;
import src.domain.DatePickerConverter;
import src.domain.Student;
import src.ui.GUI;

public class RegistrationAddView {
    private Student student;
    private DatabaseRegistration databaseRegistration;
    private FillComboBox fcb;
    private Label viewTitleLabel;
    private Button closeButton;
    private Label courseLabel;
    private ComboBox<String> courseComboBox;
    private ObservableList<String> listCourses;
    private Label registrationDateLabel;
    private DatePicker datePicker;
    private DatePickerConverter DatePickerConverter;
    private Callback<DatePicker, DateCell> dayCellFactory;
    private Button insertButton;
    private Region region;
    private HBox topLayout;
    private VBox layout;

    //Initialize the variables
    public RegistrationAddView(Student student) {
        this.student = student;
        databaseRegistration = new DatabaseRegistration("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseRegistration.loadRegistrations(student.getStudentId());
        fcb = new FillComboBox("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");

        viewTitleLabel = new Label("Add Registration");
        closeButton = new Button("Close");
        courseLabel = new Label("Course");
        courseComboBox = new ComboBox<String>();
        listCourses = FXCollections.observableArrayList();
        registrationDateLabel = new Label("Registration Date");
        datePicker = new DatePicker();
        DatePickerConverter = new DatePickerConverter("yyyy-MM-dd");
        dayCellFactory = new Callback<DatePicker, DateCell>(){
            public DateCell call(final DatePicker datePicker){
                return new DateCell(){
                    @Override
                    public void updateItem(LocalDate item, boolean empty){
                        super.updateItem(item, empty);
 
                        // Show weekends in blue color
                        DayOfWeek day = DayOfWeek.from(item);
                        if(day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY){
                            this.setTextFill(Color.BLUE);
                        }
                    }
                };
            }
        };
        insertButton = new Button("Insert");

        region = new Region();
        topLayout = new HBox();
        layout = new VBox();
    }

    //Configure the nodes
    private void configureNodes() {
        viewTitleLabel.getStyleClass().add("view-title");
        try {
                fcb.fillListFromDataBaseString(listCourses, "CourseName", "Course");
            } catch (Exception e) {
                e.printStackTrace();
            }
        courseComboBox.setItems(listCourses);
        

        datePicker.setConverter(DatePickerConverter);
        datePicker.setPromptText("yyyy-MM-dd".toLowerCase());
        datePicker.setDayCellFactory(dayCellFactory);
    }

    //Configure the layout
    private void configureLayout() {
        HBox.setHgrow(region, Priority.ALWAYS);
        topLayout.getChildren().addAll(viewTitleLabel, region, closeButton);

        layout.getChildren().addAll(topLayout, courseLabel, courseComboBox, registrationDateLabel, datePicker, insertButton);
    }

    
    //Initialize the actions of the buttons
    private void handleActions() {
        closeButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new UserDetailView(student).getView());
        });

        insertButton.setOnAction(e -> {
            try { 
                if (datePicker.getValue() == null || courseComboBox.getValue() == null ){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Missing field error");
                    alert.setContentText("You didn't fill in all the necessary fields!");
                    alert.showAndWait();
                } else {
                    databaseRegistration.insertRegistration(student.getStudentId(), String.valueOf(courseComboBox.getValue()), Date.valueOf(datePicker.getValue()));
                    
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Registrated");
                    alert.setHeaderText("Success!");
                    alert.setContentText("The registration is added!");
                    alert.showAndWait();
                        
                    datePicker.setValue(null);
                    courseComboBox.setValue(null);
                }
            } catch (Exception error) {
                error.printStackTrace();
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
