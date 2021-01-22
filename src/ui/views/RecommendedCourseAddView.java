package src.ui.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import src.db.DatabaseCourseModule;
import src.db.FillComboBox;
import src.domain.CourseModule;
import src.ui.GUI;

public class RecommendedCourseAddView {
    private CourseModule courseModule;
    private DatabaseCourseModule databaseCourseModule;
    private FillComboBox fcb;
    private Label viewTitleLabel;
    private Button closeButton;
    private Label recommendationName;
    private ComboBox<String> courseComboBox;
    private ObservableList<String> listCourses;
    private Button insertButton;
    private Alert alert;
    private Region region;
    private HBox topLayout;
    private VBox layout;

    public RecommendedCourseAddView(CourseModule courseModule) {
        this.courseModule = courseModule;
        databaseCourseModule = new DatabaseCourseModule(
                "jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseCourseModule.loadCourseModules();
        fcb = new FillComboBox("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");

        viewTitleLabel = new Label("Add Recommended Course");
        closeButton = new Button("Close");
        recommendationName = new Label("Which course do you want to add as recommendation?");
        courseComboBox = new ComboBox<String>();
        listCourses = FXCollections.observableArrayList();
        insertButton = new Button("Insert");

        region = new Region();
        topLayout = new HBox(10);
        layout = new VBox(10);
    }

    private void configureNodes() {
        viewTitleLabel.getStyleClass().add("view-title");

        try {
            fcb.fillListFromDataBaseString(listCourses, "CourseName", "Course");
        } catch (Exception e) {
            System.out.println(e);
        }

        courseComboBox.setItems(listCourses);
    }

    private void configureLayout() {
        HBox.setHgrow(region, Priority.ALWAYS);
        topLayout.getChildren().addAll(viewTitleLabel, region, closeButton);

        layout.setPadding(new Insets(10, 10, 10, 15));
        layout.getChildren().addAll(topLayout, recommendationName, courseComboBox, insertButton);
    }

    private void handleActions() {
        closeButton.setOnAction(e -> {
            try {
                GUI.getLayout().setCenter(new CourseDetailView(courseModule).getView());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        insertButton.setOnAction(e -> {
            try {             
                databaseCourseModule.insertRecommendation(courseModule.getCourseId(), String.valueOf(courseComboBox.getValue()));
            
                courseComboBox.setValue(null);

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Registrated");
                alert.setHeaderText("Success!");
                alert.setContentText("The recommendation is added!");
                alert.showAndWait();

            } catch (Exception error) {
                System.out.println(e);
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