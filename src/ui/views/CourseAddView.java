package src.ui.views;

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
import src.db.DatabaseCourseModule;
import src.domain.Level;
import src.ui.GUI;

public class CourseAddView {
    private DatabaseCourseModule databaseCourses;
    private Label viewTitleLabel;
    private Button backButton;
    private Label nameLabel;
    private TextField nameTextField;
    private Label subjectLabel;
    private TextField subjectTextField;
    private Label introductionTextLabel;
    private TextField introductionTextTextField;
    private Label levelLabel;
    private ComboBox<Level> levelComboBox;
    private Button insertButton;
    private Alert alert;
    private Region region;
    private HBox topLayout;
    private VBox layout;

    public CourseAddView() {
        databaseCourses = new DatabaseCourseModule("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseCourses.loadCourseModules();

        viewTitleLabel = new Label("Add course");
        backButton = new Button("Back");
        nameLabel = new Label("Name");
        nameTextField = new TextField();
        subjectLabel = new Label("Subject");
        subjectTextField = new TextField();
        introductionTextLabel = new Label("Introduction Text");
        introductionTextTextField = new TextField();
        levelLabel = new Label("Level");
        levelComboBox = new ComboBox<>();
        insertButton = new Button("Insert");

        region = new Region();
        topLayout = new HBox(10);
        layout = new VBox(10);
    }

    protected void configureNodes() {
        viewTitleLabel.getStyleClass().add("view-title");
        levelComboBox.getItems().setAll(Level.values());
    }

    protected void configureLayout() {
        HBox.setHgrow(region, Priority.ALWAYS);
        topLayout.getChildren().addAll(viewTitleLabel, region, backButton);
        
        layout.setPadding(new Insets(10, 10, 10, 15));
        layout.getChildren().addAll(topLayout, nameLabel, nameTextField, subjectLabel, subjectTextField, introductionTextLabel, introductionTextTextField, levelLabel, levelComboBox, insertButton);
    }

    protected void handleActions() {        
        backButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new CoursesView().getView());
        });

        insertButton.setOnAction(e -> {
            try {
                if (nameTextField.getText().isEmpty() || subjectTextField.getText().isEmpty() || levelComboBox.getValue() == null) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Missing field error");
                    alert.setContentText("You didn't fill in all the necessary fields!");
                    alert.showAndWait();
                } else {
                    databaseCourses.insertCourse(nameTextField.getText(), subjectTextField.getText(), introductionTextTextField.getText(), String.valueOf(levelComboBox.getValue()));

                    nameTextField.clear();
                    subjectTextField.clear();
                    introductionTextTextField.clear();
                    levelComboBox.setValue(null);

                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Registrated");
                    alert.setHeaderText("Success!");
                    alert.setContentText("The course is added!");
                    alert.showAndWait();
                }
            } catch (Exception error) {
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
