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
    private Student student;
    private DatabaseRegistration databaseRegistration;
    private Label viewTitleLabel;
    private Button backButton;
    private Button addRegistrationButton;
    private Button moduleProgressButton;
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
    private Region registrationsRegion;
    private HBox registrationsLayout;
    private VBox layout;

    public UsersDetailView(Student student) {
        this.student = student;
        databaseRegistration = new DatabaseRegistration("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseRegistration.loadCompletedRegistrations(student.getStudentId());
        databaseRegistration.loadRegistrations(student.getStudentId());

        viewTitleLabel = new Label(student.getName());
        backButton = new Button("Back");
        moduleProgressButton = new Button("Module Progress");
        emailLabel = new Label("Email: " + student.getEmail());
        dateOfBirthLabel = new Label("Date of Birth: " + student.getDateOfBirth());
        genderLabel = new Label("Gender: " + student.getGender());
        addressLabel = new Label("Address: " + student.getAddress());
        postalCodeLabel = new Label("Postal Code: " + student.getPostalCode());
        cityLabel = new Label("City: " + student.getCity());
        countryLabel = new Label("Country: " + student.getCountry());

        registrationsTitleLabel = new Label("Registrations");
        addRegistrationButton = new Button("Add");
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
        registrationsRegion = new Region();
        registrationsLayout = new HBox(10);
        layout = new VBox(10);
    }

    private void configureNodes() {
        viewTitleLabel.getStyleClass().add("view-title");
        emailLabel.getStyleClass().add("bold-text");
        dateOfBirthLabel.getStyleClass().add("bold-text");
        genderLabel.getStyleClass().add("bold-text");
        addressLabel.getStyleClass().add("bold-text");
        postalCodeLabel.getStyleClass().add("bold-text");
        cityLabel.getStyleClass().add("bold-text");
        countryLabel.getStyleClass().add("bold-text");

        registrationsTitleLabel.getStyleClass().add("view-subtitle");
        regCourseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        regRegistrationDateCol.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
        registrationsTableView.setItems(databaseRegistration.getRegistrations());
        registrationsTableView.getColumns().addAll(regCourseNameCol, regRegistrationDateCol);

        certificatesTitleLabel.getStyleClass().add("view-subtitle");
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
        topLayout.getChildren().addAll(viewTitleLabel, region, moduleProgressButton, backButton);

        HBox.setHgrow(registrationsRegion, Priority.ALWAYS);
        registrationsLayout.getChildren().addAll(registrationsTitleLabel, registrationsRegion, addRegistrationButton);


        layout.setPadding(new Insets(10, 10, 10, 15));
        layout.getChildren().addAll(topLayout, emailLabel, dateOfBirthLabel, genderLabel, addressLabel, postalCodeLabel, cityLabel, countryLabel, registrationsLayout, registrationsTableView, certificatesTitleLabel, certificatesTableView);
    }

    private void handleActions() {
        backButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new UsersView().getView());
        });

        moduleProgressButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new ModuleProgressView(student).getView());
        });

        addRegistrationButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new RegistrationAddView(student).getView());
        });
    }

    public VBox getView() {
        configureNodes();
        configureLayout();
        handleActions();

        return layout;
    }
}
