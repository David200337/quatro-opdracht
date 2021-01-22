package src.ui.views;

import java.util.Optional;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import src.db.DatabaseStudent;
import src.domain.EditingCell;
import src.domain.Student;
import src.ui.GUI;

public class UsersView {
    private DatabaseStudent databaseStudent;
    private Label viewTitleLabel;
    private Button addUserButton;
    private Button viewDetailsButton;
    private Button removeUserButton;
    private TableView<Student> usersTableView;
    private TableColumn<Student, String> nameColumn;
    private TableColumn<Student, String> emailColumn;
    private TableColumn<Student, String> dateOfBirthColumn;
    private TableColumn<Student, String> genderColumn;
    private TableColumn<Student, String> addressColumn;
    private TableColumn<Student, String> postalCodeColumn;
    private TableColumn<Student, String> cityColumn;
    private TableColumn<Student, String> countryColumn;
    private PieChart genderPieChart;
    private PieChart.Data femaleDataPieChart;
    private int femaleInteger;
    private PieChart.Data maleDataPieChart;
    private int maleInteger;
    private Alert alert;
    private Region region;
    private HBox topLayout;
    private VBox layout;


    public UsersView() {
        // Database connection
        databaseStudent = new DatabaseStudent("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseStudent.loadStudents();

        // Top Layout
        viewTitleLabel = new Label("Users");
        addUserButton = new Button("Add");
        viewDetailsButton = new Button("Go to details");
        removeUserButton = new Button("Delete");

        // Table view
        usersTableView = new TableView<>();
        nameColumn = new TableColumn<>("Name");
        emailColumn = new TableColumn<>("Email");
        dateOfBirthColumn = new TableColumn<>("Date of birth");
        genderColumn = new TableColumn<>("Gender");
        addressColumn = new TableColumn<>("Address");
        postalCodeColumn = new TableColumn<>("Postal code");
        cityColumn = new TableColumn<>("City");
        countryColumn = new TableColumn<>("Country");

        // Pie chart
        try {
            femaleInteger = databaseStudent.getNumberOfFemales();
            maleInteger = databaseStudent.getNumberOfMales();
        } catch (Exception e) {
            System.out.println(e);
        }

        genderPieChart = new PieChart();
        femaleDataPieChart = new PieChart.Data("Female", femaleInteger);
        maleDataPieChart = new PieChart.Data("Male", maleInteger);

        // Layout
        region = new Region();
        topLayout = new HBox(10);
        layout = new VBox(10);
    }

    private void configureNodes() {
        viewTitleLabel.getStyleClass().add("view-title");

        Callback<TableColumn<Student, String>, TableCell<Student, String>> stringCellFactory = (TableColumn<Student, String> param) -> new EditingCell<Student>();
        
        usersTableView.setEditable(true);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(stringCellFactory);
        nameColumn.setOnEditCommit((TableColumn.CellEditEvent<Student, String> t) -> {
            ((Student) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());

            Student student = t.getRowValue();
            student.setName(t.getNewValue());
            databaseStudent.updateStudentString("StudentName", t.getNewValue(), student.getStudentId());
        });

        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setCellFactory(stringCellFactory);
        emailColumn.setOnEditCommit((TableColumn.CellEditEvent<Student, String> t) -> {
            ((Student) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEmail(t.getNewValue());

            Student student = t.getRowValue();
            student.setEmail(t.getNewValue());
            databaseStudent.updateStudentString("StudentEmail", t.getNewValue(), student.getStudentId());
        });

        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        dateOfBirthColumn.setEditable(true);

        ObservableList<String> genderList = FXCollections.observableArrayList("Female", "Male");
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        genderColumn.setCellValueFactory(new Callback<CellDataFeatures<Student, String>, ObservableValue<String>>() {
            @Override

            public ObservableValue<String> call(CellDataFeatures<Student, String> param) {
                Student student = param.getValue();
                return new SimpleObjectProperty<String>(student.getGender());
            }

        });
        genderColumn.setCellFactory(ComboBoxTableCell.forTableColumn(genderList));
        genderColumn.setOnEditCommit((CellEditEvent<Student, String> event) -> {
            TablePosition<Student, String> pos = event.getTablePosition();

            String newStatus = event.getNewValue();

            int row = pos.getRow();
            Student student = event.getTableView().getItems().get(row);

            databaseStudent.updateStudentString("Gender", newStatus, student.getStudentId());

            student.setGender(newStatus);
        });

        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressColumn.setCellFactory(stringCellFactory);
        addressColumn.setOnEditCommit((TableColumn.CellEditEvent<Student, String> t) -> {
            ((Student) t.getTableView().getItems()
            .get(t.getTablePosition().getRow()))
            .setAddress(t.getNewValue());

            Student student = t.getRowValue();
            student.setAddress(t.getNewValue());
            databaseStudent.updateStudentString("Address", t.getNewValue(), student.getStudentId());
        });

        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        postalCodeColumn.setCellFactory(stringCellFactory);
        postalCodeColumn.setOnEditCommit((TableColumn.CellEditEvent<Student, String> t) -> {
            ((Student) t.getTableView().getItems()
            .get(t.getTablePosition().getRow()))
            .setPostalCode(t.getNewValue());

            Student student = t.getRowValue();
            student.setPostalCode(t.getNewValue());
            databaseStudent.updateStudentString("PostalCode", t.getNewValue(), student.getStudentId());
        });

        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        cityColumn.setCellFactory(stringCellFactory);
        cityColumn.setOnEditCommit((TableColumn.CellEditEvent<Student, String> t) -> {
            ((Student) t.getTableView().getItems()
            .get(t.getTablePosition().getRow()))
            .setCity(t.getNewValue());

            Student student = t.getRowValue();
            student.setCity(t.getNewValue());
            databaseStudent.updateStudentString("City", t.getNewValue(), student.getStudentId());
        });

        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        countryColumn.setCellFactory(stringCellFactory);
        countryColumn.setOnEditCommit((TableColumn.CellEditEvent<Student, String> t) -> {
            ((Student) t.getTableView().getItems()
            .get(t.getTablePosition().getRow()))
            .setCountry(t.getNewValue());

            Student student = t.getRowValue();
            student.setCountry(t.getNewValue());
            databaseStudent.updateStudentString("Country", t.getNewValue(), student.getStudentId());
        });

        usersTableView.setItems(databaseStudent.getStudents());
        usersTableView.getColumns().addAll(nameColumn, emailColumn, dateOfBirthColumn, genderColumn, addressColumn, postalCodeColumn, cityColumn, countryColumn);
    
        genderPieChart.getData().add(femaleDataPieChart);
        genderPieChart.getData().add(maleDataPieChart);
    }

    private void configureLayout() {
        HBox.setHgrow(region, Priority.ALWAYS);
        topLayout.getChildren().addAll(viewTitleLabel, region, addUserButton, viewDetailsButton, removeUserButton);
        layout.setPadding(new Insets(10, 10, 10, 15));
        layout.getChildren().addAll(topLayout, usersTableView, genderPieChart);
    }

    private void handleActions() {
        addUserButton.setOnAction(e -> {
            GUI.getLayout().setCenter(new UsersAddView().getView());
        });

        viewDetailsButton.setOnAction(e -> {
            try {
                Student student = usersTableView.getSelectionModel().getSelectedItem();

                if(student == null){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Missing course");
                    alert.setContentText("You didn't select a student!");
                    alert.showAndWait();
                } else{
                    GUI.getLayout().setCenter(new UsersDetailView(student).getView());
                }
            } catch(Exception error) {
                error.printStackTrace();
            } 
        });

        removeUserButton.setOnAction(e -> {
            try {
                Student selectedItem = usersTableView.getSelectionModel().getSelectedItem();

                if(selectedItem == null){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Missing student");
                    alert.setContentText("You didn't select a student!");
                    alert.showAndWait();
                } else {
                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Delete");
                    alert.setHeaderText("Are you sure you want to delete this student?");
                    alert.setContentText("");
                  
                    Optional<ButtonType> result = alert.showAndWait();

                    if(!result.isPresent() || result.get() != ButtonType.OK) {
                        // TODO: ??
                    } else {
                        usersTableView.getItems().remove(selectedItem);
                        databaseStudent.deleteStudent(selectedItem);
                    }
                }
            } catch(Exception error) {
                System.out.println(error);
            } 
        });
    }

    public VBox getView() {
        configureNodes();
        configureLayout();
        handleActions();

        return layout;
    }
}
