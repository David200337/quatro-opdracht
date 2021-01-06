package src.ui;

import src.db.DatabaseStudent;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import src.domain.EditingCell;
import src.domain.Student;

public class UsersDetailView {
    DatabaseStudent databaseStudent;

    public UsersDetailView() {
        databaseStudent = new DatabaseStudent(
                "jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseStudent.loadStudents();
    }

    public Parent getView(Student student) {
        // databaseCoursesModule.loadCourseDetails(courseModule);

        VBox layout = new VBox();
        HBox topLayout = new HBox(10);
        Region filler = new Region();

        Label titleLabel = new Label("Student:");
        titleLabel.getStyleClass().add("view-title");
        titleLabel.setPadding(new Insets(10, 10, 10, 15));

        Label lblStudentName = new Label("Name: " + student.getName());
        Label lblEmail = new Label("E-mail: " + student.getEmail());
        Label lblDateOfBirth = new Label("Date of Birth: " + student.getDateOfBirth());
        Label lblGender = new Label("Gender: " + student.getGender());
        Label lblAddress = new Label("Address: " + student.getAddress());
        Label lblPostalCode = new Label("Postal Code: " + student.getPostalCode());
        Label lblCity = new Label("City: " + student.getCity());
        Label lblCountry = new Label("Country: " + student.getCountry());



        

        // Add layout
        HBox.setHgrow(filler, Priority.ALWAYS);
        topLayout.getChildren().addAll(titleLabel, filler);
        layout.getChildren().addAll(topLayout, lblStudentName, lblEmail, lblDateOfBirth, lblGender, lblAddress, lblPostalCode, lblCity, lblCountry);
        layout.setPadding(new Insets(10, 10, 10, 15));



        return layout;

    }
}
