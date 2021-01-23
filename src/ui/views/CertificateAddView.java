package src.ui.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import src.db.DatabaseRegistration;
import src.db.FillComboBox;
import src.domain.Student;
import src.ui.GUI;
import src.validators.GradeValidator;

public class CertificateAddView {
    private DatabaseRegistration databaseRegistration;
    private Student student;
    private Label viewTitleLabel;
    private Button backButton;
    private Label studentLabel;
    private Label staffNameLabel;
    private TextField staffNameTextField;
    private Label gradeLabel;
    private TextField gradeTextField;
    private Label courseLabel;
    private ComboBox<String> courseComboBox;
    private ObservableList<String> courseList;
    private Button insertButton;
    private Alert alert;
    private Region region;
    private HBox topLayout;
    private VBox layout;
    private FillComboBox fcb;

    public CertificateAddView(Student student) {

        databaseRegistration = new DatabaseRegistration("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseRegistration.loadRegistrations(student.getStudentId());
        this.student=student;

        viewTitleLabel = new Label("Add certificate");
        backButton = new Button("Back");
        studentLabel = new Label("For student: " +student.getName());
        courseLabel = new Label("Course");
        courseComboBox = new ComboBox<>();
        courseList = FXCollections.observableArrayList();
        fcb = new FillComboBox("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        staffNameLabel = new Label("Staff Name:");
        staffNameTextField = new TextField();
        gradeLabel = new Label("Grade:");
        gradeTextField = new TextField();
        gradeTextField.setPromptText("0.0");
        
        insertButton = new Button("Insert");

        region = new Region();
        topLayout = new HBox(10);
        layout = new VBox(10);
    }

    protected void configureNodes() {
        viewTitleLabel.getStyleClass().add("view-title");
        
        try {
            fcb.fillListFromDataBaseStringCertificates(courseList, student.getStudentId());
        } catch (Exception e) {
            System.out.println(e);
        }

        courseComboBox.setItems(courseList);
    }

    protected void configureLayout() {
        HBox.setHgrow(region, Priority.ALWAYS);
        topLayout.getChildren().addAll(viewTitleLabel, region, backButton);
        
        layout.setPadding(new Insets(10, 10, 10, 15));
        layout.getChildren().addAll(topLayout, studentLabel, courseLabel, courseComboBox, staffNameLabel, staffNameTextField, gradeLabel, gradeTextField, insertButton);
    }

    protected void handleActions() {        
        backButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new UserDetailView(student).getView());
        });

        insertButton.setOnAction(e -> {
            try {
                if (staffNameTextField.getText().isEmpty() ||  courseComboBox.getValue() == null) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Missing field error");
                    alert.setContentText("You didn't fill in all the necessary fields!");
                    alert.showAndWait();
                } else if(gradeTextField.getText().isEmpty() || !GradeValidator.isValid(Double.valueOf(gradeTextField.getText()))){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Grade is incorrect");
                    alert.setContentText("You didn't fill in a valid grade!");
                    alert.showAndWait();
                }else {
                    databaseRegistration.insertCertificate(staffNameTextField.getText(), Double.valueOf(gradeTextField.getText()));
                    databaseRegistration.updateRegistrationWithCertificate(student.getStudentId(), courseComboBox.getValue(), staffNameTextField.getText(), Double.valueOf(gradeTextField.getText()));

                    staffNameTextField.clear();
                    gradeTextField.clear();
                    courseComboBox.setValue(null);

                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Registrated");
                    alert.setHeaderText("Success!");
                    alert.setContentText("The certificate is added!");
                    alert.showAndWait();
                }
            } catch (Exception error) {
                error.printStackTrace();

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Connection Error");
                alert.setHeaderText("Connection failed.");
                alert.setContentText("Please check your internet connection, or try again later.");
                alert.showAndWait();
            }
        });
    }

    public Pane getView() {
        configureNodes();
        configureLayout();
        handleActions();

        return layout;
    }
}
