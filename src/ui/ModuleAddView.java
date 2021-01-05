package src.ui;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import src.domain.CourseModule;
import src.domain.DatePickerConverter;
import src.domain.Level;
import src.domain.Status;
import src.db.DatabaseCourseModule;

public class ModuleAddView {
    private DatabaseCourseModule databaseCourses;


    public ModuleAddView() {
        databaseCourses = new DatabaseCourseModule("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseCourses.loadCourseModules();
    }

    public Parent getView(CourseModule selectedCourseModule) {
        VBox layout = new VBox();

        Label titleLabel = new Label("Add Course");
        titleLabel.getStyleClass().add("view-title");
        titleLabel.setPadding(new Insets(0, 0, 10, 0));

        //Insert module title
        Label lblModuleTitle = new Label("Module Title");
        TextField moduleTitle = new TextField();

        //Insert module title
        Label lblModuleTheme = new Label("Module Theme");
        TextField moduleTheme = new TextField();
               
        //Insert module description
        Label lblModuleDescription = new Label("Module Description");
        TextField moduleDescription = new TextField();
                
        //Insert date of publication
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
        Label lblPublicationDate = new Label("Publication Date");
        //Create the HBox for the DatePicker
        HBox pickerBox = new HBox(lblPublicationDate, datePicker);
        
        
        //Insert status
        Label lblStatus = new Label("Status");
        ComboBox<Status> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().setAll(Status.values());

        //Insert module serialNumber
        Label lblModuleSerialNumber = new Label("Module Serial Number");
        TextField moduleSerialNumber = new TextField();

        Button btnInsert = new Button("Insert");

        layout.getChildren().addAll(titleLabel, lblModuleTitle, moduleTitle, lblModuleTheme, moduleTheme, lblModuleDescription, moduleDescription, pickerBox, lblStatus, statusComboBox, lblModuleSerialNumber, moduleSerialNumber, btnInsert);

        //Insert button action
        btnInsert.setOnAction((event) -> {
            try{             
                
                databaseCourses.insertModule(selectedCourseModule.getCourseId(), selectedCourseModule.getContentId(), Date.valueOf(datePicker.getValue()), moduleTitle.getText(), moduleTheme.getText(), moduleDescription.getText(), String.valueOf(statusComboBox.getValue()), Integer.parseInt(moduleSerialNumber.getText()));
                // System.out.println("It worked!");  
                

            } catch(Exception e) {
                e.printStackTrace();
            }
        });
        
        return layout;
    }
}
