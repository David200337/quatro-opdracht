package src.ui;

import src.db.*;
// import src.domain.DateEditingCell;
import src.domain.DatePickerConverter;
import src.domain.EditingCell;
import src.domain.Student;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Students");
        databaseStudent = new DatabaseStudent(
                "jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseStudent.loadStudents();

        //Buttons to switch between table and insert
        Button switchToTable = new Button("To table");
        Button switchToInsert = new Button("To insert");

        studentTable = new TableView<>();
        studentTable.setEditable(true);
        Callback<TableColumn<Student, String>, TableCell<Student, String>> stringCellFactory = (
                TableColumn<Student, String> param) -> new EditingCell();
        // Callback<TableColumn<Student, Date>, TableCell<Student, Date>> dateCellFactory = (
        //         TableColumn<Student, Date> param) -> new DateEditingCell();
        // Callback<TableColumn<Student, RadioButton>, TableCell<Student, RadioButton>>
        // radioButtonCellFactory
        // = (TableColumn<Student, RadioButton> param) -> new RadioButtonEditingCell();

        // ID Column
        TableColumn<Student, Integer> studentIdColumn = new TableColumn<>("ID");
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        

        // Name Column
        TableColumn<Student, String> studentNameColumn = new TableColumn<>("Name");
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        studentNameColumn.setCellFactory(stringCellFactory);
        studentNameColumn.setOnEditCommit((TableColumn.CellEditEvent<Student, String> t) -> {
            ((Student) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());

            Student student = t.getRowValue();
            student.setName(t.getNewValue());
            databaseStudent.updateStudentString("StudentName", t.getNewValue(), student.getStudentId());
        });

        // Email Column
        TableColumn<Student, String> studentEmailColumn = new TableColumn<>("Email");
        studentEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        studentEmailColumn.setCellFactory(stringCellFactory);
        studentEmailColumn.setOnEditCommit((TableColumn.CellEditEvent<Student, String> t) -> {
            ((Student) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEmail(t.getNewValue());

            Student student = t.getRowValue();
            student.setEmail(t.getNewValue());
            databaseStudent.updateStudentString("StudentEmail", t.getNewValue(), student.getStudentId());
        });

        // Date of birth Column
        TableColumn<Student, Date> studentDateOfBirthColumn = new TableColumn<>("Date of birth");
        studentDateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        studentDateOfBirthColumn.setEditable(true);
        //Date of birth kan nog niet aangepast worden

        // Gender Column
        TableColumn<Student, String> studentGenderColumn = new TableColumn<>("Gender");
        studentGenderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        //gender kan nog niet aangepast worden

        // Address Column
        TableColumn<Student, String> studentAddressColumn = new TableColumn<>("Address");
        studentAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        studentAddressColumn.setCellFactory(stringCellFactory);
        studentAddressColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Student, String> t) -> {
                    ((Student) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()))
                    .setAddress(t.getNewValue());

                    Student student = t.getRowValue();
                    student.setAddress(t.getNewValue());
                    databaseStudent.updateStudentString("Address", t.getNewValue(), student.getStudentId());
                });

        // Postal Code Column
        TableColumn<Student, String> studentPostalCodeColumn = new TableColumn<>("Postal Code");
        studentPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        studentPostalCodeColumn.setCellFactory(stringCellFactory);
        studentPostalCodeColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Student, String> t) -> {
                    ((Student) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()))
                    .setPostalCode(t.getNewValue());

                    Student student = t.getRowValue();
                    student.setPostalCode(t.getNewValue());
                    databaseStudent.updateStudentString("PostalCode", t.getNewValue(), student.getStudentId());
                });

        // City Column
        TableColumn<Student, String> studentCityColumn = new TableColumn<>("City");
        studentCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        studentCityColumn.setCellFactory(stringCellFactory);
        studentCityColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Student, String> t) -> {
                    ((Student) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()))
                    .setCity(t.getNewValue());

                    Student student = t.getRowValue();
                    student.setCity(t.getNewValue());
                    databaseStudent.updateStudentString("City", t.getNewValue(), student.getStudentId());
                });

        // Country Column
        TableColumn<Student, String> studentCountryColumn = new TableColumn<>("Country");
        studentCountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        studentCountryColumn.setCellFactory(stringCellFactory);
        studentCountryColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Student, String> t) -> {
                    ((Student) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()))
                    .setCountry(t.getNewValue());

                    Student student = t.getRowValue();
                    student.setCountry(t.getNewValue());
                    databaseStudent.updateStudentString("Country", t.getNewValue(), student.getStudentId());
                });

        // Setup table, load data & add columns
        
        studentTable.setItems(databaseStudent.getStudents());
        studentTable.getColumns().addAll(studentIdColumn, studentNameColumn, studentEmailColumn, studentDateOfBirthColumn, studentGenderColumn, studentAddressColumn, studentPostalCodeColumn, studentCityColumn, studentCountryColumn);

        VBox vBoxTable = new VBox();
        

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
        vBoxTable.getChildren().addAll(switchToInsert, studentTable, deleteButton);

        Scene tableScene = new Scene(vBoxTable);

        //Insert a new student
        VBox vBoxInsert = new VBox();

        Label lblTitle = new Label("Insert new student");

        //Insert ID
        Label lblId = new Label("ID");
        TextField studentId = new TextField();
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
        //Radio buttons for gender
        ComboBox genderComboBox = new ComboBox<String>(FXCollections.observableArrayList("Female", "Male"));
        
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

        vBoxInsert.getChildren().addAll(switchToTable, lblTitle, lblId, studentId, lblName, name, lblEmail, email, pickerBox, lblGender, genderComboBox, lblAddress, address, lblPostalCode, postalCode, lblCity, city, lblCountry, country, btnInsert);
        //Insert button action
        btnInsert.setOnAction((event) -> {
            try{ 
                //Set date of birth
                LocalDate dateOfBirth = datePicker.getValue();

             

                databaseStudent.insertStudent(name.getText(), email.getText(), Date.valueOf(dateOfBirth),String.valueOf(genderComboBox.getValue()), address.getText(), postalCode.getText(), city.getText(), country.getText());
                // System.out.println("It worked!");
                studentId.clear();
                name.clear();
                email.clear();
                datePicker.setValue(null);
                
                address.clear();
                postalCode.clear();
                city.clear();
                country.clear();

                
            } catch(Exception e) {
                e.printStackTrace();
            }
        });


        Scene insertScene = new Scene(vBoxInsert);
        

        switchToTable.setOnAction((event) -> {
            window.setScene(tableScene);
        });

        switchToInsert.setOnAction((event) -> {
            window.setScene(insertScene);
        });

        window.setScene(tableScene);
        window.show();
    }
    
    
} 