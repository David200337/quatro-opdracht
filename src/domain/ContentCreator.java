package src.domain;

public class ContentCreator {
    private int creatorId;
    private String name;
    private String email;
    private String organisation;

    public ContentCreator(int creatorId, String name, String email, String organisation){
        this.creatorId = creatorId;
        this.name = name;
        this.email = email;
        this.organisation = organisation;
    }

    public Integer getCreatorId(){
        return creatorId;
    }

    public void setCreatorId(int id){
        this.creatorId = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getOrganisation(){
        return organisation;
    }

    public void setOrganisation(String organisation){
        this.organisation = organisation;
    }
}
