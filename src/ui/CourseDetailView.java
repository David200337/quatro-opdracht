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
import src.domain.Course;
import src.domain.CourseModule;
import src.domain.EditingCell;

public class CourseDetailView {
    DatabaseCourseModule databaseCoursesModule;
    private ModuleAddView moduleAddView;

    public CourseDetailView() {
        databaseCoursesModule = new DatabaseCourseModule(
                "jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseCoursesModule.loadCourseModules();
        moduleAddView = new ModuleAddView();
    }

    public Parent getView(CourseModule courseModule) {
        // databaseCoursesModule.loadCourseDetails(courseModule);

        VBox layout = new VBox();
        HBox topLayout = new HBox(10);
        Region filler = new Region();

        Label titleLabel = new Label("Course:");
        titleLabel.getStyleClass().add("view-title");
        titleLabel.setPadding(new Insets(10, 10, 10, 15));

        Label lblCourseName = new Label("Course Name: " + courseModule.getCourseName());
        Label lblSubject = new Label("Subject: " + courseModule.getSubject());
        Label lblIntroductionText = new Label("Introduction Text: " + courseModule.getIntroductionText());
        Label lblLevel = new Label("Level: " + courseModule.getLevel());

        TableView<CourseModule> moduleList = new TableView<>();

        moduleList.setEditable(true);
        Callback<TableColumn<CourseModule, String>, TableCell<CourseModule, String>> stringCellFactory = (
                TableColumn<CourseModule, String> param) -> new EditingCell<CourseModule>();

        Button addModuleButton = new Button("Add module");
        Button removeModuleButton = new Button("Remove");

        addModuleButton.setOnAction(e -> {
            layout.getChildren().clear();
            try {
                layout.getChildren().add(moduleAddView.getView(courseModule));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        removeModuleButton.setOnAction((event) -> {
            try{
                CourseModule module = moduleList.getSelectionModel().getSelectedItem();
                moduleList.getItems().remove(module);
                databaseCoursesModule.deleteCourse(module);
            }catch(Exception e) {
                e.printStackTrace();
            } 
        });

        TableColumn<CourseModule, String> titleCol = new TableColumn<>("Module title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("moduleTitle"));
        titleCol.setCellFactory(stringCellFactory);
        titleCol.setOnEditCommit((TableColumn.CellEditEvent<CourseModule, String> t) -> {
            ((CourseModule) t.getTableView().getItems().get(t.getTablePosition().getRow())).setModuleTitle(t.getNewValue());

            CourseModule course = t.getRowValue();
            course.setModuleTitle(t.getNewValue());
            databaseCoursesModule.updateCourseModuleString("Title", t.getNewValue(), course.getContentId());
        });

        TableColumn<CourseModule, String> themeCol = new TableColumn<>("Module Theme");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("moduleTheme"));
        titleCol.setCellFactory(stringCellFactory);
        titleCol.setOnEditCommit((TableColumn.CellEditEvent<CourseModule, String> t) -> {
            ((CourseModule) t.getTableView().getItems().get(t.getTablePosition().getRow())).setModuleTheme(t.getNewValue());

            CourseModule course = t.getRowValue();
            course.setModuleTheme(t.getNewValue());
            databaseCoursesModule.updateCourseModuleString("Theme", t.getNewValue(), course.getContentId());
        });

        TableColumn<CourseModule, String> descriptionCol = new TableColumn<>("Module Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("moduleDescription"));
        descriptionCol.setCellFactory(stringCellFactory);
        descriptionCol.setOnEditCommit((TableColumn.CellEditEvent<CourseModule, String> t) -> {
            ((CourseModule) t.getTableView().getItems().get(t.getTablePosition().getRow())).setModuleDescription(t.getNewValue());

            CourseModule course = t.getRowValue();
            course.setModuleDescription(t.getNewValue());
            databaseCoursesModule.updateCourseModuleString("Description", t.getNewValue(), course.getContentId());
        });

        TableColumn<CourseModule, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        //Editen met combobox

        TableColumn<CourseModule, Integer> serialNumberCol = new TableColumn<>("Module serialnumber");
        serialNumberCol.setCellValueFactory(new PropertyValueFactory<>("moduleSerialNumber"));
        //Mag niet veranderd worden

        TableColumn<CourseModule, String> creatorCol = new TableColumn<>("Module creator");
        serialNumberCol.setCellValueFactory(new PropertyValueFactory<>("creatorName"));

        moduleList.setItems(databaseCoursesModule.getCourseModules());
        moduleList.getColumns().addAll(themeCol, titleCol, descriptionCol, statusCol, serialNumberCol);


        // Add layout
        HBox.setHgrow(filler, Priority.ALWAYS);
        topLayout.getChildren().addAll(titleLabel, filler, addModuleButton, removeModuleButton);
        layout.getChildren().addAll(topLayout, lblCourseName, lblSubject, lblIntroductionText, lblLevel, moduleList);
        layout.setPadding(new Insets(10, 10, 10, 15));



        return layout;

    }
}
