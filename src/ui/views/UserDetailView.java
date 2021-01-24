package src.ui.views;

import java.sql.Date;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import src.db.DatabaseRegistration;
import src.domain.Registration;
import src.domain.Student;
import src.ui.GUI;

public class UserDetailView {
    private Student student;
    private DatabaseRegistration databaseRegistration;
    private Label viewTitleLabel;
    private Button backButton;
    private Button addRegistrationButton;
    private Button moduleProgressButton;
    private Button addCertificateButton;
    private Button removeCertificateButton;
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
    private Alert alert;

    private Region region;
    private HBox topLayout;
    private Region registrationsRegion;
    private HBox registrationsLayout;
    private Region certificateRegion;
    private HBox certificateLayout;
    private VBox layout;

    //Initialize the variables
    public UserDetailView(Student student) {
        this.student = student;
        databaseRegistration = new DatabaseRegistration("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseRegistration.loadCompletedRegistrations(student.getStudentId());
        databaseRegistration.loadRegistrations(student.getStudentId());

        viewTitleLabel = new Label(student.getName());
        backButton = new Button("Back");
        moduleProgressButton = new Button("Progress");
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

        addCertificateButton = new Button("Add");
        removeCertificateButton = new Button("Remove");
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
        certificateRegion = new Region();
        certificateLayout = new HBox(10);
        layout = new VBox(10);
    }

    //Configure the nodes
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

    //Configure the layout
    private void configureLayout() {
        HBox.setHgrow(region, Priority.ALWAYS);
        topLayout.getChildren().addAll(viewTitleLabel, region, moduleProgressButton, backButton);

        HBox.setHgrow(registrationsRegion, Priority.ALWAYS);
        registrationsLayout.getChildren().addAll(registrationsTitleLabel, registrationsRegion, addRegistrationButton);

        HBox.setHgrow(certificateRegion,Priority.ALWAYS);
        certificateLayout.getChildren().addAll(certificatesTitleLabel, certificateRegion, addCertificateButton, removeCertificateButton);

        layout.setPadding(new Insets(10, 10, 10, 15));
        layout.getChildren().addAll(topLayout, emailLabel, dateOfBirthLabel, genderLabel, addressLabel, postalCodeLabel, cityLabel, countryLabel, registrationsLayout, registrationsTableView, certificateLayout, certificatesTableView);
    }

    //Initialize the actions of the buttons
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

        addCertificateButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new CertificateAddView(student).getView());
        });

        removeCertificateButton.setOnAction(e -> {
            try {
                Registration selectedItem = certificatesTableView.getSelectionModel().getSelectedItem();
                if (selectedItem == null){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Missing certificate");
                    alert.setContentText("You didn't select a certificate!");
                    alert.showAndWait();
                } else {
                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Delete");
                    alert.setHeaderText("Are you sure you want to delete this certificate?");

                    Optional<ButtonType> result = alert.showAndWait();
                    
                    if (!result.isPresent() || result.get() != ButtonType.OK) {
                       
                    } else {
                        certificatesTableView.getItems().remove(selectedItem);
                        databaseRegistration.deleteCertificate(selectedItem);
                        databaseRegistration.updateRegistrationWhenDeleteCertificate(selectedItem);
                    }
                }
            } catch(Exception error) {
                error.printStackTrace();
            } 
        });
    }

    //Put everything together and show it
    public VBox getView() {
        configureNodes();
        configureLayout();
        handleActions();

        return layout;
    }
}
