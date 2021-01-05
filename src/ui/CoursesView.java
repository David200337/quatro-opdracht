package src.ui;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import src.db.DatabaseCourseModule;
import src.domain.CourseModule;
import src.domain.EditingCell;

public class CoursesView {
    private DatabaseCourseModule databaseCourses;
    private CourseAddView courseAddView;
    private CourseDetailView courseDetailView;
    TableView<CourseModule> coursesTableView = new TableView<>();


    public CoursesView() {
        databaseCourses = new DatabaseCourseModule("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        // databaseCourses.loadCourseModules();
        databaseCourses.loadCourse();
        courseAddView = new CourseAddView();
        courseDetailView = new CourseDetailView();
    }

    public Parent getView() {
        VBox layout = new VBox();
        HBox topLayout = new HBox(10);
        Region filler = new Region(); 

        Label titleLabel = new Label("Courses");
        titleLabel.getStyleClass().add("view-title");
        titleLabel.setPadding(new Insets(10, 10, 10, 15));

        Button addCourseButton = new Button("Add course");
        Button detailCourseButton = new Button("Go to details");
        Button removeCourseButton = new Button("Delete Course");



        addCourseButton.setOnAction(e -> {
            layout.getChildren().clear();
            layout.getChildren().add(courseAddView.getView());
        });


        detailCourseButton.setOnAction((event) -> {
            try{
                CourseModule courseModule = coursesTableView.getSelectionModel().getSelectedItem();
                layout.getChildren().clear();
                layout.getChildren().add(courseDetailView.getView(courseModule));
            }catch(Exception e) {
                e.printStackTrace();
            } 
        });

        removeCourseButton.setOnAction((event) -> {
            try{
                CourseModule courseModule = coursesTableView.getSelectionModel().getSelectedItem();
                coursesTableView.getItems().remove(courseModule);
                databaseCourses.deleteCourse(courseModule);
            }catch(Exception e) {
                e.printStackTrace();
            } 
        });

        
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
            ((CourseModule) t.getTableView().getItems().get(t.getTablePosition().getRow())).setIntroductionText(t.getNewValue());

            CourseModule course = t.getRowValue();
            course.setIntroductionText(t.getNewValue());
            databaseCourses.updateCourseModuleString("Description", t.getNewValue(), course.getCourseId());
        });

        TableColumn<CourseModule, String> levelCol = new TableColumn<>("Level");
        levelCol.setCellValueFactory(new PropertyValueFactory<>("level"));
        //Editen met combobox

        // TableColumn<CourseModule, String> titleCol = new TableColumn<>("Module title");
        // titleCol.setCellValueFactory(new PropertyValueFactory<>("moduleTitle"));
        // titleCol.setCellFactory(stringCellFactory);
        // titleCol.setOnEditCommit((TableColumn.CellEditEvent<CourseModule, String> t) -> {
        //     ((CourseModule) t.getTableView().getItems().get(t.getTablePosition().getRow())).setModuleTitle(t.getNewValue());

        //     CourseModule course = t.getRowValue();
        //     course.setModuleTitle(t.getNewValue());
        //     databaseCourses.updateCourseModuleString("Title", t.getNewValue(), course.getContentId());
        // });

        // TableColumn<CourseModule, String> themeCol = new TableColumn<>("Module Theme");
        // titleCol.setCellValueFactory(new PropertyValueFactory<>("moduleTheme"));
        // titleCol.setCellFactory(stringCellFactory);
        // titleCol.setOnEditCommit((TableColumn.CellEditEvent<CourseModule, String> t) -> {
        //     ((CourseModule) t.getTableView().getItems().get(t.getTablePosition().getRow())).setModuleTheme(t.getNewValue());

        //     CourseModule course = t.getRowValue();
        //     course.setModuleTheme(t.getNewValue());
        //     databaseCourses.updateCourseModuleString("Theme", t.getNewValue(), course.getContentId());
        // });

        // TableColumn<CourseModule, String> descriptionCol = new TableColumn<>("Module Description");
        // descriptionCol.setCellValueFactory(new PropertyValueFactory<>("moduleDescription"));
        // descriptionCol.setCellFactory(stringCellFactory);
        // descriptionCol.setOnEditCommit((TableColumn.CellEditEvent<CourseModule, String> t) -> {
        //     ((CourseModule) t.getTableView().getItems().get(t.getTablePosition().getRow())).setModuleDescription(t.getNewValue());

        //     CourseModule course = t.getRowValue();
        //     course.setModuleDescription(t.getNewValue());
        //     databaseCourses.updateCourseModuleString("Description", t.getNewValue(), course.getContentId());
        // });

        // TableColumn<CourseModule, String> statusCol = new TableColumn<>("Status");
        // statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        // //Editen met combobox

        // TableColumn<CourseModule, Integer> serialNumberCol = new TableColumn<>("Module serialnumber");
        // serialNumberCol.setCellValueFactory(new PropertyValueFactory<>("moduleSerialNumber"));
        //Mag niet veranderd worden

        coursesTableView.setItems(databaseCourses.getCourseModules());
        coursesTableView.getColumns().addAll(courseNameCol, subjectCol, introductionTextCol, levelCol);


        // Add layout
        HBox.setHgrow(filler, Priority.ALWAYS);
        topLayout.getChildren().addAll(titleLabel, filler, addCourseButton, detailCourseButton, removeCourseButton);
        layout.getChildren().addAll(topLayout, coursesTableView);
        layout.setPadding(new Insets(10, 10, 10, 15));



        return layout;
    }
}
