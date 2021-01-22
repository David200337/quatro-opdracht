package src.ui.views;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GeneralView {
    private Label viewTitleLabel;
    private Label welcomeLabel;
    private VBox layout;

    public GeneralView() {
        viewTitleLabel = new Label("General");
        welcomeLabel = new Label("Hello World!");
        layout = new VBox(10);
        databaseWebcast = new DatabaseWebcast("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseWebcast.loadWebcastTop3();

        webcastTop3TableView = new TableView<>();
        titleColumn = new TableColumn<>("Title");
    }

    private void configureNodes() {
        viewTitleLabel.getStyleClass().add("view-title");
    }

    private void configureLayout() {
        layout.setPadding(new Insets(10, 10, 10, 15));
        layout.getChildren().addAll(viewTitleLabel, welcomeLabel);
    }

    public Pane getView() {
        configureNodes();
        configureLayout();

        return layout;
    }
}
