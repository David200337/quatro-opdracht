package src.domain;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

public class Webcast {
    private String url;
    private Time duration;
    private int contentId;
    private Date publicationDate;
    private String theme;
    private String title;
    private String description;
    private Object status;
    private String creatorName;

    public Webcast(int contentId, Date publicationDate, String theme, String title, String description, Object status,
            String creatorName, String url, Time duration) {
        this.setContentId(contentId);
        this.setPublicationDate(publicationDate);
        this.setTheme(theme);
        this.setTitle(title);
        this.setDescription(description);
        this.setStatus(status);
        this.setCreatorName(creatorName);
        this.url = url;
        this.duration = duration;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public Webcast() {
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
