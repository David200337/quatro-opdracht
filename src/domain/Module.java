package src.domain;

import java.sql.Date;

public class Module extends Content {
    private int serialNumber;
    
    public Module(int contentId, Date publicationDate, String theme, String title, int percentage, Status status,
            ContentCreator creator, int number) {
        super(contentId, publicationDate, theme, title, percentage, status, creator);
        this.serialNumber = number;
    }

    

    public Integer getSerialNumber(){
        return serialNumber;
    }

    public void setSerialNumber(int number){
        this.serialNumber = number;
    }
}
