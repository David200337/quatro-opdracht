

public class Student {
    private int studentId;
    private String name;
    private String email;
    private String dateOfBirth;
    private String gender;
    private String address;
    private String postalCode;
    private String city;
    private String country;
    
    public Student(int studentId, String name, String email, String dateOfBirth, String gender, String address, String postalCode, String city, String country ){
        setStudentId(studentId);
        setStudentEmail(email);
        setStudentName(name);
        setStudentAddress(address);
        setStudentCity(city);
        setStudentCountry(country);
        setStudentDateOfBirth(dateOfBirth);
        setStudentGender(gender);
        setStudentPostalCode(postalCode);
    }

    public int getStudentId(){
        return studentId;
    }

    public void setStudentId(int studentId){
        this.studentId = studentId;
    }

    public String getStudentName(){
        return name;
    }

    public void setStudentName(String name){
        this.name = name;
    }

    public String getStudentEmail(){
        return email;
    }

    public void setStudentEmail(String email){
        this.email = email;
    }

    public String getStudentDateOfBirth(){
        return dateOfBirth;
    }

    public void setStudentDateOfBirth(String dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }

    public String getStudentGender(){
        return gender;
    }

    public void setStudentGender(String gender){
        this.gender = gender;
    }

    public String getStudentAddress(){
        return address;
    }

    public void setStudentAddress(String address){
        this.address = address;
    }

    public String getStudentPostalCode(){
        return postalCode;
    }

    public void setStudentPostalCode(String postalCode){
        this.postalCode = postalCode;
    }

    public String getStudentCity(){
        return city;
    }

    public void setStudentCity(String city){
        this.city = city;
    }

    public String getStudentCountry(){
        return country;
    }

    public void setStudentCountry(String country){
        this.country = country;
    }

}
