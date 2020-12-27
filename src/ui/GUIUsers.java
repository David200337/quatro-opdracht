package src.ui;

import src.db.*;
import src.domain.Student;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class GUIUsers extends Application {
    private DatabaseStudent databaseStudent;
    private Stage window;
    TableView<Student> studentTable;

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
        vBox.getChildren().addAll(studentTable);

        Label lblTitle = new Label("Insert new student");

        Label lblId = new Label("ID");
        TextField studentId = new TextField();
        
        Label lblName = new Label("Name");
        TextField name = new TextField();

        Label lblEmail = new Label("E-Mail");
        TextField email = new TextField();

        Label lblDateOfBirth = new Label("DateOfBirth");
        TextField dateOfBirth = new TextField();

        Label lblGender = new Label("Gender");
        TextField gender = new TextField();

        Label lblAddress = new Label("Address");
        TextField address = new TextField();

        Label lblPostalCode = new Label("Postal Code");
        TextField postalCode = new TextField();

        Label lblCity = new Label("City");
        TextField city = new TextField();

        Label lblCountry = new Label("Country");
        TextField country = new TextField();

        Button btnInsert = new Button("Insert");

        vBox.getChildren().addAll(lblTitle, lblId, studentId, lblName, name, lblEmail, email, lblDateOfBirth, dateOfBirth, lblGender, gender, lblAddress, address, lblPostalCode, postalCode, lblCity, city, lblCountry, country, btnInsert);

        btnInsert.setOnAction((event) -> {
            try{ 
                databaseStudent.insertStudent(Integer.parseInt(studentId.getText()), name.getText(), email.getText(), dateOfBirth.getText(), gender.getText(), address.getText(), postalCode.getText(), city.getText(), country.getText());
                System.out.println("It worked!");
            } catch(Exception e) {
                e.printStackTrace();
            }
        });


        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();
    }
    
} 