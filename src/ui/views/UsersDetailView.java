package src.ui.views;

import java.sql.Date;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import src.db.DatabaseRegistration;
import src.domain.Registration;
import src.domain.Student;
import src.ui.GUI;

public class UsersDetailView {
    private DatabaseRegistration databaseRegistration;
    private Label viewTitleLabel;
    private Button backButton;
    private Label emailLabel;
    private Label dateOfBirthLabel;
    private Label genderLabel;
    private Label addressLabel;
    private Label postalCodeLabel;
    private Label cityLabel;
    private Label countryLabel;
    private Label registrationsTitleLabel;
    private TableView<Registration> registrationsTableView;
    private TableColumn<Registration, String> regCourseNameCol;
    private TableColumn<Registration, Date> regRegistrationDateCol;
    private Label certificatesTitleLabel;
    private TableView<Registration> certificatesTableView;
    private TableColumn<Registration, Integer> courseNameCol;
    private TableColumn<Registration, Date> registrationDateCol;
    private TableColumn<Registration, Date> certificateIdCol;
    private TableColumn<Registration, Date> certificateStaffName;
    private TableColumn<Registration, Date> certificateGrade;

    private Region region;
    private HBox topLayout;
    private VBox layout;

    public UsersDetailView(Student student) {
        databaseRegistration = new DatabaseRegistration("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        viewTitleLabel = new Label(student.getName());
        backButton = new Button("Back");
        emailLabel = new Label("Email: " + student.getEmail());
        dateOfBirthLabel = new Label("Date of Birth: " + student.getDateOfBirth());
        genderLabel = new Label("Gender: " + student.getGender());
        addressLabel = new Label("Address: " + student.getAddress());
        postalCodeLabel = new Label("Postal Code: " + student.getPostalCode());
        cityLabel = new Label("City: " + student.getCity());
        countryLabel = new Label("Country: " + student.getCountry());

        registrationsTitleLabel = new Label("Registrations");
        registrationsTableView = new TableView<>();
        regCourseNameCol = new TableColumn<>("Course");
        regRegistrationDateCol = new TableColumn<>("Registration Date");

        certificatesTitleLabel = new Label("Certificates");
        certificatesTableView = new TableView<>();
        courseNameCol = new TableColumn<>("Course");
        registrationDateCol = new TableColumn<>("Registration Date");
        certificateIdCol = new TableColumn<>("Certificate ID");
        certificateStaffName = new TableColumn<>("Certificate Staff Name");
        certificateGrade = new TableColumn<>("Grade");

        region = new Region();
        topLayout = new HBox(10);
        layout = new VBox(10);
    }

    private void configureNodes() {
        viewTitleLabel.getStyleClass().add("view-title");

        registrationsTitleLabel.getStyleClass().add("view-title");
        regCourseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        regRegistrationDateCol.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
        registrationsTableView.getColumns().addAll(regCourseNameCol, regRegistrationDateCol);

        certificatesTitleLabel.getStyleClass().add("view-title");
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        registrationDateCol.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
        certificateIdCol.setCellValueFactory(new PropertyValueFactory<>("certificateId"));
        certificateStaffName.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        certificateGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        certificatesTableView.setItems(databaseRegistration.getCompletedRegistrations());
        certificatesTableView.getColumns().addAll(courseNameCol, registrationDateCol, certificateIdCol, certificateStaffName, certificateGrade);
    }

    private void configureLayout() {
        HBox.setHgrow(region, Priority.ALWAYS);
        topLayout.getChildren().addAll(viewTitleLabel, region, backButton);

        layout.setPadding(new Insets(10, 10, 10, 15));
        layout.getChildren().addAll(topLayout, emailLabel, dateOfBirthLabel, genderLabel, addressLabel, postalCodeLabel, cityLabel, countryLabel, registrationsTitleLabel, registrationsTableView, certificatesTitleLabel, certificatesTableView);
    }

    private void handleActions() {
        backButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new UsersView().getView());
        });
    }

    public VBox getView() {
        configureNodes();
        configureLayout();
        handleActions();

        return layout;
    }
}
