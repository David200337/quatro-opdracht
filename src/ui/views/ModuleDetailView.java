package src.ui.views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import src.db.DatabaseCourseModule;
import src.db.DatabaseViewStatistics;
import src.domain.CourseModule;
import src.ui.GUI;

public class ModuleDetailView {
    private CourseModule courseModule;
    private DatabaseCourseModule databaseCourseModule;
    private DatabaseViewStatistics databaseViewStatistics;

    private Button backButton;
    private Label viewTitleLabel;
    private Label titleLabel;
    private Label versionLabel;
    private Label themeLabel;
    private Label descriptionLabel;
    private Label statusLabel;
    private Label serialNumberLabel;
    private Label creatorNameLabel;
    private Label creatorEmailLabel;
    private Label creatorOrganisationLabel;
    private Label nothingLabel;
    private Label progressBarLabel;
    private Label progressBarNumberLabel;

    private ProgressBar moduleProgressBar;

    private Region region;
    private HBox topLayout;
    private VBox layout;

    public ModuleDetailView(CourseModule courseModule) throws Exception {
        this.courseModule = courseModule;
        // databaseCourseModule = new
        // DatabaseCourseModule("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        // databaseCourseModule.loadCourseModules();
        databaseViewStatistics = new DatabaseViewStatistics(
                "jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        // databaseViewStatistics.loadPercentageAllAccounts(courseModule.getContentId());

        viewTitleLabel = new Label(courseModule.getCourseName());
        backButton = new Button("Back");
        titleLabel = new Label("Title: " + courseModule.getModuleTitle());
        versionLabel = new Label("Version: " + courseModule.getModuleVersion());
        themeLabel = new Label("Theme: " + courseModule.getModuleTheme());
        descriptionLabel = new Label("Description: " + courseModule.getModuleDescription());
        statusLabel = new Label("Status: " + courseModule.getStatus());
        serialNumberLabel = new Label("SerialNumber: " + courseModule.getModuleSerialNumber());
        creatorNameLabel = new Label("Creator Name: " + courseModule.getCreatorName());
        creatorEmailLabel = new Label("Creator E-mail: " + courseModule.getCreatorEmail());
        creatorOrganisationLabel = new Label ("Creator Organisation: " + courseModule.getCreatorOrganisation());

        nothingLabel = new Label("");

        progressBarLabel = new Label("Average progress for all accounts:");
        progressBarNumberLabel = new Label (+databaseViewStatistics.getPercentageAllAccounts(courseModule.getContentId()) * 100+"%");
        moduleProgressBar = new ProgressBar(
                databaseViewStatistics.getPercentageAllAccounts(courseModule.getContentId()));


        region = new Region();
        topLayout = new HBox(10);
        layout = new VBox(10);
    }

    private void configureNodes() {
        viewTitleLabel.getStyleClass().add("view-title");
        titleLabel.getStyleClass().add("bold-text");
        versionLabel.getStyleClass().add("bold-text");
        themeLabel.getStyleClass().add("bold-text");
        descriptionLabel.getStyleClass().add("bold-text");
        statusLabel.getStyleClass().add("bold-text");
        serialNumberLabel.getStyleClass().add("bold-text");
        creatorNameLabel.getStyleClass().add("bold-text");
        creatorEmailLabel.getStyleClass().add("bold-text");
        creatorOrganisationLabel.getStyleClass().add("bold-text");
        progressBarLabel.getStyleClass().add("bold-text");

    }

    private void configureLayout() {
        HBox.setHgrow(region, Priority.ALWAYS);
        topLayout.getChildren().addAll(viewTitleLabel, region, backButton);

        layout.setPadding(new Insets(10, 10, 10, 15));
        layout.getChildren().addAll(topLayout, titleLabel, versionLabel, themeLabel, descriptionLabel, statusLabel,
                serialNumberLabel, creatorNameLabel,creatorEmailLabel, creatorOrganisationLabel, nothingLabel, progressBarLabel, moduleProgressBar, progressBarNumberLabel);
    }

    private void handleActions() {
        backButton.setOnAction(e -> {
            try {
                GUI.getLayout().setCenter(new CourseDetailView(courseModule).getView());
            } catch (Exception e1) {
                e1.printStackTrace();
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
