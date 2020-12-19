import java.sql.SQLException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GUIUsers extends Application {

    private DatabaseStudent databaseConnection;

    @Override
    public void start(Stage window) throws ClassNotFoundException, SQLException {
        databaseConnection = new DatabaseStudent("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseConnection.loadStudents();
        
        window.setTitle("Courses");
        TableView<Student> studentTable = new TableView<>();
        TableColumn<Student, String> studentIdCol = new TableColumn<>("StudentID");
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        TableColumn<Student, String> studentNameCol = new TableColumn<>("Name");
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Student, String> studentEmailCol = new TableColumn<>("E-mail");
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Student, String> studentDateOfBirthCol = new TableColumn<>("Date of Birth");
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        TableColumn<Student, String> studentGenderCol = new TableColumn<>("Gender");
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        TableColumn<Student, String> studentAddressCol = new TableColumn<>("Address");
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumn<Student, String> studentPostalCodeCol = new TableColumn<>("Postal Code");
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        TableColumn<Student, String> studentCityCol = new TableColumn<>("City");
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        TableColumn<Student, String> studentCountryCol = new TableColumn<>("Country");
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("country"));

        // Print all students' name and email in the console
        for (Student student : databaseConnection.getStudents()) {
            System.out.println(student.getStudentName());
            System.out.println(student.getStudentEmail());
        }

        studentTable.getColumns().addAll(studentIdCol, studentNameCol, studentEmailCol, studentDateOfBirthCol, studentGenderCol, studentAddressCol, studentPostalCodeCol, studentCityCol, studentCountryCol);
        
        studentTable.getItems().addAll(databaseConnection.getStudents());

        BorderPane root = new BorderPane();
        root.setCenter(studentTable);
        Scene scene = new Scene(root, 750, 400);
        window.setScene(scene);

        window.show();
    }

    public static void main(String[] args){
        launch(GUIUsers.class);
    }
} 