package src.ui;

import src.db.*;
import src.domain.EditingCell;
import src.domain.Student;


import java.sql.Date;

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

public class UsersView {
    private DatabaseStudent databaseStudent;
    private UsersAddView usersAddView;
    private UsersDetailView usersDetailView;
    private TableView<Student> usersTableView = new TableView<>();

    public UsersView() {
        databaseStudent = new DatabaseStudent("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseStudent.loadStudents();
        usersAddView = new UsersAddView();
        usersDetailView = new UsersDetailView();
    }

    // void refreshTable() {
    //     final List<Student> items = usersTableView.getItems();
    //     if( items == null || items.size() == 0) return;
    //     final Student item = usersTableView.getItems().get(0);
    //     items.remove(0);
    //     Platform.runLater(new Runnable(){
    //         @Override
    //         public void run() {
    //             items.add(0, item);
    //         }
    //     });
    //  }

    public Parent getView() {
        VBox layout = new VBox();
        HBox topLayout = new HBox(10);
        Region filler = new Region(); 

        Label titleLabel = new Label("Users");
        titleLabel.getStyleClass().add("view-title");
        titleLabel.setPadding(new Insets(0, 0, 10, 0));

        Button addUserButton = new Button("Add user");
        Button removeUserButton = new Button("Remove user");
        Button detailUserButton = new Button("Go to details");




        addUserButton.setOnAction(e -> {
            layout.getChildren().clear();
            layout.getChildren().add(usersAddView.getView());
        });


        removeUserButton.setOnAction((event) -> {
            try{
                Student selectedItem = usersTableView.getSelectionModel().getSelectedItem();
                usersTableView.getItems().remove(selectedItem);
                databaseStudent.deleteStudent(selectedItem);
            }catch(Exception e) {
                e.printStackTrace();
            } 
        });

        detailUserButton.setOnAction((event) -> {
            try{
                Student student = usersTableView.getSelectionModel().getSelectedItem();
                layout.getChildren().clear();
                layout.getChildren().add(usersDetailView.getView(student));
            }catch(Exception e) {
                e.printStackTrace();
            } 
        });

        usersTableView.setEditable(true);
        Callback<TableColumn<Student, String>, TableCell<Student, String>> stringCellFactory = (TableColumn<Student, String> param) -> new EditingCell<Student>();
        // Callback<TableColumn<Student, String>, TableCell<Student, String>> comboBoxCellFactory
        //         = (TableColumn<Student, String> param) -> new ComboBoxEditingCell();


        // ID Column
        TableColumn<Student, Integer> studentIdColumn = new TableColumn<>("ID");
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));

        // Name column
        TableColumn<Student, String> studentNameColumn = new TableColumn<>("Name");
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        studentNameColumn.setCellFactory(stringCellFactory);
        studentNameColumn.setOnEditCommit((TableColumn.CellEditEvent<Student, String> t) -> {
            ((Student) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());

            Student student = t.getRowValue();
            student.setName(t.getNewValue());
            databaseStudent.updateStudentString("StudentName", t.getNewValue(), student.getStudentId());
        });

        // Email column
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
        // studentEmailColumn.setCellFactory(stringCellFactory);
        // studentEmailColumn.setOnEditCommit((TableColumn.CellEditEvent<Student, String> t) -> {
        //     ((Student) t.getTableView().getItems().get(t.getTablePosition().getRow())).setGender(t.getNewValue());

        //     Student student = t.getRowValue();
        //     student.setGender(t.getNewValue());
        //     databaseStudent.updateStudentString("Gender", t.getNewValue(), student.getStudentId());
        // });

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

        usersTableView.setItems(databaseStudent.getStudents());
        usersTableView.getColumns().addAll(studentIdColumn, studentNameColumn, studentEmailColumn, studentDateOfBirthColumn, studentGenderColumn, studentAddressColumn, studentPostalCodeColumn, studentCityColumn, studentCountryColumn);

        // Add layout
        HBox.setHgrow(filler, Priority.ALWAYS);
        topLayout.getChildren().addAll(titleLabel, filler, addUserButton, removeUserButton, detailUserButton);
        layout.getChildren().addAll(topLayout, usersTableView);
        layout.setPadding(new Insets(10, 10, 10, 15));
        
    
        return layout;
    }
}
