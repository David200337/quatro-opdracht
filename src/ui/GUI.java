package src.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import src.ui.components.MenuComponent;
import src.ui.views.GeneralView;

public class GUI extends Application {
    private static BorderPane layout;

    public GUI() {
        layout = new BorderPane();
    }

    public static void main(String[] args) {
        launch(GUI.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        layout.setLeft(new MenuComponent().getComponent());
        layout.setCenter(new GeneralView().getView());

        Scene scene = new Scene(layout, 800, 600);
        scene.getStylesheets().add("/src/ui/style.css");
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&display=swap");

        primaryStage.setTitle("David van Mourik - 2174508 , Tristan Maltha - 2170581 , Janne Sterk - 2173624");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static BorderPane getLayout() {
        return layout;
    }
}
