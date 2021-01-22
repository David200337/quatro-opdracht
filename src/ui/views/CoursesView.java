package src.ui.views;

import java.util.Optional;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import src.db.DatabaseCourseModule;
import src.domain.CourseModule;
import src.domain.EditingCell;
import src.domain.Level;
import src.ui.GUI;

public class CoursesView {
    private DatabaseCourseModule databaseCourses;
    private Label viewTitleLabel;
    private Button addCourseButton;
    private Button viewDetailsButton;
    private Button removeCourseButton;
    private TableView<CourseModule> coursesTableView;
    private Alert alert;
    private Region region;
    private HBox topLayout;
    private VBox layout;

    public CoursesView() {
        databaseCourses = new DatabaseCourseModule("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseCourses.loadCourse();
        
        viewTitleLabel = new Label("Courses");
        addCourseButton = new Button("Add");
        viewDetailsButton = new Button("Go to details");
        removeCourseButton = new Button("Delete");
        coursesTableView = new TableView<>();

        region = new Region();
        topLayout = new HBox(10);
        layout = new VBox(10);
    }

    private void configureNodes() {
        viewTitleLabel.getStyleClass().add("view-title");

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

        TableColumn<CourseModule, Level> levelCol = new TableColumn<>("Level");
        levelCol.setCellValueFactory(new PropertyValueFactory<>("level"));
        ObservableList<Level> listLevel = FXCollections.observableArrayList(Level.values());
        
        levelCol.setCellValueFactory(new Callback<CellDataFeatures<CourseModule, Level>, ObservableValue<Level>>() {
            @Override
            public ObservableValue<Level> call(CellDataFeatures<CourseModule, Level> param) {
                CourseModule course = param.getValue();

                Object levelCode = course.getLevel();

                Level level = Level.getByCode(levelCode);

                return new SimpleObjectProperty<Level>(level);
            }
        });

        levelCol.setCellFactory(ComboBoxTableCell.forTableColumn(listLevel));
        levelCol.setOnEditCommit((CellEditEvent<CourseModule, Level> event) -> {
            TablePosition<CourseModule, Level> pos = event.getTablePosition();

            Level newStatus = event.getNewValue();

            int row = pos.getRow();
            CourseModule course = event.getTableView().getItems().get(row);

            databaseCourses.updateCourseModuleObject("Level", newStatus, course.getContentId());

            course.setStatus(newStatus.getCode());
        });

        coursesTableView.setItems(databaseCourses.getCourseModules());
        coursesTableView.getColumns().addAll(courseNameCol, subjectCol, introductionTextCol, levelCol);
    }

    private void configureLayout() {
        HBox.setHgrow(region, Priority.ALWAYS);
        topLayout.getChildren().addAll(viewTitleLabel, region, addCourseButton, viewDetailsButton, removeCourseButton);
        layout.setPadding(new Insets(10, 10, 10, 15));
        layout.getChildren().addAll(topLayout, coursesTableView);
    }

    private void handleActions() {
        addCourseButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new CourseAddView().getView());
            System.out.println("Add course");
        });

        viewDetailsButton.setOnAction(e -> {
            try{
                CourseModule courseModule = coursesTableView.getSelectionModel().getSelectedItem();
                
                if(courseModule == null){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Missing course");
                    alert.setContentText("You didn't select a course!");
                    alert.showAndWait();
                } else{
                    GUI.getLayout().setCenter(new CourseDetailView(courseModule).getView());
                }
               
	            
            }catch(Exception error) {
                error.printStackTrace();
            } 
        });

        removeCourseButton.setOnAction(e -> {
            try {
                CourseModule courseModule = coursesTableView.getSelectionModel().getSelectedItem();

                if (courseModule == null){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Missing course");
                    alert.setContentText("You didn't select a course!");
                    alert.showAndWait();
                } else {
                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Delete");
                    alert.setHeaderText("Are you sure you want to delete this course?");
                    
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        coursesTableView.getItems().remove(courseModule);
                        databaseCourses.deleteCourse(courseModule);
                    }
                }
            }catch(Exception error) {
                System.out.println(error);
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