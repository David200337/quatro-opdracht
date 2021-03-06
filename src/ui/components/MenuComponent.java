package src.ui.components;

import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import src.domain.PersistentButtonToggleGroup;
import src.ui.GUI;
import src.ui.views.ContentCreatorView;
import src.ui.views.CoursesView;
import src.ui.views.GeneralView;
import src.ui.views.UsersView;
import src.ui.views.WebcastsView;

public class MenuComponent {
    private Image logoImage;
    private ImageView logoImageView;
    private PersistentButtonToggleGroup menuGroup;
    private ToggleButton generalMenuButton;
    private ToggleButton usersMenuButton;
    private ToggleButton coursesMenuButton;
    private ToggleButton webcastsMenuButton;
    private ToggleButton contentCreatorsMenuButton;
    private VBox layout;

    //Initialize the variables
    public MenuComponent() {
        logoImage = new Image("src/resources/codecademy-logo.png");
        logoImageView = new ImageView(logoImage);
        menuGroup = new PersistentButtonToggleGroup();
        generalMenuButton = new ToggleButton("General");
        usersMenuButton = new ToggleButton("Users");
        coursesMenuButton = new ToggleButton("Courses");
        webcastsMenuButton = new ToggleButton("Webcasts");
        contentCreatorsMenuButton = new ToggleButton("Content Creators");
        layout = new VBox();
    }

    //Configure the nodes
    private void configureNodes() {
        logoImageView.setPreserveRatio(true);
        logoImageView.setFitWidth(150);
        logoImageView.setTranslateX(-15);
        logoImageView.setTranslateY(-25);

        generalMenuButton.setToggleGroup(menuGroup);
        generalMenuButton.getStyleClass().add("menu-button");
        generalMenuButton.setSelected(true);

        usersMenuButton.setToggleGroup(menuGroup);
        usersMenuButton.getStyleClass().add("menu-button");
        
        coursesMenuButton.setToggleGroup(menuGroup);
        coursesMenuButton.getStyleClass().add("menu-button");

        webcastsMenuButton.setToggleGroup(menuGroup);
        webcastsMenuButton.getStyleClass().add("menu-button");

        contentCreatorsMenuButton.setToggleGroup(menuGroup);
        contentCreatorsMenuButton.getStyleClass().add("menu-button");
    }

    //Configure the layout
    private void configureLayout() {
        layout.getStyleClass().add("menu");
        layout.setPadding(new Insets(0, 0, 0, 15));
        layout.setMaxWidth(150);
        layout.getChildren().addAll(logoImageView, generalMenuButton, usersMenuButton, coursesMenuButton, webcastsMenuButton, contentCreatorsMenuButton);
    }

    //Initialize the actions of the buttons
    private void switchScene() {
        generalMenuButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new GeneralView().getView());
        });

        usersMenuButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new UsersView().getView());
        });

        coursesMenuButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new CoursesView().getView());
        });

        webcastsMenuButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new WebcastsView().getView());
        });

        contentCreatorsMenuButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new ContentCreatorView().getView());
        });


    }

    //Put everything together and show it
    public VBox getComponent() {
        configureNodes();
        configureLayout();
        switchScene();

        return layout;
    }

}
