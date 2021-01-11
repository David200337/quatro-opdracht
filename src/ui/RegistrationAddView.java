package src.ui;

import src.db.DatabaseRegistration;
import src.db.FillComboBox;
import src.domain.DatePickerConverter;
import src.domain.Student;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class RegistrationAddView {
    DatabaseRegistration databaseRegistration;
    FillComboBox fcb;

    public RegistrationAddView(){
        databaseRegistration = new DatabaseRegistration("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseRegistration.loadCompletedRegistrations();

        fcb = new FillComboBox("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
    }

    public Parent getView(Student selectedStudent) throws Exception {
        VBox layout = new VBox();

        Label titleLabel = new Label("Add Course");
        titleLabel.getStyleClass().add("view-title");
        titleLabel.setPadding(new Insets(0, 0, 10, 0));

        

        //Link registration to a course
        Label lblCourse = new Label("Course");
        ComboBox<String> courseComboBox = new ComboBox<String>();
        ObservableList<String> listCourses = FXCollections.observableArrayList();
        fcb.fillListFromDataBaseString(listCourses, "CourseName","Course");
        courseComboBox.setItems(listCourses);
        

       //Insert registration date
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
       Label lblRegistrationDate = new Label("Registration Date");
       //Create the HBox for the DatePicker
       HBox pickerBox = new HBox(lblRegistrationDate, datePicker);


       //Insert button
       Button btnInsert = new Button("Insert");

       layout.getChildren().addAll(titleLabel, lblCourse, courseComboBox, lblRegistrationDate, pickerBox, btnInsert);
        //Insert button action
        btnInsert.setOnAction((event) -> {
            try{ 
                databaseRegistration.insertRegistration(selectedStudent.getStudentId(), String.valueOf(courseComboBox.getValue()), Date.valueOf(datePicker.getValue()));
                // System.out.println("It worked!");
                
                datePicker.setValue(null);
                courseComboBox.setValue(null);
                

            } catch(Exception e) {
                e.printStackTrace();
            }
        });

        return layout;

    }
}
