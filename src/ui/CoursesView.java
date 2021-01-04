package src.ui;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import src.db.DatabaseCourseModule;
import src.domain.CourseModule;
import src.domain.EditingCell;

public class CoursesView {
    DatabaseCourseModule databaseCourses;


    public CoursesView() {
        databaseCourses = new DatabaseCourseModule("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseCourses.loadCourseModules();
    }

    public Parent getView() {
        VBox layout = new VBox();

        Label titleLabel = new Label("Courses");
        titleLabel.getStyleClass().add("view-title");
        titleLabel.setPadding(new Insets(10, 10, 10, 15));

        TableView<CourseModule> coursesTableView = new TableView<>();
        coursesTableView.setEditable(true);
        Callback<TableColumn<CourseModule, String>, TableCell<CourseModule, String>> stringCellFactory = (TableColumn<CourseModule, String> param) -> new EditingCell<CourseModule>();

        TableColumn<CourseModule, String> courseNameCol = new TableColumn<>("Course Name");
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseNameCol.setCellFactory(stringCellFactory);
        courseNameCol.setOnEditCommit((TableColumn.CellEditEvent<CourseModule, String> t) -> {
            ((CourseModule) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCourseName(t.getNewValue());

            CourseModule course = t.getRowValue();
            course.setCourseName(t.getNewValue());
            databaseCourses.updateCourseModuleString("CourseName", t.getNewValue(), course.getCourseId());
        });

        TableColumn<CourseModule, String> subjectCol = new TableColumn<>("Subject");
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        subjectCol.setCellFactory(stringCellFactory);
        subjectCol.setOnEditCommit((TableColumn.CellEditEvent<CourseModule, String> t) -> {
            ((CourseModule) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSubject(t.getNewValue());

            CourseModule course = t.getRowValue();
            course.setSubject(t.getNewValue());
            databaseCourses.updateCourseModuleString("Subject", t.getNewValue(), course.getCourseId());
        });

        TableColumn<CourseModule, String> introductionTextCol = new TableColumn<>("Introduction Text");
        introductionTextCol.setCellValueFactory(new PropertyValueFactory<>("introductionText"));
        introductionTextCol.setCellFactory(stringCellFactory);
        introductionTextCol.setOnEditCommit((TableColumn.CellEditEvent<CourseModule, String> t) -> {
            ((CourseModule) t.getTableView().getItems().get(t.getTablePosition().getRow())).setModuleDescription(t.getNewValue());

            CourseModule course = t.getRowValue();
            course.setModuleDescription(t.getNewValue());
            databaseCourses.updateCourseModuleString("Description", t.getNewValue(), course.getCourseId());
        });

        TableColumn<CourseModule, String> levelCol = new TableColumn<>("Level");
        levelCol.setCellValueFactory(new PropertyValueFactory<>("level"));

        TableColumn<CourseModule, String> titleCol = new TableColumn<>("Module title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("moduleTitle"));

        TableColumn<CourseModule, String> descriptionCol = new TableColumn<>("Module Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("moduleDescription"));

        TableColumn<CourseModule, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<CourseModule, String> serialNumberCol = new TableColumn<>("Module serialnumber");
        serialNumberCol.setCellValueFactory(new PropertyValueFactory<>("moduleSerialNumber"));

        coursesTableView.setItems(databaseCourses.getCourseModules());
        coursesTableView.getColumns().addAll(courseNameCol, subjectCol, introductionTextCol, levelCol, titleCol, descriptionCol, statusCol, serialNumberCol);


        layout.getChildren().addAll(titleLabel, coursesTableView);

        return layout;
    }
}
