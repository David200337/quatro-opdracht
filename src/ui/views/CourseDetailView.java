package src.ui.views;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
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
import src.ui.GUI;

public class CourseDetailView {
    private CourseModule courseModule;
    private DatabaseCourseModule databaseCourseModule;
    private Label viewTitleLabel;
    private Button backButton;
    private Label subjectLabel;
    private Label introductionTextLabel;
    private Label levelLabel;
    private Label modulesTitleLabel;
    private Button addModuleButton;
    private Button deleteModuleButton;
    private TableView<CourseModule> modulesCourseTableView;
    private Callback<TableColumn<CourseModule, String>, TableCell<CourseModule, String>> stringCellFactory;
    private TableColumn<CourseModule, String> contentIdCol;
    private TableColumn<CourseModule, String> titleCol;
    private TableColumn<CourseModule, String> versionCol;
    private TableColumn<CourseModule, String> themeCol;
    private TableColumn<CourseModule, String> descriptionCol;
    private ObservableList<Status> listStatus;
    private TableColumn<CourseModule, Status> statusCol;
    private TableColumn<CourseModule, Integer> serialNumberCol;
    private TableColumn<CourseModule, String> creatorCol;
    private Label recommendationsTitleLabel;
    private Button addRecommendationButton;
    private Button deleteRecommendationButton;
    private TableColumn<CourseModule, String> courseNameCol;
    private TableView<CourseModule> recommendedModulesCourseTableView;
    private Region region;
    private HBox topLayout;
    private Region modulesRegion;
    private HBox modulesLayout;
    private Region recommendationsRegion;
    private HBox recommendationsLayout;
    private VBox layout;
    
    public CourseDetailView(CourseModule courseModule) {
        this.courseModule = courseModule;
        databaseCourseModule = new DatabaseCourseModule("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        
        viewTitleLabel = new Label(courseModule.getCourseName());
        backButton = new Button("Back");
        subjectLabel = new Label("Subject: " + courseModule.getSubject());
        introductionTextLabel = new Label("Introduction Text: " + courseModule.getIntroductionText());
        levelLabel = new Label("Level: " + courseModule.getLevel());
        
        modulesTitleLabel = new Label("Modules");
        addModuleButton = new Button("Add");
        deleteModuleButton = new Button("Delete");

        recommendationsTitleLabel = new Label("Recommendations");
        addRecommendationButton = new Button("Add");
        deleteRecommendationButton = new Button("Delete");

        modulesCourseTableView = new TableView<>();
        stringCellFactory = (TableColumn<CourseModule, String> param) -> new EditingCell<CourseModule>();
        contentIdCol = new TableColumn<>("ID");
        titleCol = new TableColumn<>("Title");
        versionCol = new TableColumn<>("Version");
        themeCol = new TableColumn<>("Theme");
        descriptionCol = new TableColumn<>("Description");
        listStatus = FXCollections.observableArrayList(Status.values());
        statusCol = new TableColumn<>("Status");
        serialNumberCol = new TableColumn<>("Serialnumber");
        creatorCol = new TableColumn<>("Creator");
        
        recommendedModulesCourseTableView = new TableView<>();
        courseNameCol = new TableColumn<>("Course Name");

        region = new Region();
        topLayout = new HBox(10);
        modulesRegion = new Region();
        modulesLayout = new HBox(10);
        recommendationsRegion = new Region();
        recommendationsLayout = new HBox(10);
        layout = new VBox(10);
    }

    private void configureNodes() {
        viewTitleLabel.getStyleClass().add("view-title");
        
        modulesTitleLabel.getStyleClass().add("view-title");

        contentIdCol.setCellValueFactory(new PropertyValueFactory<>("contentId"));

        titleCol.setCellValueFactory(new PropertyValueFactory<>("moduleTitle"));
        titleCol.setCellFactory(stringCellFactory);
        titleCol.setOnEditCommit((TableColumn.CellEditEvent<CourseModule, String> t) -> {
            ((CourseModule) t.getTableView().getItems().get(t.getTablePosition().getRow())).setModuleTitle(t.getNewValue());

            CourseModule course = t.getRowValue();
            course.setModuleTitle(t.getNewValue());
            databaseCourseModule.updateCourseModuleString("Title", t.getNewValue(), course.getContentId());
        });

        versionCol.setCellValueFactory(new PropertyValueFactory<>("moduleVersion"));

        themeCol.setCellValueFactory(new PropertyValueFactory<>("moduleTheme"));
        themeCol.setCellFactory(stringCellFactory);
        themeCol.setOnEditCommit((TableColumn.CellEditEvent<CourseModule, String> t) -> {
            ((CourseModule) t.getTableView().getItems().get(t.getTablePosition().getRow())).setModuleTheme(t.getNewValue());

            CourseModule course = t.getRowValue();
            course.setModuleTheme(t.getNewValue());
            databaseCourseModule.updateCourseModuleString("Theme", t.getNewValue(), course.getContentId());
        });

        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("moduleDescription"));
        descriptionCol.setCellFactory(stringCellFactory);
        descriptionCol.setOnEditCommit((TableColumn.CellEditEvent<CourseModule, String> t) -> {
            ((CourseModule) t.getTableView().getItems().get(t.getTablePosition().getRow())).setModuleDescription(t.getNewValue());

            CourseModule course = t.getRowValue();
            course.setModuleDescription(t.getNewValue());
            databaseCourseModule.updateCourseModuleString("Description", t.getNewValue(), course.getContentId());
        });

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

            databaseCourseModule.updateCourseModuleObject("Status", newStatus, module.getContentId());

            module.setStatus(newStatus.getCode());
        });

        serialNumberCol.setCellValueFactory(new PropertyValueFactory<>("moduleSerialNumber"));
        creatorCol.setCellValueFactory(new PropertyValueFactory<>("creatorName"));

        modulesCourseTableView.setItems(databaseCourseModule.getCourseModules());
        modulesCourseTableView.getColumns().addAll(contentIdCol, titleCol, versionCol, themeCol, descriptionCol, statusCol, serialNumberCol, creatorCol);

        recommendationsTitleLabel.getStyleClass().add("view-title");

        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));

        recommendedModulesCourseTableView.setItems(databaseCourseModule.getRecommendedCourses());
        recommendedModulesCourseTableView.getColumns().addAll(courseNameCol);
    }

    private void configureLayout() {
        HBox.setHgrow(region, Priority.ALWAYS);
        topLayout.getChildren().addAll(viewTitleLabel, region, backButton);

        HBox.setHgrow(modulesRegion, Priority.ALWAYS);
        modulesLayout.getChildren().addAll(modulesTitleLabel, modulesRegion, addModuleButton, deleteModuleButton);
        
        HBox.setHgrow(recommendationsRegion, Priority.ALWAYS);
        recommendationsLayout.getChildren().addAll(recommendationsTitleLabel, recommendationsRegion, addRecommendationButton, deleteRecommendationButton);

        layout.setPadding(new Insets(10, 10, 10, 15));
        layout.getChildren().addAll(topLayout, subjectLabel, introductionTextLabel, levelLabel, modulesLayout, modulesCourseTableView, recommendationsLayout, recommendedModulesCourseTableView);
    }

    private void handleActions() {
        backButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new CoursesView().getView());
        });

        addModuleButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new ModuleAddView(courseModule).getView());
        });

        addRecommendationButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new RecommendedCourseAddView(courseModule).getView());
        });
    }

    public VBox getView() {
        configureNodes();
        configureLayout();
        handleActions();

        return layout;
    }
}
