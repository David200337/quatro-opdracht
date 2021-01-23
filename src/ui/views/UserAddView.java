package src.ui.views;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import src.db.DatabaseStudent;
import src.domain.DatePickerConverter;
import src.ui.GUI;
import src.validators.EmailValidator;
import src.validators.PostalCodeValidator;

public class UserAddView {
    private DatabaseStudent databaseStudent;
    private Label viewTitleLabel;
    private Button backButton;
    private Label nameLabel;
    private TextField nameTextField;
    private Label emailLabel;
    private TextField emailTextField;
    private Label dateOfBirthLabel;
    private DatePicker datePicker;
    private DatePickerConverter datePickerConverter;
    private Callback<DatePicker, DateCell> dayCellFactory;
    private Label genderLabel;
    private ComboBox<String> genderComboBox;
    private Label addressLabel;
    private TextField addressTextField;
    private Label postalCodeLabel;
    private TextField postalCodeTextField;
    private Label cityLabel;
    private TextField cityTextField;
    private Label countryLabel;
    private TextField countryTextField;
    private Button insertButton;
    private Alert alert;
    private Region region;
    private HBox topLayout;
    private VBox layout;

    public UserAddView() {
        databaseStudent = new DatabaseStudent("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseStudent.loadStudents();

        viewTitleLabel = new Label("Add");
        backButton = new Button("Back");

        nameLabel = new Label("Name");
        nameTextField = new TextField();
        emailLabel = new Label("Email");
        emailTextField = new TextField();
        dateOfBirthLabel = new Label("Date of birth");
        datePicker = new DatePicker();
        datePickerConverter = new DatePickerConverter("yyyy-MM-dd");
        dayCellFactory = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker){
                return new DateCell(){
                    @Override
                    public void updateItem(LocalDate item, boolean empty){
                        super.updateItem(item, empty);

                        //Show weekends in blue color
                        DayOfWeek day = DayOfWeek.from(item);
                        if(day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY){
                            this.setTextFill(Color.BLUE);
                        }
                    }
                };
            }
        };
        genderLabel = new Label("Gender");
        genderComboBox = new ComboBox<String>(FXCollections.observableArrayList("Female", "Male"));
        addressLabel = new Label("Address");
        addressTextField = new TextField();
        postalCodeLabel = new Label("Postal code");
        postalCodeTextField = new TextField();
        cityLabel = new Label("City");
        cityTextField = new TextField();
        countryLabel = new Label("Country");
        countryTextField = new TextField();
        insertButton = new Button("Insert");

        region = new Region();
        topLayout = new HBox(10);
        layout = new VBox(10);
    }

    private void configureNodes() {
        viewTitleLabel.getStyleClass().add("view-title");

        datePicker.setConverter(datePickerConverter);
        datePicker.setPromptText("yyyy-MM-dd".toLowerCase());
        datePicker.setDayCellFactory(dayCellFactory);
    }

    private void configureLayout() {
        HBox.setHgrow(region, Priority.ALWAYS);
        topLayout.getChildren().addAll(viewTitleLabel, region, backButton);

        layout.setPadding(new Insets(10, 10, 10, 15));
        layout.getChildren().addAll(topLayout, nameLabel, nameTextField, emailLabel, emailTextField, dateOfBirthLabel, datePicker, genderLabel, genderComboBox, addressLabel, addressTextField, postalCodeLabel, postalCodeTextField, cityLabel, cityTextField, countryLabel, countryTextField, insertButton);
    }

    private void handleActions() {
        backButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new UsersView().getView());
        });

        insertButton.setOnAction(e -> {
            try {
                // Set date of birth
                LocalDate dateOfBirth = datePicker.getValue();

                //Check input
                if(nameTextField.getText().isEmpty() || genderComboBox.getValue().isEmpty() || addressTextField.getText().isEmpty() || cityTextField.getText().isEmpty() || countryTextField.getText().isEmpty()){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Missing field error");
                    alert.setContentText("You didn't fill in all the necessary fields!");
                    alert.showAndWait();
                }else if(emailTextField.getText().isEmpty() || !EmailValidator.isValid(emailTextField.getText())){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error in the email field");
                    alert.setContentText("Your email address is invalid");
                    alert.showAndWait();
                } else if(datePicker.getValue() == null || dateOfBirth.isAfter(LocalDate.now())) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error in the date picker");
                    alert.setContentText("Your date of birth is invalid");
                    alert.showAndWait();
                } else if (postalCodeTextField.getText().isEmpty() || !PostalCodeValidator.isValid(postalCodeTextField.getText())){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error in the postal code field");
                    alert.setContentText("Your postal code is invalid");
                    alert.showAndWait();
                } else{
                    databaseStudent.insertStudent(nameTextField.getText(), emailTextField.getText(), Date.valueOf(dateOfBirth), String.valueOf(genderComboBox.getValue()), addressTextField.getText(), postalCodeTextField.getText(), cityTextField.getText(), countryTextField.getText());
                
                    nameTextField.clear();
                    emailTextField.clear();
                    datePicker.setValue(null);
                    genderComboBox.setValue(null);
                    addressTextField.clear();
                    postalCodeTextField.clear();
                    cityTextField.clear();
                    countryTextField.clear();

                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Registrated");
                    alert.setHeaderText("Success!");
                    alert.setContentText("The student is added!");
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

    public VBox getView() {
        configureNodes();
        configureLayout();
        handleActions();

        return layout;
    }
}
