package src.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import src.db.DatabaseCourseModule;
import src.db.FillComboBox;
import src.domain.CourseModule;

public class RecommendedCourseAddView {
    private DatabaseCourseModule databaseCourses;
    private FillComboBox fcb;


    public RecommendedCourseAddView() {
        databaseCourses = new DatabaseCourseModule("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseCourses.loadCourseModules();
        fcb = new FillComboBox("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
    }

    public Parent getView(CourseModule selectedItem) throws Exception {

        VBox layout = new VBox();

        Label titleLabel = new Label("Add Recommendation");
        titleLabel.getStyleClass().add("view-title");
        titleLabel.setPadding(new Insets(0, 0, 10, 0));

        Label recommendationName = new Label("Which course do you want to add as recommendation?");
        ComboBox<String> courseComboBox = new ComboBox<String>();
        ObservableList<String> listCourses = FXCollections.observableArrayList();
        fcb.fillListFromDataBaseString(listCourses, "CourseName","Course");
        courseComboBox.setItems(listCourses);


        Button btnInsert = new Button("Insert");

        layout.getChildren().addAll(titleLabel,recommendationName, courseComboBox, btnInsert);

        //Insert button action
        btnInsert.setOnAction((event) -> {
            try{             
                    databaseCourses.insertRecommendation(selectedItem.getCourseId(), String.valueOf(courseComboBox.getValue()));
                
                    courseComboBox.setValue(null);

                    Alert succesAlert = new Alert(AlertType.CONFIRMATION);
                    succesAlert.setTitle("Registrated");
                    succesAlert.setHeaderText("Success!");
                    succesAlert.setContentText("The recommendation is added!");
                    succesAlert.showAndWait();

            } catch(Exception e) {
                e.printStackTrace();
            }
        });
        
        return layout;
    }
}
