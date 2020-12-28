package src.ui;

import src.db.*;
import src.domain.DatePickerConverter;
import src.domain.Student;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;


public class GUIUsers extends Application {
    private DatabaseStudent databaseStudent;
    private Stage window;
    TableView<Student> studentTable;
    LocalDate dateOfBirth;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Students");
        databaseStudent = new DatabaseStudent("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseStudent.loadStudents();

        // ID Column
        TableColumn<Student, Integer> studentIdColumn = new TableColumn<>("ID");
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));

        // Name Column
        TableColumn<Student, String> studentNameColumn = new TableColumn<>("Name");
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Email Column
        TableColumn<Student, String> studentEmailColumn = new TableColumn<>("Email");
        studentEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Date of birth Column
        TableColumn<Student, String> studentDateOfBirthColumn = new TableColumn<>("Date of birth");
        studentDateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        // Gender Column
        TableColumn<Student, String> studentGenderColumn = new TableColumn<>("Gender");
        studentGenderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        // Address Column
        TableColumn<Student, String> studentAddressColumn = new TableColumn<>("Address");
        studentAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        // Postal Code Column
        TableColumn<Student, String> studentPostalCodeColumn = new TableColumn<>("Postal Code");
        studentPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        // City Column
        TableColumn<Student, String> studentCityColumn = new TableColumn<>("City");
        studentCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));

        // Country Column
        TableColumn<Student, String> studentCountryColumn = new TableColumn<>("Country");
        studentCountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

        


        // Setup table, load data & add columns
        studentTable = new TableView<>();
        studentTable.setItems(databaseStudent.getStudents());
        studentTable.getColumns().addAll(studentIdColumn, studentNameColumn, studentEmailColumn, studentDateOfBirthColumn, studentGenderColumn, studentAddressColumn, studentPostalCodeColumn, studentCityColumn, studentCountryColumn);

        VBox vBox = new VBox();
        // Button refreshButton = new Button("Refresh");
        // refreshButton.setOnAction((event) -> {
        //     refreshTable();
        // });

        //Delete button - removes selected item
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction((event) -> {
            try{
                Student selectedItem = studentTable.getSelectionModel().getSelectedItem();
                studentTable.getItems().remove(selectedItem);
                databaseStudent.deleteStudent(selectedItem);
            }catch(Exception e) {
                e.printStackTrace();
            } 
        });
        vBox.getChildren().addAll(studentTable, deleteButton);







        Label lblTitle = new Label("Insert new student");

        Label lblId = new Label("ID");
        TextField studentId = new TextField();
        
        Label lblName = new Label("Name");
        TextField name = new TextField();

        Label lblEmail = new Label("E-Mail");
        TextField email = new TextField();

        
        String pattern = "yyyy-MM-dd";
        
        DatePicker datePicker = new DatePicker();
        // LocalDate dateOfBirth = null;

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
        //Radio buttons for gender
        ToggleGroup genderGroup = new ToggleGroup();
        
        RadioButton rbMale = new RadioButton("Male");
        rbMale.setToggleGroup(genderGroup);

        RadioButton rbFemale = new RadioButton("Female");
        rbFemale.setToggleGroup(genderGroup);
        

        Label lblAddress = new Label("Address");
        TextField address = new TextField();

        Label lblPostalCode = new Label("Postal Code");
        TextField postalCode = new TextField();

        Label lblCity = new Label("City");
        TextField city = new TextField();

        Label lblCountry = new Label("Country");
        TextField country = new TextField();

        Button btnInsert = new Button("Insert");

        vBox.getChildren().addAll(lblTitle, lblId, studentId, lblName, name, lblEmail, email, pickerBox, lblGender, rbMale, rbFemale, lblAddress, address, lblPostalCode, postalCode, lblCity, city, lblCountry, country, btnInsert);

        btnInsert.setOnAction((event) -> {
            try{ 
                //Set date of birth
                LocalDate dateOfBirth = datePicker.getValue();

                //Set gender
                RadioButton selectedRadioButton = (RadioButton) genderGroup.getSelectedToggle();

                databaseStudent.insertStudent(Integer.parseInt(studentId.getText()), name.getText(), email.getText(), Date.valueOf(dateOfBirth), selectedRadioButton.getText(), address.getText(), postalCode.getText(), city.getText(), country.getText());
                // System.out.println("It worked!");
                
            } catch(Exception e) {
                e.printStackTrace();
            }
        });


        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();
    }
    
    
} 