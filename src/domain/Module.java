package src.domain;

import java.sql.Date;

public class Module extends Content {
    private int serialNumber;
    
    public Module(int contentId, Date publicationDate, String theme, String title, String description, String status,
            int creatorId, String name, String email, String organisation, int number) {
        super(contentId, publicationDate, theme, title, description, status, creatorId, name, email, organisation);
        this.serialNumber = number;
    }


    public Integer getSerialNumber(){
        return serialNumber;
    }

    public void setSerialNumber(int number){
        this.serialNumber = number;
    }
}
