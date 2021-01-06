package src.ui;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import src.domain.ContentCreator;
import src.domain.CourseModule;
import src.domain.DatePickerConverter;
import src.domain.Level;
import src.domain.Status;
import src.db.DatabaseCourseModule;
import src.db.FillComboBox;

public class ModuleAddView {
    private DatabaseCourseModule databaseCourses;
    private FillComboBox fcb;


    public ModuleAddView() {
        databaseCourses = new DatabaseCourseModule("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseCourses.loadCourseModules();
        fcb = new FillComboBox("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
    }

    public Parent getView(CourseModule selectedCourseModule) throws Exception {
        VBox layout = new VBox();

        Label titleLabel = new Label("Add Course");
        titleLabel.getStyleClass().add("view-title");
        titleLabel.setPadding(new Insets(0, 0, 10, 0));


        //Insert content
        Label lblContent = new Label("Content Item");
        TextField contentId = new TextField();
        // ComboBox contentComboBox = new ComboBox<String>();
        // ObservableList<String> listContent = FXCollections.observableArrayList();
        // fcb.fillListFromDataBaseString(listContent, "ContentId","Content");
        // contentComboBox.setItems(listContent);

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

        //Insert creator
        Label lblCreator = new Label("Creator");
        ComboBox creatorComboBox = new ComboBox<String>();
        ObservableList<String> listCreators = FXCollections.observableArrayList();
        fcb.fillListFromDataBaseString(listCreators, "CreatorId","ContentCreator");
        creatorComboBox.setItems(listCreators);
        

        Button btnInsert = new Button("Insert");

        layout.getChildren().addAll(titleLabel, lblContent, contentId, lblModuleTitle, moduleTitle, lblModuleTheme, moduleTheme, lblModuleDescription, moduleDescription, pickerBox, lblStatus, statusComboBox, lblModuleSerialNumber, moduleSerialNumber, lblCreator, creatorComboBox, btnInsert);

        //Insert button action
        btnInsert.setOnAction((event) -> {
            try{             
                
                databaseCourses.insertModule(selectedCourseModule.getCourseId(), Integer.parseInt(contentId.getText()), Date.valueOf(datePicker.getValue()), moduleTitle.getText(), moduleTheme.getText(), moduleDescription.getText(), String.valueOf(statusComboBox.getValue()), Integer.parseInt(moduleSerialNumber.getText()), String.valueOf(creatorComboBox.getValue()));
                // System.out.println("It worked!");  
                contentId.clear();
                datePicker.setValue(null);
                moduleTitle.clear();
                moduleTheme.clear();
                moduleDescription.clear();
                statusComboBox.setValue(null);
                moduleSerialNumber.clear();
                creatorComboBox.setValue(null);

            } catch(Exception e) {
                e.printStackTrace();
            }
        });
        
        return layout;
    }
}