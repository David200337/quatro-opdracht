package src.domain;

import java.sql.Date;
import java.sql.Time;

public class Webcast extends Content {
    private String url;
    private Time duration;
    
    public Webcast(int contentId, Date publicationDate, String theme, String title, String description, String status,
        int creatorId, String name, String email, String organisation, String url, Time duration) {
        super(contentId, publicationDate, theme, title, description, status, creatorId, name, email, organisation);
        this.url = url;
        this.duration = duration;
    }


    public Webcast(){
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public Time getDuration(){
        return duration;
    }

    public void setDuration(Time duration){
        this.duration = duration;
    }

    

    

    
}
