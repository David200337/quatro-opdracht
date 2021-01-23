package src.ui.views;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import src.db.DatabaseContentCreator;
import src.ui.GUI;
import src.validators.EmailValidator;

public class CreatorAddView {
private DatabaseContentCreator databaseCreators;
private Label viewTitleLabel;
private Button backButton;
private Label nameLabel;
private TextField nameTextField;
private Label emailLabel;
private TextField emailTextField;
private Label organisationLabel;
private TextField organisationTextField;
private Button insertButton;
private Region region;
private HBox topLayout;
private VBox layout;
private Alert alert;



    public CreatorAddView(){
        databaseCreators = new DatabaseContentCreator("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseCreators.loadCreators();

        viewTitleLabel = new Label("Add creator");
        backButton = new Button("Back");
        nameLabel = new Label("Name");
        nameTextField = new TextField();
        emailLabel = new Label("E-Mail");
        emailTextField = new TextField();
        organisationLabel = new Label("Organisation");
        organisationTextField = new TextField();
        insertButton = new Button("Insert");

        region = new Region();
        topLayout = new HBox(10);
        layout = new VBox(10);
    }

    protected void configureNodes() {
        viewTitleLabel.getStyleClass().add("view-title");
    }

    protected void configureLayout() {
        HBox.setHgrow(region, Priority.ALWAYS);
        topLayout.getChildren().addAll(viewTitleLabel, region, backButton);
        
        layout.setPadding(new Insets(10, 10, 10, 15));
        layout.getChildren().addAll(topLayout, nameLabel, nameTextField, emailLabel, emailTextField, organisationLabel, organisationTextField, insertButton);
    }

    protected void handleActions() {        
        backButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new ContentCreatorView().getView());
        });

        insertButton.setOnAction(e -> {
            try {
                if (nameTextField.getText().isEmpty() || organisationTextField.getText().isEmpty()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Missing field error");
                    alert.setContentText("You didn't fill in all the necessary fields!");
                    alert.showAndWait();
                } else if(emailTextField.getText().isEmpty() || !EmailValidator.isValid(emailTextField.getText())){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error in the email field");
                    alert.setContentText("Your email address is invalid");
                    alert.showAndWait();
                } else {
                    databaseCreators.insertCreator(nameTextField.getText(), emailTextField.getText(), organisationTextField.getText());

                    nameTextField.clear();
                    emailTextField.clear();
                    organisationTextField.clear();
                    

                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Registrated");
                    alert.setHeaderText("Success!");
                    alert.setContentText("The creator is added!");
                    alert.showAndWait();
                }
            } catch (Exception error) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Connection Error");
                alert.setHeaderText("Connection failed.");
                alert.setContentText("Please check your internet connection, or try again later.");
                alert.showAndWait();
            }
        });
    }

    public Pane getView() {
        configureNodes();
        configureLayout();
        handleActions();

        return layout;
    }
}
