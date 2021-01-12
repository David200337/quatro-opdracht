package src.ui;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import src.db.DatabaseStudent;
import src.domain.DatePickerConverter;
import src.validators.EmailValidator;
import src.validators.PostalCodeValidator;

public class UsersAddView {
    private DatabaseStudent databaseStudent;


    public UsersAddView() {
        databaseStudent = new DatabaseStudent("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseStudent.loadStudents();

    }

    public Parent getView() {
        VBox layout = new VBox();

        Label titleLabel = new Label("Add user");
        titleLabel.getStyleClass().add("view-title");
        titleLabel.setPadding(new Insets(0, 0, 10, 0));

        //Insert name
        Label lblName = new Label("Name");
        TextField name = new TextField();
        //Insert email
        Label lblEmail = new Label("E-Mail");
        TextField email = new TextField();

        //Insert date of birth
        String pattern = "yyyy-MM-dd";
        DatePicker datePicker = new DatePicker();
        //Create the DateConverter
        DatePickerConverter converter = new DatePickerConverter(pattern);
        //Add the converter to the DatePicker
        datePicker.setConverter(converter);
        //Set the date in the prompt
        datePicker.setPromptText(pattern.toLowerCase());

        //Create a day cell factory
        Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>(){
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

        //Set the day cell factory to the DatePicker
        datePicker.setDayCellFactory(dayCellFactory);
        Label lblDateOfBirth = new Label("Date of birth");
        //Create the HBox for the DatePicker
        HBox pickerBox = new HBox(lblDateOfBirth, datePicker);


        Label lblGender = new Label("Gender");
        //Combobox for gender
        ComboBox<String> genderComboBox = new ComboBox<String>(FXCollections.observableArrayList("Female", "Male"));
        
        //Insert address
        Label lblAddress = new Label("Address");
        TextField address = new TextField();
        //Insert postal code
        Label lblPostalCode = new Label("Postal Code");
        TextField postalCode = new TextField();
        //Insert city
        Label lblCity = new Label("City");
        TextField city = new TextField();
        //Insert country
        Label lblCountry = new Label("Country");
        TextField country = new TextField();
        //Insert button
        Button btnInsert = new Button("Insert");

        layout.getChildren().addAll(titleLabel, lblName, name, lblEmail, email, pickerBox, lblGender, genderComboBox, lblAddress, address, lblPostalCode, postalCode, lblCity, city, lblCountry, country, btnInsert);
        //Insert button action
        btnInsert.setOnAction((event) -> {
            try{ 
                //Set date of birth
                LocalDate dateOfBirth = datePicker.getValue();

                //Check input
                if(name.getText().isEmpty() || genderComboBox.getValue().isEmpty() || address.getText().isEmpty() || city.getText().isEmpty() || country.getText().isEmpty()){
                    Alert missingAlert = new Alert(AlertType.ERROR);
                    missingAlert.setTitle("Error");
                    missingAlert.setHeaderText("Missing field error");
                    missingAlert.setContentText("You didn't fill in all the necessary fields!");
                    missingAlert.showAndWait();
                }else if(email.getText().isEmpty() || !EmailValidator.isValid(email.getText())){
                    Alert emailAlert = new Alert(AlertType.ERROR);
                    emailAlert.setTitle("Error");
                    emailAlert.setHeaderText("Error in the email field");
                    emailAlert.setContentText("Your email address is invalid");
                    emailAlert.showAndWait();
                } else if(datePicker.getValue() == null || dateOfBirth.isAfter(LocalDate.now())) {
                    Alert dateAlert = new Alert(AlertType.ERROR);
                    dateAlert.setTitle("Error");
                    dateAlert.setHeaderText("Error in the date picker");
                    dateAlert.setContentText("Your date of birth is invalid");
                    dateAlert.showAndWait();
                } else if (postalCode.getText().isEmpty() || !PostalCodeValidator.isValid(postalCode.getText())){
                    Alert postalCodeAlert = new Alert(AlertType.ERROR);
                    postalCodeAlert.setTitle("Error");
                    postalCodeAlert.setHeaderText("Error in the postal code field");
                    postalCodeAlert.setContentText("Your postal code is invalid");
                    postalCodeAlert.showAndWait();
                } else{
                    databaseStudent.insertStudent(name.getText(), email.getText(), Date.valueOf(dateOfBirth), String.valueOf(genderComboBox.getValue()), address.getText(), postalCode.getText(), city.getText(), country.getText());
                
                    name.clear();
                    email.clear();
                    datePicker.setValue(null);
                    genderComboBox.setValue(null);
                    address.clear();
                    postalCode.clear();
                    city.clear();
                    country.clear();

                    Alert succesAlert = new Alert(AlertType.CONFIRMATION);
                    succesAlert.setTitle("Registrated");
                    succesAlert.setHeaderText("Success!");
                    succesAlert.setContentText("The student is added!");
                    succesAlert.showAndWait();
                    
                }

            } catch(Exception e) {
                e.printStackTrace();
            }
        });

        return layout;
    }
}
