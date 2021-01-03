package src.domain;

import java.sql.Date;

public class Module extends Content {
    private int serialNumber;
    
    public Module(int contentId, Date publicationDate, String theme, String title, String description, String status,
            ContentCreator creator, int number) {
        super(contentId, publicationDate, theme, title, description, status, creator);
        this.serialNumber = number;
    }


    public Integer getSerialNumber(){
        return serialNumber;
    }

    public void setSerialNumber(int number){
        this.serialNumber = number;
    }
}
