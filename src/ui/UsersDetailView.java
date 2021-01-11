package src.ui;

import src.db.DatabaseRegistration;
import src.db.DatabaseStudent;

import java.sql.Date;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import src.domain.Registration;
import src.domain.Student;

public class UsersDetailView {
    DatabaseStudent databaseStudent;
    DatabaseRegistration databaseRegistration;
    private RegistrationAddView registrationAddView;
    TableView<Registration> registrationList;

    public UsersDetailView() {
        databaseStudent = new DatabaseStudent(
                "jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseStudent.loadStudents();

        databaseRegistration = new DatabaseRegistration("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseRegistration.loadCompletedRegistrations();
        databaseRegistration.loadRegistrations();

        registrationAddView = new RegistrationAddView();
        registrationList = new TableView<>();
    }

    public Parent getView(Student student) throws Exception {
      

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


        registrationList.setEditable(true);

        Button addRegistrationButton = new Button("Add registration");
        Button removeRegistrationButton = new Button("Remove");

        addRegistrationButton.setOnAction(e -> {
            layout.getChildren().clear();
            try {
                layout.getChildren().add(registrationAddView.getView(student));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        removeRegistrationButton.setOnAction((event) -> {
            try{
                Registration registration = registrationList.getSelectionModel().getSelectedItem();
                registrationList.getItems().remove(registration);
                databaseRegistration.deleteRegistration(registration);
            }catch(Exception e) {
                e.printStackTrace();
            } 
        });

        //Completed registrations (with certificate)
        Label titleTable = new Label("Courses with certificate:");
        titleTable.getStyleClass().add("view-title");
        titleTable.setPadding(new Insets(10, 10, 10, 15));

        TableColumn<Registration, Integer> courseNameCol = new TableColumn<>("Course");
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        
        
        TableColumn<Registration, Date> registrationDateCol = new TableColumn<>("Registration Date");
        registrationDateCol.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));

        TableColumn<Registration, Date> certificateIdCol = new TableColumn<>("Certificate ID");
        certificateIdCol.setCellValueFactory(new PropertyValueFactory<>("certificateId"));

        TableColumn<Registration, Date> certificateStaffName = new TableColumn<>("Certificate Staff Name");
        certificateStaffName.setCellValueFactory(new PropertyValueFactory<>("staffName"));

        TableColumn<Registration, Date> certificateGrade = new TableColumn<>("Grade");
        certificateGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));



        registrationList.setItems(databaseRegistration.getCompletedRegistrations());
        registrationList.getColumns().addAll(courseNameCol, registrationDateCol, certificateIdCol, certificateStaffName, certificateGrade);


        //Other registrations
        Label titleRegistrationsTable = new Label("Other registrations:");
        titleRegistrationsTable.getStyleClass().add("view-title");
        titleRegistrationsTable.setPadding(new Insets(10, 10, 10, 15));
        
        TableView<Registration> registrationsTable = new TableView<>();
       
        Button removeButton = new Button("Remove");

        removeButton.setOnAction((event) -> {
            try{
                Registration registration = registrationsTable.getSelectionModel().getSelectedItem();
                registrationsTable.getItems().remove(registration);
                databaseRegistration.deleteRegistration(registration);
            }catch(Exception e) {
                e.printStackTrace();
            } 
        });
        
       

        TableColumn<Registration, String> regCourseNameCol = new TableColumn<>("Course");
        regCourseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        
        
        TableColumn<Registration, Date> regRegistrationDateCol = new TableColumn<>("Registration Date");
        regRegistrationDateCol.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));

        registrationsTable.setItems(databaseRegistration.getRegistrations());
        registrationsTable.getColumns().addAll(regCourseNameCol, regRegistrationDateCol);


        // Add layout
        HBox.setHgrow(filler, Priority.ALWAYS);
        topLayout.getChildren().addAll(titleLabel, filler, addRegistrationButton, removeRegistrationButton);
        layout.getChildren().addAll(topLayout, lblStudentName, lblEmail, lblDateOfBirth, lblGender, lblAddress, lblPostalCode, lblCity, lblCountry, titleTable, registrationList, titleRegistrationsTable, removeButton, registrationsTable);
        layout.setPadding(new Insets(10, 10, 10, 15));



        return layout;

    }

}
