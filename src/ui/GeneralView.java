package src.ui;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GeneralView {
    public Parent getView() {
        VBox layout = new VBox();

        Label titleLabel = new Label("General");
        titleLabel.getStyleClass().add("view-title");
        titleLabel.setPadding(new Insets(10, 10, 10, 15));

        layout.getChildren().add(titleLabel);

        return layout;
    }
}
