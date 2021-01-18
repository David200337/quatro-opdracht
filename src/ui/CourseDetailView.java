package src.ui;

import java.util.Optional;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import src.db.DatabaseCourseModule;
import src.domain.CourseModule;
import src.domain.EditingCell;
import src.domain.Status;

public class CourseDetailView {
    DatabaseCourseModule databaseCoursesModule;
    private ModuleAddView moduleAddView;
    private RecommendedCourseAddView recommendedCourseAddView;
    TableView<CourseModule> recommendedCourseList;

    public CourseDetailView() {
        databaseCoursesModule = new DatabaseCourseModule(
                "jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseCoursesModule.loadCourseModules();
        moduleAddView = new ModuleAddView();
        recommendedCourseAddView = new RecommendedCourseAddView();

    }

    public Parent getView(CourseModule courseModule) {
        databaseCoursesModule.loadRecommendedCourses(courseModule);

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
        

        Button addModuleButton = new Button("Add");
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

                if(courseModule == null){
                    Alert missingAlert = new Alert(AlertType.ERROR);
                    missingAlert.setTitle("Error");
                    missingAlert.setHeaderText("Missing module");
                    missingAlert.setContentText("You didn't select a module!");
                    missingAlert.showAndWait();
                } else{
                    Alert removeAlert = new Alert(AlertType.CONFIRMATION);
                    removeAlert.setTitle("Delete");
                    removeAlert.setHeaderText("Are you sure you want to delete this module?");
                    removeAlert.setContentText("");

                    Optional<ButtonType> result = removeAlert.showAndWait();
                    if(!result.isPresent() || result.get() != ButtonType.OK) {
                        
                    } else {
                        
                        moduleList.getItems().remove(module);
                        databaseCoursesModule.deleteCourse(module);
                    }
                }
            }catch(Exception e) {
                e.printStackTrace();
            } 
        });

        TableColumn<CourseModule, String> contentIdCol = new TableColumn<>("ID");
        contentIdCol.setCellValueFactory(new PropertyValueFactory<>("contentId"));

        TableColumn<CourseModule, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("moduleTitle"));
        titleCol.setCellFactory(stringCellFactory);
        titleCol.setOnEditCommit((TableColumn.CellEditEvent<CourseModule, String> t) -> {
            ((CourseModule) t.getTableView().getItems().get(t.getTablePosition().getRow())).setModuleTitle(t.getNewValue());

            CourseModule course = t.getRowValue();
            course.setModuleTitle(t.getNewValue());
            databaseCoursesModule.updateCourseModuleString("Title", t.getNewValue(), course.getContentId());
        });

        
        TableColumn<CourseModule, String> versionCol = new TableColumn<>("Version");
        versionCol.setCellValueFactory(new PropertyValueFactory<>("moduleVersion"));


        TableColumn<CourseModule, String> themeCol = new TableColumn<>("Theme");
        themeCol.setCellValueFactory(new PropertyValueFactory<>("moduleTheme"));
        themeCol.setCellFactory(stringCellFactory);
        themeCol.setOnEditCommit((TableColumn.CellEditEvent<CourseModule, String> t) -> {
            ((CourseModule) t.getTableView().getItems().get(t.getTablePosition().getRow())).setModuleTheme(t.getNewValue());

            CourseModule course = t.getRowValue();
            course.setModuleTheme(t.getNewValue());
            databaseCoursesModule.updateCourseModuleString("Theme", t.getNewValue(), course.getContentId());
        });

        TableColumn<CourseModule, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("moduleDescription"));
        descriptionCol.setCellFactory(stringCellFactory);
        descriptionCol.setOnEditCommit((TableColumn.CellEditEvent<CourseModule, String> t) -> {
            ((CourseModule) t.getTableView().getItems().get(t.getTablePosition().getRow())).setModuleDescription(t.getNewValue());

            CourseModule course = t.getRowValue();
            course.setModuleDescription(t.getNewValue());
            databaseCoursesModule.updateCourseModuleString("Description", t.getNewValue(), course.getContentId());
        });

        ObservableList<Status> listStatus = FXCollections.observableArrayList(Status.values());
        TableColumn<CourseModule, Status> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        statusCol.setCellValueFactory(new Callback<CellDataFeatures<CourseModule, Status>, ObservableValue<Status>>() {
            @Override

            public ObservableValue<Status> call(CellDataFeatures<CourseModule, Status> param) {
                CourseModule course = param.getValue();

                Object statusCode = course.getStatus();

                Status status = Status.getByCode(statusCode);

                return new SimpleObjectProperty<Status>(status);
            }

        });

        statusCol.setCellFactory(ComboBoxTableCell.forTableColumn(listStatus));
        statusCol.setOnEditCommit((CellEditEvent<CourseModule, Status> event) -> {
            TablePosition<CourseModule, Status> pos = event.getTablePosition();

            Status newStatus = event.getNewValue();

            int row = pos.getRow();
            CourseModule module = event.getTableView().getItems().get(row);

            databaseCoursesModule.updateCourseModuleObject("Status", newStatus, module.getContentId());

            module.setStatus(newStatus.getCode());
        });
        //Editen met combobox

        TableColumn<CourseModule, Integer> serialNumberCol = new TableColumn<>("Serialnumber");
        serialNumberCol.setCellValueFactory(new PropertyValueFactory<>("moduleSerialNumber"));
        //Mag niet veranderd worden

        TableColumn<CourseModule, String> creatorCol = new TableColumn<>("Creator");
        creatorCol.setCellValueFactory(new PropertyValueFactory<>("creatorName"));

        moduleList.setItems(databaseCoursesModule.getCourseModules());
        moduleList.getColumns().addAll(contentIdCol, titleCol, versionCol, themeCol, descriptionCol, statusCol, serialNumberCol, creatorCol);
        
        
        HBox recommendedSection = new HBox(10);
        
        Button addRecommendedCourse = new Button("Add recommendation");
        addRecommendedCourse.setOnAction(e -> {
            layout.getChildren().clear();
            try {
                layout.getChildren().add(recommendedCourseAddView.getView(courseModule));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        Button removeRecommendation = new Button("Remove");
        removeRecommendation.setOnAction((event) -> {
            try{
                CourseModule recommendation = recommendedCourseList.getSelectionModel().getSelectedItem();

                if(courseModule == null){
                    Alert missingAlert = new Alert(AlertType.ERROR);
                    missingAlert.setTitle("Error");
                    missingAlert.setHeaderText("Missing course");
                    missingAlert.setContentText("You didn't select a course!");
                    missingAlert.showAndWait();
                } else{
                    Alert removeAlert = new Alert(AlertType.CONFIRMATION);
                    removeAlert.setTitle("Delete");
                    removeAlert.setHeaderText("Are you sure you want to delete this recommendation?");
                    removeAlert.setContentText("");
                    // removeAlert.showAndWait();

                    Optional<ButtonType> result = removeAlert.showAndWait();
                    if(!result.isPresent() || result.get() != ButtonType.OK) {
                        
                    } else {
                        
                        recommendedCourseList.getItems().remove(recommendation);
                        databaseCoursesModule.deleteRecommendation(recommendation);
                    }
                }
            }catch(Exception e) {
                e.printStackTrace();
            } 
        });

        Label lblRecommendedTable = new Label("Recommended");
        lblRecommendedTable.getStyleClass().add("view-title");
        lblRecommendedTable.setPadding(new Insets(10, 10, 10, 15));
        recommendedCourseList = new TableView<>();
                
        TableColumn<CourseModule, String> courseNameCol = new TableColumn<>("Course Name");
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));

        recommendedCourseList.setItems(databaseCoursesModule.getRecommendedCourses());
        recommendedCourseList.getColumns().addAll(courseNameCol);


    
        // Add layout
        HBox.setHgrow(filler, Priority.ALWAYS);
        recommendedSection.getChildren().addAll(lblRecommendedTable, filler, addRecommendedCourse, removeRecommendation);
        topLayout.getChildren().addAll(titleLabel, filler, addModuleButton, removeModuleButton);
        layout.getChildren().addAll(topLayout, lblCourseName, lblSubject, lblIntroductionText, lblLevel, moduleList, recommendedSection, recommendedCourseList);
        layout.setPadding(new Insets(10, 10, 10, 15));


       

        return layout;

    }
}
