package src.domain;

import java.sql.Date;

public class Webcast extends Content {
    private String url;
    private int duration;
    
    public Webcast(int contentId, Date publicationDate, String theme, String title, int percentage, Status status,
            ContentCreator creator, String url, int duration) {
        super(contentId, publicationDate, theme, title, percentage, status, creator);
        this.url = url;
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public Integer getDuration(){
        return duration;
    }

    public void setDuration(int duration){
        this.duration = duration;
    }

    
}
