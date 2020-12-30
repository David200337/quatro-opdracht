package src.domain;

import java.sql.Date;

abstract class Content {
    private int contentId;
    private Date publicationDate;
    private String theme;
    private String title;
    private int percentage;
    private Status status;
    private ContentCreator creator;

    public Content(int contentId, Date publicationDate, String theme, String title, int percentage, Status status, ContentCreator creator){
        this.contentId = contentId;
        this.percentage = percentage;
        this.publicationDate = publicationDate;
        this.status = status;
        this.theme = theme;
        this.title = title;
        this.creator = creator;
    }


    public Integer getContentId(){
        return contentId;
    }

    public void setContentId(int id){
        this.contentId = id;
    }

    public Date getPublicationDate(){
        return publicationDate;
    }

    public void setPublicationDate(Date date){
        this.publicationDate = date;
    }

    public String getTheme(){
        return theme;
    }

    public void setTheme(String theme){
        this.theme = theme;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public Integer getPercentage(){
        return percentage;
    }

    public void setPercentage(int percentage){
        this.percentage = percentage;
    }

    public Status getStatus(){
        return status;
    }

    public void setStatus(Status status){
        this.status = status;
    }
}