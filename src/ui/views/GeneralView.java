package src.ui.views;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import src.db.DatabaseWebcast;
import src.domain.Webcast;

public class GeneralView {
    private Label viewTitleLabel;
    private Label welcomeLabel;
    private VBox layout;
    private DatabaseWebcast databaseWebcast;
    private TableView<Webcast> webcastTop3TableView;
    private TableColumn<Webcast, String> titleColumn;
    private Label webcastTableTitle;

    public GeneralView() {
        viewTitleLabel = new Label("General");
        welcomeLabel = new Label("Hello World!");
        layout = new VBox(10);

        databaseWebcast = new DatabaseWebcast("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseWebcast.loadWebcastTop3();

        webcastTableTitle = new Label("Webcast Top 3:");
        webcastTop3TableView = new TableView<Webcast>();
        webcastTop3TableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        

        titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));


        webcastTop3TableView.setItems(databaseWebcast.getWebcasts());
        webcastTop3TableView.getColumns().addAll(titleColumn);
    }

    private void configureNodes() {
        viewTitleLabel.getStyleClass().add("view-title");
        webcastTableTitle.getStyleClass().add("view-title");
        
        
    }

    private void configureLayout() {
        layout.setPadding(new Insets(10, 10, 10, 15));
        layout.getChildren().addAll(viewTitleLabel, welcomeLabel, webcastTableTitle, webcastTop3TableView);
    }

    public Pane getView() {
        configureNodes();
        configureLayout();

        return layout;
    }
}
