package src.ui;


import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import src.domain.Level;
import src.db.DatabaseCourseModule;

public class CourseAddView {
    private DatabaseCourseModule databaseCourses;


    public CourseAddView() {
        databaseCourses = new DatabaseCourseModule("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseCourses.loadCourseModules();
    }

    public Parent getView() {
        VBox layout = new VBox();

        Label titleLabel = new Label("Add Course");
        titleLabel.getStyleClass().add("view-title");
        titleLabel.setPadding(new Insets(0, 0, 10, 0));

        //Insert course name
        Label lblCourseName = new Label("Course Name");
        TextField courseName = new TextField();
        
        //Insert subject
        Label lblSubject = new Label("Subject");
        TextField subject = new TextField();

        //Insert introductionText
        Label lblIntroductionText = new Label("Introduction Text");
        TextField introductionText = new TextField();

        //Insert level
        Label lblLevel = new Label("Level");
        ComboBox<Level> levelComboBox = new ComboBox<>();
        levelComboBox.getItems().setAll(Level.values());

        
        Button btnInsert = new Button("Insert");

        layout.getChildren().addAll(titleLabel, lblCourseName, courseName, lblSubject, subject, lblIntroductionText,introductionText , lblLevel, levelComboBox, btnInsert);

        //Insert button action
        btnInsert.setOnAction((event) -> {
            try{             
                
                databaseCourses.insertCourse(courseName.getText(), subject.getText(), introductionText.getText(), String.valueOf(levelComboBox.getValue()));
                // System.out.println("It worked!");  Date.valueOf(datePicker.getValue()), moduleTitle.getText(), moduleTheme.getText(), moduleDescription.getText(), String.valueOf(statusComboBox.getValue()), Integer.parseInt(moduleSerialNumber.getText())
                courseName.clear();
                subject.clear();
                introductionText.clear();
                levelComboBox.setValue(null);

            } catch(Exception e) {
                e.printStackTrace();
            }
        });
        
        return layout;
    }
}
