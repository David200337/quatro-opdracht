package src.ui;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import src.db.DatabaseWebcast;
import src.db.FillComboBox;
import src.domain.DatePickerConverter;
import src.domain.Status;
import src.domain.Webcast;

public class WebcastAddView {
    private DatabaseWebcast databaseWebcast;
    private FillComboBox fcb;


    public WebcastAddView() {
        databaseWebcast = new DatabaseWebcast("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
        databaseWebcast.loadWebcasts();
        fcb = new FillComboBox("jdbc:sqlserver://localhost;databaseName=Quatro-opdracht;integratedSecurity=true;");
    }

    public Parent getView() throws Exception {
        VBox layout = new VBox();

        Label titleLabel = new Label("Add Webcast");
        titleLabel.getStyleClass().add("view-title");
        titleLabel.setPadding(new Insets(0, 0, 10, 0));


        //Insert content
        Label lblContent = new Label("Content Item");
        TextField contentId = new TextField();
     
        //Insert title
        Label lblWebcastTitle = new Label("Webcast title");
        TextField webcastTitle = new TextField();

        //Insert title
        Label lblWebcastTheme = new Label("Webcast Theme");
        TextField webcastTheme = new TextField();
               
        //Insert module description
        Label lblWebcastDescription = new Label("Webcast Description");
        TextField webcastDescription = new TextField();
                
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

        //Insert URL
        Label lblWebcastUrl = new Label("Webcast URL");
        TextField webcastUrl = new TextField();

        //Insert duration
        Label lblWebcastDuration = new Label("Webcast Duration");
        TextField webcastDuration = new TextField();

        //Insert creator
        Label lblCreator = new Label("Creator");
        ComboBox<String> creatorComboBox = new ComboBox<String>();
        ObservableList<String> listCreators = FXCollections.observableArrayList();
        fcb.fillListFromDataBaseString(listCreators, "Name","ContentCreator");
        creatorComboBox.setItems(listCreators);
        

        Button btnInsert = new Button("Insert");

        layout.getChildren().addAll(titleLabel, lblContent, contentId, lblWebcastTitle, webcastTitle, lblWebcastTheme, webcastTheme, lblWebcastDescription, webcastDescription, pickerBox, lblStatus, statusComboBox, lblWebcastDuration, webcastDuration, lblWebcastUrl, webcastUrl ,lblCreator, creatorComboBox, btnInsert);

        //Insert button action
        btnInsert.setOnAction((event) -> {
            try{             
                if(webcastTitle.getText().isEmpty() || webcastDuration.getText().isEmpty() || statusComboBox.getValue() == null || webcastUrl.getText().isEmpty() || creatorComboBox.getValue() == null){
                    Alert missingAlert = new Alert(AlertType.ERROR);
                    missingAlert.setTitle("Error");
                    missingAlert.setHeaderText("Missing field error");
                    missingAlert.setContentText("You didn't fill in all the necessary fields!");
                    missingAlert.showAndWait();
                } else {

                    databaseWebcast.insertWebcast(Integer.parseInt(contentId.getText()), Date.valueOf(datePicker.getValue()), webcastTitle.getText(), webcastTheme.getText(), webcastDescription.getText(), String.valueOf(statusComboBox.getValue()), Time.valueOf(webcastDuration.getText()), String.valueOf(creatorComboBox.getValue()), webcastUrl.getText());
                    
                    contentId.clear();
                    datePicker.setValue(null);
                    webcastTitle.clear();
                    webcastTheme.clear();
                    webcastDescription.clear();
                    statusComboBox.setValue(null);
                    webcastDuration.clear();
                    creatorComboBox.setValue(null);
                    webcastUrl.clear();
                }

            } catch(Exception e) {
                e.printStackTrace();
            }
        });
        
        return layout;
    }
}
