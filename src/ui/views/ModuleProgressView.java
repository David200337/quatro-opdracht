package src.ui.views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import src.db.DatabaseViewStatistics;
import src.domain.Student;
import src.domain.ViewStatistics;
import src.ui.GUI;

public class ModuleProgressView {
    
    private Student student;
    private DatabaseViewStatistics databaseViewStatistics;
    private Label viewTitleLabel;
    private Button backButton;

    private Label moduleProgressTitleLabel;
    private TableView<ViewStatistics> moduleProgressTableView;
    private TableColumn<ViewStatistics, String> modTitleCol;
    private TableColumn<ViewStatistics, Integer> modVersionNrCol;
    private TableColumn<ViewStatistics, Double> modProgressCol;

    private Label webcastProgressTitleLabel;
    private TableView<ViewStatistics> webcastProgressTableView;
    private TableColumn<ViewStatistics, String> webTitleCol;
    private TableColumn<ViewStatistics, Double> webProgressCol;

    private Region region;
    private HBox topLayout;
    private VBox layout;

    //Initialize the variables
    public ModuleProgressView(Student student) {
        this.student = student;
        databaseViewStatistics = new DatabaseViewStatistics("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseViewStatistics.loadPercentageModule(student.getStudentId());
        databaseViewStatistics.loadPercentageWebcast(student.getStudentId());

        viewTitleLabel = new Label("Student: "+ student.getName());
        backButton = new Button("Back");

        moduleProgressTitleLabel = new Label("Progress (Modules)");
        moduleProgressTableView = new TableView<>();

        modTitleCol = new TableColumn<>("Title");
        modVersionNrCol = new TableColumn<>("VersionNr");
        modProgressCol = new TableColumn<>("Progress");
        
        webcastProgressTitleLabel = new Label("Progress (Webcasts)");
        webcastProgressTableView = new TableView<>();

        webTitleCol = new TableColumn<>("Title");
        webProgressCol = new TableColumn<>("Progress");

        region = new Region();
        topLayout = new HBox(10);
        layout = new VBox(10);
    }

    //Configure the nodes
    private void configureNodes() {
        viewTitleLabel.getStyleClass().add("view-title");

        moduleProgressTitleLabel.getStyleClass().add("view-title");
        modTitleCol.setCellValueFactory(new PropertyValueFactory<>("moduleTitle"));
        modVersionNrCol.setCellValueFactory(new PropertyValueFactory<>("versionNr"));
        modProgressCol.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        modProgressCol.setCellFactory(ProgressBarTableCell.<ViewStatistics> forTableColumn());

        moduleProgressTableView.setItems(databaseViewStatistics.getPercentageModules());
        moduleProgressTableView.getColumns().addAll(modTitleCol, modVersionNrCol, modProgressCol);

        webcastProgressTitleLabel.getStyleClass().add("view-title");
        webTitleCol.setCellValueFactory(new PropertyValueFactory<>("webcastTitle"));
        webProgressCol.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        webProgressCol.setCellFactory(ProgressBarTableCell.<ViewStatistics> forTableColumn());

        webcastProgressTableView.setItems(databaseViewStatistics.getPercentageWebcast());
        webcastProgressTableView.getColumns().addAll(webTitleCol, webProgressCol);

    }

    //Configure the layout
    private void configureLayout() {
        HBox.setHgrow(region, Priority.ALWAYS);
        topLayout.getChildren().addAll(viewTitleLabel, region, backButton);

        layout.setPadding(new Insets(10, 10, 10, 15));
        layout.getChildren().addAll(topLayout, moduleProgressTitleLabel, moduleProgressTableView, webcastProgressTitleLabel, webcastProgressTableView);
    }

    //Initialize the actions of the buttons
    private void handleActions() {
        backButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new UserDetailView(student).getView());
        });
    }

    //Put everything together and show it
    public VBox getView() {
        configureNodes();
        configureLayout();
        handleActions();

        return layout;
    }
}
