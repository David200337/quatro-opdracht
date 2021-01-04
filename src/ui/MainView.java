package src.ui;

import src.domain.PersistentButtonToggleGroup;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainView extends Application {
    public static void main(String[] args) {
        launch(MainView.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Instantiate subviews
        GeneralView generalView = new GeneralView();
        CoursesView coursesView = new CoursesView();
        UsersView usersView = new UsersView();
        
        // Instantiate higher level layout
        BorderPane layout = new BorderPane();

        // Instantiate menu and setting up
        VBox menu = new VBox(5);
        menu.getStyleClass().add("menu");
        menu.setPadding(new Insets(10));
        menu.setMaxWidth(150);

        // Instantiate Codecademy logo image
        Image codecademyLogo = new Image("src/resources/codecademy-logo.png");
        ImageView codecademyLogoImageView = new ImageView(codecademyLogo);
        codecademyLogoImageView.setPreserveRatio(true);
        codecademyLogoImageView.setFitWidth(150);
        codecademyLogoImageView.setTranslateY(-25);
        codecademyLogoImageView.setTranslateX(-15);

        // Instantiate button group and buttons
        final PersistentButtonToggleGroup menuGroup = new PersistentButtonToggleGroup();

        ToggleButton generalMenuButton = new ToggleButton("General");
        generalMenuButton.setToggleGroup(menuGroup);
        generalMenuButton.setSelected(true);
        generalMenuButton.getStyleClass().add("menu-button");

        ToggleButton coursesMenuButton = new ToggleButton("Courses");
        coursesMenuButton.setToggleGroup(menuGroup);
        coursesMenuButton.getStyleClass().add("menu-button");

        ToggleButton usersMenuButton = new ToggleButton("Users");
        usersMenuButton.setToggleGroup(menuGroup);
        usersMenuButton.getStyleClass().add("menu-button");

        // Add elements to menu layout and set some padding
        menu.getChildren().addAll(codecademyLogoImageView, generalMenuButton, coursesMenuButton, usersMenuButton);
        menu.setPadding(new Insets(0, 0, 0, 15));

        // Add menu to layout
        layout.setLeft(menu);

        // Change view when menu button is pressed
        generalMenuButton.setOnAction(e -> {
            layout.setCenter(generalView.getView());
        });

        coursesMenuButton.setOnAction(e -> {
            layout.setCenter(coursesView.getView());
        });

        usersMenuButton.setOnAction(e -> {
            layout.setCenter(usersView.getView());
        });

        // Set initial view to center of BorderPane
        layout.setCenter(generalView.getView());

        // Instantiate scene and add stylesheets
        Scene scene = new Scene(layout, 800, 600);
        scene.getStylesheets().add("/src/ui/style.css");
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&display=swap");
        
        // Set scene and show stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
