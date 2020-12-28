package src.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import javafx.util.converter.LocalDateStringConverter;

public class DatePickerConverter extends LocalDateStringConverter{
    //Default Date Pattern
    private String pattern = "yyyy-MM-dd";
    //The Date Time Converter
    private DateTimeFormatter dtFormatter;

    public DatePickerConverter(){
        dtFormatter = DateTimeFormatter.ofPattern(pattern);
    }

    public DatePickerConverter(String pattern){
        this.pattern = pattern;
        dtFormatter = DateTimeFormatter.ofPattern(pattern);
    }

    //Change String to LocalDate
    @Override
    public LocalDate fromString(String text){
        LocalDate date = null;

        if(text != null && !text.trim().isEmpty()){
            date = LocalDate.parse(text, dtFormatter);
        }
        return date;
    }

    //Change LocalDate to String
    @Override
    public String toString(LocalDate date) {
        String text = null;
         
        if (date != null) 
        {
            text = dtFormatter.format(date);
        }
     
        return text;
    }


}
